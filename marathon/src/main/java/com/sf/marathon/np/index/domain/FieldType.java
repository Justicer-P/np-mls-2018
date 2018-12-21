package com.sf.marathon.np.index.domain;

import java.util.List;

/**
 * 描述：
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2015/9/18     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class FieldType {
    private String fieldName;
    private IndexFieldType indexFieldType = IndexFieldType.STRING;
    private AnalyzedType analyzedType = AnalyzedType.NotAnalyzed;
    private List<FieldType> childFieldTypes;

    public FieldType() {

    }

    public FieldType(String fieldName) {
        this(fieldName, IndexFieldType.STRING, AnalyzedType.NotAnalyzed, null);
    }

    private FieldType(String fieldName, IndexFieldType indexFieldType) {
        this(fieldName, indexFieldType, AnalyzedType.NotAnalyzed, null);
    }

    private FieldType(String fieldName, IndexFieldType indexFieldType, List<FieldType> childFieldTypes) {
        this(fieldName, indexFieldType, AnalyzedType.NotAnalyzed, childFieldTypes);
    }

    private FieldType(String fieldName, IndexFieldType indexFieldType, AnalyzedType analyzedType, List<FieldType> childFieldTypes) {
        this.fieldName = fieldName;
        this.indexFieldType = indexFieldType;
        this.analyzedType = analyzedType;
        this.childFieldTypes = childFieldTypes;
    }

    public static FieldType type(String fieldName){
        return new FieldType(fieldName, IndexFieldType.STRING, AnalyzedType.NotAnalyzed, null);
    }

    public static FieldType type(String fieldName, IndexFieldType indexFieldType){
        return new FieldType(fieldName, indexFieldType, AnalyzedType.NotAnalyzed, null);
    }

    public static FieldType type(String fieldName, IndexFieldType indexFieldType, AnalyzedType analyzedType){
        return new FieldType(fieldName, indexFieldType, analyzedType, null);
    }

    public static FieldType type(String fieldName, IndexFieldType indexFieldType, List<FieldType> childFieldTypes){
        return new FieldType(fieldName, indexFieldType, AnalyzedType.NotAnalyzed, childFieldTypes);
    }

    public static FieldType type(String fieldName, IndexFieldType indexFieldType, AnalyzedType analyzedType, List<FieldType> childFieldTypes){
        return new FieldType(fieldName, indexFieldType, analyzedType, childFieldTypes);
    }

    public List<FieldType> getChildFieldTypes() {
        return childFieldTypes;
    }

    public void setChildFieldTypes(List<FieldType> childFieldTypes) {
        this.childFieldTypes = childFieldTypes;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public IndexFieldType getIndexFieldType() {
        return indexFieldType;
    }

    public void setIndexFieldType(IndexFieldType indexFieldType) {
        this.indexFieldType = indexFieldType;
    }

    public AnalyzedType getAnalyzedType() {
        return analyzedType;
    }

    public void setAnalyzedType(AnalyzedType analyzedType) {
        this.analyzedType = analyzedType;
    }

    public enum AnalyzedType {
        Analyzed("analyzed"),
        NotAnalyzed("not_analyzed"),
        NO("no");
        private String analyzed;

        AnalyzedType(String analyzed) {

            this.analyzed = analyzed;
        }

        @Override
        public String toString() {
            return analyzed;
        }
    }
}
