package com.sf.marathon.np.index.domain;

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
public class EsConfig {
    private static EsConfig esConfig = new EsConfig();

    private EsConfig() {

    }

    public static EsConfig getInstance() {
        return esConfig;
    }

    private String clusterName;

    private String esUrl;

    private Integer workerCount;

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getEsUrl() {
        return esUrl;
    }

    public void setEsUrl(String esUrl) {
        this.esUrl = esUrl;
    }

    public Integer getWorkerCount() {
        return workerCount == null ? 4 : workerCount;
    }

    public void setWorkerCount(Integer workerCount) {
        this.workerCount = workerCount;
    }
}
