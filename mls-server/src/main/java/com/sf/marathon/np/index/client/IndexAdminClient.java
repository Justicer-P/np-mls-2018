package com.sf.marathon.np.index.client;

import com.sf.marathon.index.domain.FieldType;
import com.sf.marathon.index.domain.IndexFieldType;
import com.sf.marathon.index.exception.ESClientException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.engine.EngineConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.sf.marathon.index.util.Constant.*;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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
public class IndexAdminClient implements IIndexAdminClient {
    private static Logger L = LoggerFactory.getLogger(IndexAdminClient.class);

    private static final String ELASTIC_COMMON_INDEX_TM = "__index_tm__";
    private static final String DELETE_GC_RETAIN_TIME = "20m";

    @Override
    public void createDb(String db) {
        createDb(db, DEFAULT_ES_SHARDS, DEFAULT_ES_REPLICAS, false);
        do {
            boolean createOk = existDb(db);
            if (createOk) {
                break;
            }
            wait(db);
        } while (true);
    }

    public boolean existDb(String indexName) {
        IndicesExistsResponse indicesExistsResponse = ElasticClient.instance.getClient().admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();
        return indicesExistsResponse.isExists();
    }

    private void wait(String db) {
        try {
            L.warn("not create db:{} ok ,try again", db);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void createDb(String db, int shards, int replicas, boolean shouldSetRefreshInterval) {
        ImmutableSettings.Builder builder = ImmutableSettings.settingsBuilder()
                //设置主分片数
                .put("index.number_of_shards", shards)
                //设置副本数
                .put("index.number_of_replicas", replicas)
                .put(EngineConfig.INDEX_GC_DELETES_SETTING, DELETE_GC_RETAIN_TIME);
        if(shouldSetRefreshInterval) {
            builder.put("index.refresh_interval","5s");
        }
        Settings settings = builder.build();

        Map source = new HashMap();
        source.put("dynamic", "strict");

        ElasticClient.instance.getClient().admin().indices()
                .create(new CreateIndexRequest(db).mapping("_default_", source).settings(settings))
                .actionGet();
    }

    @Override
    public void createTable(String index, String type, List<FieldType> fieldTypes, boolean plusIndexTm) {
        try {
            XContentBuilder mappingBuilder = jsonBuilder()
                    .startObject()
                    .startObject(type)
                    .startObject("_all")
                    .field("enabled", false)
                    .endObject()
                    .startObject("properties");
            for (FieldType fieldType : fieldTypes) {
                recursionMapping(mappingBuilder, fieldType);
            }
            if (plusIndexTm) {
                plusDefaultField(mappingBuilder, ELASTIC_COMMON_INDEX_TM);
            }
            mappingBuilder.endObject()//properties
                    .endObject() //type
                    .endObject();//db
            PutMappingRequest mappingRequest = Requests.putMappingRequest(index).type(type).source(mappingBuilder.string());
            ElasticClient.instance.getClient().admin().indices().putMapping(mappingRequest).actionGet();
        } catch (IOException e) {
            throw new ESClientException(e.getMessage());
        }
    }

    private void plusDefaultField(XContentBuilder mappingBuilder, String elasticCommonIndexModifyTm) throws IOException {
        mappingBuilder.startObject(elasticCommonIndexModifyTm);
        mappingBuilder.field("type", IndexFieldType.DATE)
                .field("index", FieldType.AnalyzedType.NotAnalyzed)
                .field("doc_values", true);
        mappingBuilder.endObject();
    }

    private void recursionMapping(XContentBuilder mappingBuilder, FieldType fieldType) throws IOException {
        mappingBuilder.startObject(fieldType.getFieldName());

        if (fieldType.getIndexFieldType() != IndexFieldType.NESTED) {
            mappingBuilder.field("type", fieldType.getIndexFieldType())
                    .field("index", fieldType.getAnalyzedType());
            if (fieldType.getAnalyzedType() != FieldType.AnalyzedType.NO)
                mappingBuilder.field("doc_values", true);
        } else {
            mappingBuilder.field("type", IndexFieldType.NESTED);
            mappingBuilder.startObject("properties");
            List<FieldType> childFieldTypes = fieldType.getChildFieldTypes();
            for (FieldType childObject : childFieldTypes) {
                recursionMapping(mappingBuilder, childObject);
            }
            mappingBuilder.endObject();//end
        }
        mappingBuilder.endObject();
    }
}
