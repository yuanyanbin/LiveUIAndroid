package com.ggd.live.httputils.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ggd.live.R;


public class SharedPreferencesUtil {
    public static String getString(Context context, String key,
                                   final String defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getString(key, defaultValue);
    }

    public static void setString(Context context, final String key,
                                 final String value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putString(key, value).apply();
    }

    public static boolean getBoolean(Context context, final String key,
                                     final boolean defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean hasKey(Context context, final String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(
                key);
    }

    public static void setBoolean(Context context, final String key,
                                  final boolean value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putBoolean(key, value).apply();
    }

    public static void setInt(Context context, final String key,
                              final int value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putInt(key, value).apply();
    }

    public static int getInt(Context context, final String key,
                             final int defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getInt(key, defaultValue);
    }

    public static void setFloat(Context context, final String key,
                                final float value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putFloat(key, value).apply();
    }

    public static float getFloat(Context context, final String key,
                                 final float defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getFloat(key, defaultValue);
    }

    public static void setLong(Context context, final String key,
                               final long value) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        settings.edit().putLong(key, value).apply();
    }

    public static long getLong(Context context, final String key,
                               final long defaultValue) {
        final SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(context);
        return settings.getLong(key, defaultValue);
    }


    public static SharedPreferences preferences;

    public static void init(Context context) {
        if (null == preferences) {
            preferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        }
    }

    //将数据加入到sharedPreferences里
    public static <T> void put(Context context, String key, T values) {
        init(context);

        SharedPreferences.Editor editor = preferences.edit();

        //判断sharedPreferences加入的类型
        if (values instanceof String) {
            editor.putString(key, values.toString());
        } else if (values instanceof Integer) {
            editor.putInt(key, (Integer) values);
        } else if (values instanceof Long) {
            editor.putLong(key, ((Long) values).intValue());
        } else if (values instanceof Boolean) {
            editor.putBoolean(key, (Boolean) values);
        } else if (values instanceof Double) {
            editor.putFloat(key, ((Double) values).intValue());
        }
        editor.apply();
    }

    //将数据取出来
    public static <T> T get(Context context, String key, T values) {
        init(context);

        Object o = null;
        if (values instanceof String) {
            o = preferences.getString(key, values.toString());
        } else if (values instanceof Integer) {
            o = preferences.getInt(key, (Integer) values);
        } else if (values instanceof Long) {
            o = preferences.getLong(key, ((Long) values).intValue());
        } else if (values instanceof Boolean) {
            o = preferences.getBoolean(key, (Boolean) values);
        } else if (values instanceof Double) {
            o = preferences.getFloat(key, ((Double) values).intValue());
        }

        T t = (T) o;
        return t;
    }


    public static void clear(Context context,
                             final SharedPreferences p) {
        final SharedPreferences.Editor editor = p.edit();
        editor.clear();
        editor.apply();
    }
}
