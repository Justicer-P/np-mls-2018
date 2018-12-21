package com.sf.marathon.np.index.client;

import com.sf.marathon.index.clause.GroupBy;
import com.sf.marathon.index.clause.SearchClause;
import com.sf.marathon.index.domain.RowBean;

import java.util.List;
import java.util.Map;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2018/12/20     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public interface IIndexClient
{
    List<RowBean> findPageData(String type, SearchClause searchClause, int pageSize, int pageIndex, String... indices);

    Map<String, Number[]> mulTiAggregation(String type, SearchClause searchClause, GroupBy groupBy, String... indices);

    void save(String index, String type, Map<String, Object> dataMap);
}
