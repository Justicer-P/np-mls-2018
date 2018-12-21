package com.sf.marathon.np.index.api.domain;

import com.sf.marathon.np.index.api.GroupType;

/**
 * 描述:
 * <p>
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1    2018/12/21     204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class LogData {
    private String sourceIp;
    private String destIp;
    /**
     * 请求时间格式yyyy-MM-dd HH:mm
     * 到分钟
     */
    private String reqTime;
    private String url;
    /**
     *
     * 请求总次数
     */
    private Integer requestTimes;
    /**
     *
     * 请求错误次数
     */
    private Integer errorTimes;
    private Double maxReponseTime;
    private Double minReponseTime;
    private Double avgReponseTime;
    private Double ninePercentResponseTime;

    /**
     * 分组类型
     */
    private GroupType type;

    public void setType(GroupType type) {
        this.type = type;
    }

    public GroupType getType(){
         return type;
     }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    public String getDestIp() {
        return destIp;
    }

    public void setDestIp(String destIp) {
        this.destIp = destIp;
    }

    public String getReqTime() {
        return reqTime;
    }

    public void setReqTime(String reqTime) {
        this.reqTime = reqTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRequestTimes() {
        return requestTimes;
    }

    public void setRequestTimes(Integer requestTimes) {
        this.requestTimes = requestTimes;
    }

    public Integer getErrorTimes() {
        return errorTimes;
    }

    public void setErrorTimes(Integer errorTimes) {
        this.errorTimes = errorTimes;
    }

    public Double getMaxReponseTime() {
        return maxReponseTime;
    }

    public void setMaxReponseTime(Double maxReponseTime) {
        this.maxReponseTime = maxReponseTime;
    }

    public Double getMinReponseTime() {
        return minReponseTime;
    }

    public void setMinReponseTime(Double minReponseTime) {
        this.minReponseTime = minReponseTime;
    }

    public Double getAvgReponseTime() {
        return avgReponseTime;
    }

    public void setAvgReponseTime(Double avgReponseTime) {
        this.avgReponseTime = avgReponseTime;
    }

    public Double getNinePercentResponseTime() {
        return ninePercentResponseTime;
    }

    public void setNinePercentResponseTime(Double ninePercentResponseTime) {
        this.ninePercentResponseTime = ninePercentResponseTime;
    }
}
