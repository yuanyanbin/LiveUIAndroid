package com.ggd.live.httputils.remote;

import android.content.Context;
import android.text.TextUtils;

import com.ggd.live.httputils.constant.Constants;
import com.ggd.live.httputils.util.CommonUtil;
import com.ggd.live.httputils.util.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Copyright (C)
 * FileName: HttpMapUtil
 * Author: 员外
 * Date: 2020-02-13 14:21
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class HttpMapUtil {

    public static HashMap<String, String> getMap(Context mContext) {
        HashMap<String, String> netParams = new HashMap<>();
        netParams.put("osType", "2"); //系统类型(1:IOS;2:安卓)
        netParams.put("osVersion", CommonUtil.getSystemVersion());//系统版本
        netParams.put("deviceId", CommonUtil.getDeviceId(mContext));//设备标识
        netParams.put("deviceModel", CommonUtil.getSystemModel()); //设备型号
        netParams.put("appVersion", CommonUtil.getVersionName(mContext)); //应用版本
        netParams.put("token", SharedPreferencesUtil.getString(mContext, Constants.USER_TOKEN, ""));
        netParams.put("channel", "bszhihui");
        return netParams;
    }
}
