package com.sf.marathon.np.index.clause;

import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.metrics.avg.AvgBuilder;
import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
import org.elasticsearch.search.aggregations.metrics.valuecount.ValueCountBuilder;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2015/12/23     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class AggregationClause {
    protected final AggregationType aggregationType;
    protected String fieldName;

    public AggregationClause(AggregationType aggregationType, String statisticFieldName) {
        this.aggregationType = aggregationType;
        this.fieldName = statisticFieldName;
    }

    public AbstractAggregationBuilder build() {
        return aggregationType.toBuilder(fieldName);
    }

    public String getFieldName() {
        return fieldName;
    }

    public AbstractAggregationBuilder build(String fieldName) {
        return aggregationType.toBuilder(fieldName);
    }

    public enum AggregationType {
        SUM {
            @Override
            public AbstractAggregationBuilder toBuilder(String fieldName) {
                SumBuilder agg = AggregationBuilders
                        .sum(fieldName);
                agg.field(fieldName);
                return agg;
            }
        },
        COUNT {
            @Override
            public AbstractAggregationBuilder toBuilder(String fieldName) {
                ValueCountBuilder agg = AggregationBuilders.count(fieldName);
                agg.field(fieldName);
                return agg;
            }
        },
        MIN {
            @Override
            public AbstractAggregationBuilder toBuilder(String fieldName) {
                return AggregationBuilders.min(fieldName).field(fieldName);
            }
        },
        MAX {
            @Override
            public AbstractAggregationBuilder toBuilder(String fieldName) {
                return AggregationBuilders.max(fieldName).field(fieldName);
            }
        },
        AVG {
            @Override
            public AbstractAggregationBuilder toBuilder(String fieldName) {
                AvgBuilder agg = AggregationBuilders
                        .avg(fieldName);
                agg.field(fieldName);
                return agg;
            }
        };

        public abstract AbstractAggregationBuilder toBuilder(String fieldName);
    }
}
