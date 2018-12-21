package com.sf.marathon.np.index.client;

import com.sf.marathon.np.index.domain.FieldType;

import java.util.List;

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
public interface IIndexAdminClient {
    void createDb(String db);

    void createDb(String db, int shards, int replicas, boolean shouldSetRefreshInterval);

    void createTable(String index, String type, List<FieldType> fieldTypes, boolean plusIndexTm);
}
