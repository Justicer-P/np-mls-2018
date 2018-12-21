package com.sf.marathon.np.index.exception;

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
public class ESClientException extends RuntimeException {
    public ESClientException() {
    }

    public ESClientException(String message) {
        super(message);
    }

    public ESClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ESClientException(Throwable cause) {
        super(cause);
    }

    public ESClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
