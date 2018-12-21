package com.sf.marathon.np.index.util;

/**
 * Created by Administrator on 011,2016-3-11.
 */
public class StringUtil {
    public static boolean notEqual(String s1, String s2) {
        return !equal(s1, s2);
    }

    public static boolean equal(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        return s2 != null && s1.equals(s2);
    }

    public static boolean isEmpty(String value) {
        return value == null || value.trim().length() == 0;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }
}
