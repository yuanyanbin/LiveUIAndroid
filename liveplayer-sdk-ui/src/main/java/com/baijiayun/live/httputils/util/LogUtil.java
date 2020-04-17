package com.baijiayun.live.httputils.util;

import android.text.TextUtils;

public class LogUtil {
    private static String tag = "KKStudent";
    private static boolean log = true;

    public static void setTag(String tag) {
        LogUtil.tag = tag;
    }

    public static void setLog(boolean log) {
        LogUtil.log = log;
    }

    public static void i(String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.i(tag, msg);
    }

    public static void i(String tag, String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.i(tag, msg);
    }

    public static void d(String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.d(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.d(tag, msg);
    }

    public static void w(String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.w(tag, msg);
    }

    public static void w(String tag, String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.w(tag, msg);
    }

    public static void v(String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.v(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (log && !TextUtils.isEmpty(msg))
            android.util.Log.v(tag, msg);
    }

    public static void e(String msg) {
        if (log && !TextUtils.isEmpty(msg))
        android.util.Log.e(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (log && !TextUtils.isEmpty(msg))
        android.util.Log.e(tag, msg);
    }
}
