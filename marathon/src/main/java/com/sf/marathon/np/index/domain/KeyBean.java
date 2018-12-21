package com.sf.marathon.np.index.domain;

/**
 * Created by 204062 on 2015/10/10.
 */
public class KeyBean {
    /**
     * 对应计算框架的key
     */
    private String key;
    /**
     * 对应计算框架的TID
     */
    private long version;

    public KeyBean(String key, long version) {
        this.key = key;
        this.version = version;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof KeyBean)) return false;

        KeyBean keyBean = (KeyBean) o;

        if (version != keyBean.version) return false;
        if (!key.equals(keyBean.key)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key.hashCode();
        result = 31 * result + (int) (version ^ (version >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "KeyBean{" +
                "key='" + key + '\'' +
                ", version=" + version +
                '}';
    }
}
