package com.sf.marathon.np.index.api;

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
public enum GroupType {
    SOURCEIP,
    DESTIP,
    /**
     * 处理次数
     */
    URL_TYPE,
    /**
     * 响应时间
     */
    URL_TIME,
}
