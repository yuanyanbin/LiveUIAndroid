package com.ggd.live.httputils.constant;

import okhttp3.MediaType;

/**
 * Copyright (C)
 * FileName: Constants
 * Author: 员外
 * Date: 2020-01-02 14:00
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class Constants {
    //url基地址——测试地址
    public static final String appBaseUrl = "http://test.gegeda.vip/studentApp";


    public static final int TYPE_GET = 0;//get请求
    public static final int TYPE_POST_FORM = 2;//post请求参数为表单
    public static final int TYPE_POST_JSON = 1;//post请求参数为json
    public static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

    public static final String USER_TOKEN = "userToken";//

    //渠道用户登录
    public static final String channelUserLogin = "system/channelUserLogin";
    //快答提交
    public static final String createQuestion = "question/createQuestion";
    //重新提交秒答
    public static final String resubmitQuestion = "question/resubmitQuestion";
    //获取未完成的秒答详情
    public static final String getIncompleteQuestion = "question/getIncompleteQuestion";
    //取消秒答
    public static final String cancelQuestion = "question/cancelQuestion";
    //完成秒答
    public static final String finishQuestion = "question/finishQuestion";
    //学生app进入秒答教室接口
    public static final String enterQuestion = "question/enterQuestion";
}
