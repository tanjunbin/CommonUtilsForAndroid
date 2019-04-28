// (c)2016 Flipboard Inc, All Rights Reserved.

package com.tan.lib_utils_android.apache;

public class ObjectUtils {


    public static String toString(final Object obj) {
        return obj == null ? "" : obj.toString();
    }

    public static String toString(final Object obj, final String nullStr) {
        return obj == null ? nullStr : obj.toString();
    }

    public static Long toLong(final Object obj) {
        if (obj == null) return 0L;
        try {
            return Long.parseLong(obj.toString());
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public static Integer toInteger(final Object obj) {
        if (obj == null) return 0;
        try {
            return Integer.parseInt(obj.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static Double toDouble(final Object obj) {
        if (obj == null) return 0.0;
        try {
            return Double.parseDouble(obj.toString());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public static Boolean toBoolean(final Object obj) {
        return obj == null ? false : Boolean.parseBoolean(obj.toString());
    }

}
