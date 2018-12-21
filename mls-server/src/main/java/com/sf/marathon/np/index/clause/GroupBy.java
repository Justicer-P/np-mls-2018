package com.sf.marathon.np.index.clause;
import com.sf.marathon.np.index.util.Tuple;
import org.elasticsearch.common.base.Preconditions;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;

import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2016/6/17     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class GroupBy {
    public static final int BUCKET_THRESHOLD = 1000;
    public static final int MAX_GROUP_SIZE = 2;
    public static final int DEFAULT_BUCKET_SIZE = 10;

    private AggregationClause[] aggregationClauses;
    private Tuple<String, Integer>[] groupFieldsTuples;
    private String[] groupFields;

    public GroupBy(AggregationClause[] aggregationClauses, String... groupFields) {
        Preconditions.checkArgument(groupFields.length >= 1, "the size of group Fields must greater than 1 ");
        this.aggregationClauses = aggregationClauses;
        this.groupFields = groupFields;
        initGroupFiledsTuple(groupFields);

    }

    public GroupBy(AggregationClause[] aggregationClauses, Tuple<String, Integer>... groupFieldsTuples) {
        Preconditions.checkArgument(groupFieldsTuples.length >= 1, "the size of group Fields must greater than 1 ");
        this.aggregationClauses = aggregationClauses;
        this.groupFieldsTuples = groupFieldsTuples;
        initGroupFields(groupFieldsTuples);
    }

    private void initGroupFiledsTuple(String[] groupFields) {
        this.groupFieldsTuples = new Tuple[groupFields.length];
        for (int i = 0; i < groupFields.length; i++) {
            groupFieldsTuples[i] = new Tuple<>(groupFields[i], DEFAULT_BUCKET_SIZE);
        }
    }

    private void initGroupFields(Tuple<String, Integer>[] groupFieldsTuples) {
        this.groupFields = new String[groupFieldsTuples.length];
        int bucket = 1;
        for (int i = 0; i < groupFieldsTuples.length; i++) {
            groupFields[i] = groupFieldsTuples[i]._1;
            bucket *= groupFieldsTuples[i]._2;
        }
        Preconditions.checkArgument(bucket <= BUCKET_THRESHOLD, "bucket size: " + bucket + " is not be over " + BUCKET_THRESHOLD);
    }

    public AggregationClause[] getAggregationClauses() {
        return aggregationClauses;
    }

    public boolean isAllowed() {
        return groupFieldsTuples.length <= MAX_GROUP_SIZE;
    }

    public String[] getGroupFields() {
        return groupFields;
    }

    public AbstractAggregationBuilder build(AggregationClause aggregationClause) {
        TermsBuilder field = terms(aggregationClause.fieldName).field(groupFields[0]);
        if (groupFieldsTuples.length == 1) {
            field.size(groupFieldsTuples[0]._2 > BUCKET_THRESHOLD ? BUCKET_THRESHOLD : groupFieldsTuples[0]._2);
        } else {
            field.size(groupFieldsTuples[0]._2);
        }
        return field.subAggregation(recurse(1, aggregationClause));
    }

    private AbstractAggregationBuilder recurse(int index, AggregationClause aggregationClause) {
        if (index == groupFields.length) {
            return aggregationClause.build();
        } else {
            Tuple<String, Integer> groupFieldsTuple = groupFieldsTuples[index];
            TermsBuilder field = terms(groupFieldsTuple._1)
                    .field(groupFieldsTuple._1)
                    .size(groupFieldsTuple._2);
            return field.subAggregation(recurse(++index, aggregationClause));
        }
    }
}
