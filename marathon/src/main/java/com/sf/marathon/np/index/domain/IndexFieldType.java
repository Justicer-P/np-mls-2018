package com.sf.marathon.np.index.domain;

/**
 * 描述：
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2015/11/27     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public enum IndexFieldType {
    STRING("string"),
    INT("integer"),
    LONG("long"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    NESTED("nested"),
    GEO_POINT("geo_point"),
    DATE("date");
    private String type;

    IndexFieldType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
