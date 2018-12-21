package com.sf.marathon.np.index.domain;

import java.util.Map;

/**
 * Created by 204062 on 2015/10/10.
 */
public class RowBean {
    protected KeyBean keyBean;
    private Map<String,Object> fieldValue;

    public RowBean(KeyBean keyBean, Map<String, Object> fieldValue) {
        this.fieldValue = fieldValue;
        this.keyBean = keyBean;
    }

    public Map<String, Object> getFieldValue() {
        return fieldValue;
    }

    public String getKey() {
        return this.keyBean.getKey();
    }

    public long getVersion() {
        return this.keyBean.getVersion();
    }

    @Override
    public String toString() {
        return "RowBean{" +
                "idBean=" + keyBean +
                ", fieldValue=" + fieldValue +
                '}';
    }
}
