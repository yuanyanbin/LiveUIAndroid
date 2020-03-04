package com.ggd.live.httputils.remote;

import android.content.Context;
import android.text.TextUtils;

import com.ggd.live.httputils.constant.Constants;
import com.ggd.live.httputils.util.CommonUtil;

import java.util.HashMap;

import static com.ggd.live.httputils.constant.Constants.TYPE_POST_FORM;


/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class HttpManager {
    public static HttpManager mInstance = null;
    public OKHttpRequest okHttpRequest;
    public Context mContext;

    public HttpManager(Context context) {
        this.mContext = context;
        okHttpRequest = OKHttpRequest.getInstance(context);//new OKHttpRequest();
    }

    public static HttpManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null) {
                    mInstance = new HttpManager(context.getApplicationContext());
                }
            }
        }
        return mInstance;
    }

    /**
     * 渠道用户登录
     *
     * @param name     客户名称
     * @param thirdId  第三方标识
     * @param classId  班级标识
     * @param schoolId 学校标识
     * @param grade    年级
     * @param phone    手机号
     * @param callBack
     */
    public void channelUserLogin(String name,  String thirdId, String classId, String schoolId, String grade, String phone, ReqCallBack<String> callBack) {
        HashMap<String, String> netParams = new HashMap<>();
        netParams.put("osType", "2"); //系统类型(1:IOS;2:安卓)
        netParams.put("osVersion", CommonUtil.getSystemVersion());//系统版本
        netParams.put("deviceId", CommonUtil.getDeviceId(mContext));//设备标识
        netParams.put("deviceModel", CommonUtil.getSystemModel()); //设备型号
        netParams.put("appVersion", CommonUtil.getVersionName(mContext)); //应用版本
        netParams.put("channel", "bszhihui");
        netParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
        netParams.put("thirdId", thirdId);
        netParams.put("classId", classId);
        netParams.put("schoolId", schoolId);
        netParams.put("grade", grade);

        if (!TextUtils.isEmpty(name)) {
            netParams.put("name", name);
        }
        if (!TextUtils.isEmpty(phone)) {
            netParams.put("phone", phone);
        }
        okHttpRequest.requestAsyn(Constants.channelUserLogin, TYPE_POST_FORM, netParams, callBack);
    }

    /**
     * 快答提交
     *
     * @param imageUrl    图片链接url
     * @param description 描述
     * @param subjectId   学科
     * @param callBack
     */
    public void createQuestion(String imageUrl, String description, String subjectId, ReqCallBack<String> callBack) {
        HashMap<String, String> netParams = HttpMapUtil.getMap(mContext);
        if (!TextUtils.isEmpty(imageUrl))
            netParams.put("imageUrl", imageUrl);
        if (!TextUtils.isEmpty(description))
            netParams.put("description", description);
        netParams.put("subject", subjectId);
        okHttpRequest.requestAsyn(Constants.createQuestion, TYPE_POST_FORM, netParams, callBack);
    }

    /**
     * 获取未完成的秒答详情
     *
     * @param callBack
     */
    public void getIncompleteQuestion(ReqCallBack<String> callBack) {
        HashMap<String, String> map = HttpMapUtil.getMap(mContext);
        okHttpRequest.requestAsyn(Constants.getIncompleteQuestion, TYPE_POST_FORM, map, callBack);
    }

    /**
     * 重新提交秒答
     *
     * @param id
     * @param callBack
     */
    public void resubmitQuestion(String id, ReqCallBack<String> callBack) {
        HashMap<String, String> netParams = HttpMapUtil.getMap(mContext);
        netParams.put("id", id);
        okHttpRequest.requestAsyn(Constants.resubmitQuestion, TYPE_POST_FORM, netParams, callBack);
    }

    /**
     * 取消秒答
     *
     * @param id
     * @param callBack
     */
    public void cancelQuestion(String id, ReqCallBack<String> callBack) {
        HashMap<String, String> netParams = HttpMapUtil.getMap(mContext);
        netParams.put("id", id);
        okHttpRequest.requestAsyn(Constants.cancelQuestion, TYPE_POST_FORM, netParams, callBack);
    }

    /**
     * 学生app进入秒答教室接口
     *
     * @param id       秒答标识
     * @param callBack
     */
    public void enterQuestion(String id, ReqCallBack<String> callBack) {
        HashMap<String, String> map = HttpMapUtil.getMap(mContext);
        map.put("id", id);
        okHttpRequest.requestAsyn(Constants.enterQuestion, TYPE_POST_FORM, map, callBack);
    }

    /**
     * 完成秒答
     *
     * @param id
     * @param callBack
     */
    public void finishQuestion(String id, ReqCallBack<String> callBack) {
        HashMap<String, String> map = HttpMapUtil.getMap(mContext);
        map.put("id", id);
        okHttpRequest.requestAsyn(Constants.finishQuestion, TYPE_POST_FORM, map, callBack);
    }
}
