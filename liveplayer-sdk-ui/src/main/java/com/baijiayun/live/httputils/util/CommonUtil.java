package com.baijiayun.live.httputils.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Copyright (C)
 * FileName: CommonUtil
 * Author: 员外
 * Date: 2020-01-02 14:17
 * Description: TODO<通用工具类>
 * Version: 1.0
 */
public class CommonUtil {
    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取当前版本号
     */
    public static String getVersionName(Context mContext) {
        String version = "";
        try {
            // 获取PackageManager的实例
            PackageManager packageManager = mContext.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(
                    mContext.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    /**
     * 获取手机IMEI(需要“android.permission.READ_PHONE_STATE”权限)
     *
     * @return 手机getDeviceId
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId(Context mContext) {
        final TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Activity.TELEPHONY_SERVICE);
        try {
            if (tm != null) {
                String deviceId = tm.getDeviceId();
                if (TextUtils.isEmpty(deviceId)) {
                    return android.os.Build.SERIAL;
                } else {
                    return deviceId;
                }
            } else {
                return android.os.Build.SERIAL;
            }

        } catch (Exception e) {
            return android.os.Build.SERIAL;
        }
    }
}
