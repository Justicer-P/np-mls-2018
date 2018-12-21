package com.sf.marathon.np.index.util;

/**
 * 描述：
 * <pre>
 * HISTORY
 * ****************************************************************************
 *  ID   DATE            PERSON          REASON
 *  1   2017/6/20         204062          Create
 * ****************************************************************************
 * </pre>
 *
 * @author 204062
 * @since 1.0
 */
public class ExceptionUtils {

    private ExceptionUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isException(Throwable e, Class<?>... exceptionClass) {
        for (Class<?> clazz : exceptionClass) {
            if (checkException(e, clazz)) {
                return true;
            }
        }
        return false;
    }

    private static boolean checkException(Throwable e, Class<?> exceptionClass) {
        String errorName = exceptionClass.getSimpleName();
        while (e != null) {
            if (exceptionClass.isAssignableFrom(e.getClass())) {
                return true;
            }
            if (e.getMessage() != null && e.getMessage().contains(errorName)) {
                return true;
            }

            e = e.getCause();//NOSONAR
        }
        return false;
    }
}
