package com.sf.marathon.np.index.clause;

import org.elasticsearch.search.aggregations.bucket.terms.Terms;

import java.util.Map;

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
public class AggregationRecurser {
    private StringBuffer keyBuffer;
    private Map<String, Number> maps;
    private Terms aggregation;
    private String[] groupFields;
    private String fieldName;

    private Map<String, Number[]> groupMaps;
    private int length;
    private int dataIndex;

    public AggregationRecurser(StringBuffer keyBuffer, Terms aggregation, String[] groupFields, String fieldName) {
        this.keyBuffer = keyBuffer;
        this.aggregation = aggregation;
        this.groupFields = groupFields;
        this.fieldName = fieldName;
    }

    public StringBuffer getKeyBuffer() {
        return keyBuffer;
    }

    public Map<String, Number> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Number> maps) {
        this.maps = maps;
    }

    public Terms getAggregation() {
        return aggregation;
    }

    public String[] getGroupFields() {
        return groupFields;
    }

    public String getGroupField(int index) {
        return groupFields[index];
    }

    public String getFieldName() {
        return fieldName;
    }

    public Map<String, Number[]> getGroupMaps() {
        return groupMaps;
    }

    public void setGroupMaps(Map<String, Number[]> groupMaps) {
        this.groupMaps = groupMaps;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDataIndex() {
        return dataIndex;
    }

    public void setDataIndex(int dataIndex) {
        this.dataIndex = dataIndex;
    }


}
