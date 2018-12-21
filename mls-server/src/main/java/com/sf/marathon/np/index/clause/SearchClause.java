package com.sf.marathon.np.index.clause;

import com.sf.marathon.index.exception.ESClientException;
import com.sf.marathon.index.util.Constant;
import com.sf.marathon.index.util.StringUtil;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.collect.Lists;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

import static org.elasticsearch.index.query.FilterBuilders.*;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * 描述：搜索条件类
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1  2015/9/21     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class SearchClause {
    private List<OrderClause> orderClauses = Lists.newArrayList();

    private List<Criteria> criterias = new ArrayList<Criteria>();

    private Stack bracketStack = new Stack();
    private String preference;

    private SearchClause() {

    }

    public static SearchClause newClause() {
        return new SearchClause();
    }

    /**
     * 等于
     *
     * @param fieldName  字段值
     * @param fieldValue 字段名称
     * @return equal(this)
     */
    public SearchClause equal(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.EQ, fieldValue);
        criterias.add(criteria);
        return this;
    }

    public String getPreference() {
        return preference;
    }

    //用于分页自动有序情形
    public SearchClause setPreference(String preference) {
        this.preference = StringUtil.isEmpty(preference) ? UUID.randomUUID().toString() : preference;
        return this;
    }

    /**
     * 模糊匹配 支持 * ?
     *
     * @param fieldName  字段值
     * @param fieldValue 字段名称
     * @return like(this)
     */
    public SearchClause like(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LIKE, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 大于
     *
     * @param fieldName  字段值
     * @param fieldValue 字段名称
     * @return greaterThan(this)
     */
    public SearchClause greaterThan(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.GT, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 小于
     *
     * @param fieldName  字段值
     * @param fieldValue 字段名称
     * @return lessThan(this)
     */
    public SearchClause lessThan(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LT, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 小于等于
     *
     * @param fieldName  字段值
     * @param fieldValue 字段名称
     * @return lessOrEqual(this)
     */
    public SearchClause lessOrEqual(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LE, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 左开右闭范围查询条件
     *
     * @param fieldName 字段名称
     * @param fromValue 左区间起始值
     * @param toValue   右区间结束值
     * @return leftAwayRightClosed(this)
     */
    public SearchClause leftAwayRightClosed(String fieldName, Object fromValue, Object toValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LARC, fromValue, toValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 左闭右闭范围查询条件
     *
     * @param fieldName 字段名称
     * @param fromValue 左区间起始值
     * @param toValue   右区间结束值
     * @return leftClosedRightClosed(this)
     */
    public SearchClause leftClosedRightClosed(String fieldName, Object fromValue, Object toValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LCRC, fromValue, toValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 左开右开范围查询条件
     *
     * @param fieldName 字段名称
     * @param fromValue 左区间起始值
     * @param toValue   右区间结束值
     * @return leftAwayRightAway(this)
     */
    public SearchClause leftAwayRightAway(String fieldName, Object fromValue, Object toValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LARA, fromValue, toValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 左闭右开范围查询条件
     *
     * @param fieldName 字段名称
     * @param fromValue 左区间起始值
     * @param toValue   右区间结束值
     * @return leftClosedRightAway(this)
     */
    public SearchClause leftClosedRightAway(String fieldName, Object fromValue, Object toValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.LCRA, fromValue, toValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 大于等于
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值
     * @return greaterOrEqual(this)
     */
    public SearchClause greaterOrEqual(String fieldName, Object fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.GE, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 地理位置范围查找
     *
     * @param fieldName   字段名称
     * @param leftTop     左上地理位置
     * @param rightBottom 右下地理位置
     * @return geoBound(this)
     */
    public SearchClause geoBound(String fieldName, GeoPoint leftTop, GeoPoint rightBottom) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.GEO_BOUND, leftTop, rightBottom);
        criterias.add(criteria);
        return this;
    }

    /**
     * 类似sql in
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值数组
     * @return in(this)
     */
    public SearchClause in(String fieldName, Object... fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.IN, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 类似sql in
     *
     * @param fieldName  字段名称
     * @param fieldValue 字段值数组
     * @return notIn(this)
     */
    public SearchClause notIn(String fieldName, Object... fieldValue) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.NOT_IN, fieldValue);
        criterias.add(criteria);
        return this;
    }

    /**
     * 为空
     *
     * @param fieldName 为空字段名称
     * @return isNull(this)
     */
    public SearchClause isNull(String fieldName) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.NULL);
        criterias.add(criteria);
        return this;
    }

    public SearchClause isNotNull(String fieldName) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.NOT_NULL);
        criterias.add(criteria);
        return this;
    }

    public SearchClause nested(String fieldName, SearchClause childClause) {
        Criteria criteria = new Criteria(fieldName, CriteriaOper.NESTED, childClause);
        criterias.add(criteria);
        return this;
    }

    /**
     * 或者
     *
     * @return or(this)
     */
    public SearchClause or() {
        Criteria criteria = new Criteria(CriteriaOper.OR);
        criterias.add(criteria);
        return this;
    }

    /**
     * 逻辑与
     *
     * @return and(this)
     */
    public SearchClause and() {
        Criteria criteria = new Criteria(CriteriaOper.AND);
        criterias.add(criteria);
        return this;
    }

    /**
     * 类似sql右括号
     *
     * @return rightBracket(this)
     */
    public SearchClause rightBracket() {
        Criteria criteria = new Criteria(CriteriaOper.RIGHT_BRACKET);
        criterias.add(criteria);
        return this;
    }

    /**
     * 类似sql左括号
     *
     * @return leftBracket(this)
     */
    public SearchClause leftBracket() {
        Criteria criteria = new Criteria(CriteriaOper.LEFT_BRACKET);
        criterias.add(criteria);
        return this;
    }

    /**
     * 排序
     *
     * @param orderCondition 排序条件对象
     * @return bindOrder(this)
     */
    public SearchClause orderBy(OrderClause orderCondition) {
        if (orderClauses.size() >= 3) throw new ESClientException("not over 3 level order");
        orderClauses.add(orderCondition);
        return this;
    }

    public SearchClause orderById(Order order) {
        orderClauses.add(new OrderClause(Constant.ELASTIC_ORDER_BY_KEY, order));
        return this;
    }

    /**
     * 获取排序对象
     *
     * @return 排序对象列表
     */
    public List<OrderClause> getOrderClauses() {
        return orderClauses;
    }

    /**
     * 构建
     *
     * @return ES QueryBuilder 对象
     */
    public QueryBuilder build() {
        if (criterias.isEmpty()) return matchAllQuery();
        Stack<FilterBuilder> stack = new Stack<FilterBuilder>();
        for (Criteria criteria : criterias) {
            CriteriaOper oper = criteria.getOper();
            switch (oper) {
                case LEFT_BRACKET:
                    BoolFilterBuilder boolQueryBuilder = boolFilter();
                    stack.add(boolQueryBuilder);
                    bracketStack.push(1);
                    break;
                case EQ:
                case NEQ:
                    FilterBuilder queryBuilder = termFilter(criteria.fieldName, criteria.fieldValue);
                    if (oper == CriteriaOper.NEQ) {
                        queryBuilder = FilterBuilders.notFilter(queryBuilder);
                    }
                    stack.add(queryBuilder);
                    break;
                case IN:
                    TermsFilterBuilder termsQueryBuilder = termsFilter(criteria.fieldName, (Object[]) criteria.fieldValue);
                    stack.add(termsQueryBuilder);
                    break;
                case NOT_IN:
                    NotFilterBuilder notFilterBuilder = FilterBuilders.notFilter(termsFilter(criteria.fieldName, (Object[]) criteria.fieldValue));
                    stack.add(notFilterBuilder);
                    break;
                case LIKE:
                    WildcardQueryBuilder wildcardQueryBuilder = QueryBuilders.wildcardQuery(criteria.fieldName, (String) criteria.fieldValue);
                    QueryFilterBuilder queryFilterBuilder = FilterBuilders.queryFilter(wildcardQueryBuilder);
                    stack.add(queryFilterBuilder);
                    break;
                case GE:
                case GT:
                    RangeFilterBuilder greaterRangeBuilder = rangeFilter(criteria.fieldName).from(criteria.fieldValue).includeLower(
                            oper == CriteriaOper.GE);
                    stack.add(greaterRangeBuilder);
                    break;
                case LT:
                case LE:
                    RangeFilterBuilder lessRangeBuilder = rangeFilter(criteria.fieldName).to(criteria.fieldValue).includeUpper(
                            oper == CriteriaOper.LE);
                    stack.add(lessRangeBuilder);
                    break;
                case OR:
                    stack.add(new ShouldEmptyBuilder());
                    break;
                case AND:
                    stack.add(new MustEmptyBuilder());
                    break;
                case NULL:
                case NOT_NULL:
                    FilterBuilder missingFilterBuilder = FilterBuilders.missingFilter(criteria.fieldName);
                    if (oper == CriteriaOper.NOT_NULL) {
                        missingFilterBuilder = FilterBuilders.notFilter(missingFilterBuilder);
                    }
                    stack.add(missingFilterBuilder);
                    break;
                case RIGHT_BRACKET:
                    if (!bracketStack.isEmpty()) bracketStack.pop();
                    rightBracket(stack);
                    break;
                case LARC:
                    stack.add(rangeFilterBuilder(criteria, false, true));
                    break;
                case LCRA:
                    stack.add(rangeFilterBuilder(criteria, true, false));
                    break;
                case LCRC:
                    stack.add(rangeFilterBuilder(criteria, true, true));
                    break;
                case LARA:
                    stack.add(rangeFilterBuilder(criteria, false, false));
                    break;
                case NESTED:
                    stack.add(nestedBuilder(criteria.fieldName, criteria.childSearchClause));
                    break;
                case GEO_BOUND:
                    GeoPoint leftTopPoint = (GeoPoint) criteria.getFieldFromValue();
                    GeoPoint rightBottomPoint = (GeoPoint) criteria.getFieldToValue();
                    FilterBuilder filter = geoBoundingBoxFilter(criteria.getFieldName())
                            .topLeft(leftTopPoint.getLat(), leftTopPoint.getLon())
                            .bottomRight(rightBottomPoint.getLat(), rightBottomPoint.getLon());
                    stack.add(filter);
                    break;
                default:
                    throw new ESClientException("NOT SUPPORT OPERATE!!");
            }

        }
        if (!bracketStack.isEmpty()) throw new ESClientException("No match bracket in searchClause!");
        FilterBuilder queryBuilderRest = null;
        if (stack.size() > 1) {
            FilterBuilder headFilterBuilder = stack.get(0);
            FilterBuilder peek = stack.get(1);
            BoolFilterBuilder boolQueryBuilder = null;
            if (peek instanceof ShouldEmptyBuilder) {
                boolQueryBuilder = boolFilter().should(headFilterBuilder);
            } else {
                boolQueryBuilder = boolFilter().must(headFilterBuilder);
            }

            FilterBuilder lastBuilder = null;
            for (int i = 1; i < stack.size(); i++) {//a=c and b=a
                FilterBuilder queryBuilder = stack.get(i);
                lastBuilder = queryBuilder;
                checkQueryBuilder(lastBuilder);
                FilterBuilder filterBuilder = stack.get(++i);

                if (queryBuilder instanceof ShouldEmptyBuilder) {
                    boolQueryBuilder = boolQueryBuilder.should(filterBuilder);
                } else if (queryBuilder != null) {
                    boolQueryBuilder = boolQueryBuilder.must(filterBuilder);
                }
            }
            queryBuilderRest = boolQueryBuilder;
        } else {
            queryBuilderRest = stack.pop();
        }
        stack.clear();

        return QueryBuilders.filteredQuery(matchAllQuery(), queryBuilderRest);
    }

    private void checkQueryBuilder(FilterBuilder queryBuilder) {
        if (queryBuilder != null && (!(queryBuilder instanceof ShouldEmptyBuilder) && !(queryBuilder instanceof MustEmptyBuilder))) {
            //check
            throw new ESClientException("error search clause");
        }
    }

    private RangeFilterBuilder rangeFilterBuilder(Criteria criteria, boolean includeLower, boolean includeUpper) {
        return rangeFilter(criteria.fieldName).from(criteria.fieldFromValue).includeLower(includeLower).to(criteria.fieldToValue).includeUpper(
                includeUpper);
    }

    private NestedFilterBuilder nestedBuilder(String path, SearchClause childSearchClause) {
        return nestedFilter(path, childSearchClause.build());
    }

    private void rightBracket(Stack<FilterBuilder> stack) {
        FilterBuilder tailPop = stack.pop();//a = 1 and b=2 and c=3
        Stack<FilterBuilder> tmpStack = new Stack<FilterBuilder>();
        while (isNotLeftBracket(tailPop)) {
            tmpStack.push(tailPop);
            if (stack.isEmpty()) {
                throw new ESClientException("No match bracket in searchClause!");
            }
            tailPop = stack.pop();
        }

        BoolFilterBuilder headBoolBuilder = (BoolFilterBuilder) tailPop;
        FilterBuilder lastBuilder = null;
        while (!tmpStack.empty()) { //c2 and c1 // (c=3   and b=2 and a = 1
            FilterBuilder popBuilder = tmpStack.pop();

            checkQueryBuilder(lastBuilder);

            if (popBuilder instanceof ShouldEmptyBuilder) {
                FilterBuilder pop = tmpStack.pop();
                headBoolBuilder.should(pop);
            } else if (popBuilder instanceof MustEmptyBuilder) {
                FilterBuilder pop = tmpStack.pop();
                headBoolBuilder.must(pop);
            } else {
                if (!tmpStack.isEmpty()) {
                    lastBuilder = tmpStack.peek();
                    if (lastBuilder instanceof ShouldEmptyBuilder) {
                        headBoolBuilder.should(popBuilder);
                    } else {
                        headBoolBuilder.must(popBuilder);
                    }
                } else {
                    headBoolBuilder.must(popBuilder);
                }
            }
            if (!tmpStack.isEmpty()) lastBuilder = tmpStack.peek();

        }
        stack.add(headBoolBuilder);
    }

    private boolean isNotLeftBracket(FilterBuilder tailPop) {
        return !(tailPop instanceof BoolFilterBuilder) || ((BoolFilterBuilder) tailPop).hasClauses();
    }

    /**
     * 描述：查询条件条件
     */
    public static class Criteria {

        /**
         * 查询字段
         */
        private String fieldName;
        /**
         * 查询操作符
         */
        private CriteriaOper oper;
        /**
         * 查询值
         */
        private Object fieldValue;

        ////范围查询左值
        private Object fieldFromValue;

        //范围查询右值
        private Object fieldToValue;

        private SearchClause childSearchClause;

        public Criteria(String fieldName, CriteriaOper oper, Object fieldValue) {
            this.fieldName = fieldName;
            this.oper = oper;
            this.fieldValue = fieldValue;
        }

        public Criteria(String fieldName, CriteriaOper oper, Object fieldFromValue, Object fieldToValue) {
            this.fieldName = fieldName;
            this.oper = oper;
            this.fieldFromValue = fieldFromValue;
            this.fieldToValue = fieldToValue;
        }

        public Criteria(String fieldName, CriteriaOper oper, SearchClause childSearchClause) {
            this.fieldName = fieldName;
            this.oper = oper;
            this.childSearchClause = childSearchClause;
        }

        public Criteria(CriteriaOper oper) {
            this.oper = oper;
        }

        public Criteria(String fieldName, CriteriaOper oper) {
            this(fieldName, oper, null);
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public CriteriaOper getOper() {
            return oper;
        }

        public void setOper(CriteriaOper oper) {
            this.oper = oper;
        }

        public Object getFieldValue() {
            return fieldValue;
        }

        public void setFieldValue(Object fieldValue) {
            this.fieldValue = fieldValue;
        }

        public Object getFieldFromValue() {
            return fieldFromValue;
        }

        public void setFieldFromValue(Object fieldFromValue) {
            this.fieldFromValue = fieldFromValue;
        }

        public Object getFieldToValue() {
            return fieldToValue;
        }

        public void setFieldToValue(Object fieldToValue) {
            this.fieldToValue = fieldToValue;
        }
    }


    /**
     * Created by 204062 on 2016/12/26.
     */
    static class ShouldEmptyBuilder implements FilterBuilder {
        @Override
        public BytesReference buildAsBytes() throws ElasticsearchException {
            return null;
        }

        @Override
        public BytesReference buildAsBytes(XContentType contentType) throws ElasticsearchException {
            return null;
        }

        @Override
        public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
            return null;
        }
    }

    /**
     * Created by 204062 on 2016/12/26.
     */
    static class MustEmptyBuilder implements FilterBuilder {
        @Override
        public BytesReference buildAsBytes() throws ElasticsearchException {
            return null;
        }

        @Override
        public BytesReference buildAsBytes(XContentType contentType) throws ElasticsearchException {
            return null;
        }

        @Override
        public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
            return null;
        }
    }
}
