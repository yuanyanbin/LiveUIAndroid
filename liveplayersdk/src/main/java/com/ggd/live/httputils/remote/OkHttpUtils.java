package com.ggd.live.httputils.remote;

import android.content.Context;


import com.ggd.live.httputils.constant.Constants;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Random;

import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;

import static com.ggd.live.httputils.constant.Constants.MEDIA_TYPE_JSON;


/**
 * Copyright (C)
 * FileName: OkHttpUtils
 * Author: 员外
 * Date: 2019-05-23 16:26
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class OkHttpUtils {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @return
     */
    public static Request requestGetByAsyn(Context context, String actionUrl, HashMap<String, String> paramsMap) throws Exception {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(String.format("%s=%s", key, paramsMap.get(key)));
            pos++;
        }

        String mBaseUrl = Constants.appBaseUrl;
        String requestUrl = String.format("%s/%s?%s", mBaseUrl, actionUrl, tempParams.toString());
        Request request = addHeaders(context)
                //.cacheControl(new CacheControl.Builder().maxAge(6000, TimeUnit.SECONDS).build())
                //.cacheControl(new CacheControl.Builder().maxStale(30, TimeUnit.SECONDS).build())
                //.cacheControl(new CacheControl.Builder().maxAge(0, TimeUnit.SECONDS).build())
                .url(requestUrl)
                .build();
        return request;
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @return
     */
    public static Request requestPostByAsyn(Context context, String actionUrl, HashMap<String, String> paramsMap) throws Exception {
        StringBuilder tempParams = new StringBuilder();
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                tempParams.append("&");
            }
            tempParams.append(paramsMap.get(key));
            pos++;
        }
        String params = tempParams.toString();
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
        String mBaseUrl = Constants.appBaseUrl;
        String requestUrl = String.format("%s/%s", mBaseUrl, actionUrl);
        Request request = addHeaders(context).url(requestUrl).post(body).build();
        return request;
    }

    /**
     * okHttp post异步请求表单提交
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @return
     */
    public static Request requestPostByAsynWithForm(Context context, String actionUrl, HashMap<String, String> paramsMap) throws Exception {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            builder.add(key, paramsMap.get(key));
        }
        RequestBody formBody = builder.build();
        String mBaseUrl = Constants.appBaseUrl;
        String requestUrl = String.format("%s/%s", mBaseUrl, actionUrl);
        Request request = addHeaders(context).url(requestUrl).post(formBody).build();
        return request;
    }

    /**
     * 统一为请求添加头信息
     * token	String	否	用户信息（加密规则见下文）
     * sign     String  是  签名信息（签名规则见下文）
     * signTime String  是	当前时间，格式：yyyyMMddHHmmss
     * userType	String 	是	用户类型：1老师 2学生
     * deviceId	String 	是	设备ID
     * version	String 	是	APP类型（大写）-版本，示例：IOS-0.0.1
     * platform	String 	是	APP平台，固定值：KUAKAO-OTM-STUDENT

     * APP端token加密算法：
     *      使用RSA加密算法，加密私钥通过版本更新接口获取
     *      token字段加密内容规则为：userId-userType
     *      未登录时userId可传0
     * APP端sign签名算法
     *      使用RSA签名算法（SHA1WithRSA），加密私钥通过版本更新接口获取
     *      sign字段加密内容规则为：platform-version-signTime
     *
     * 客户端私钥：MIIBUwIBADANBgkqhkiG9w0BAQEFAASCAT0wggE5AgEAAkEAnMMaNkfltlJWswWsUhpKHiZ5azMSYueB6kn5YeV0+EB+n0EaLEATTuXqGuTjHYY5fsLbINymG5WXFdh5pTAOaQIDAQABAkBiXV4+P0EZ9XKnpOCrKAnn/1Zi+MoJu6DehYpxbkzGVJ4+bgT2kGt/ef/cFPFVF8/2NeZfQxssjaznfdxqmEP9AiEA0PQ0W9Bdv6EryuWkk+vDTAu5lHJKKiE772ZjVs1Yf18CIQDADqOqYOfMZ7PdC7lU1ycBAkX5g1QsXAGb2aW7FZ3vNwIgbhsgs/jaTa46C1JzJNcpNBtBkS2gUw7sLDyBwC24onUCICbgT7zjVMiJjV0HTIKh8qE/po51SQqZrTGxAt016PxNAiB1Ub4KA7rKnzfwobhOuDexMiNYBksOq4qOp9p1PtN2OA==
     *      platform：KUAKAO-OTM-STUDENT
     *      version：IOS-0.0.1
     *      signTime ：20170926150135
     *      userType：2
     *      userId：9999
     *      最终生成token：ADpYqU7JB+JH+qjN4lJ6RT7D3xDOPjmcIIutDpXzHAwl56+xtQiVzefOKynlpQltpoRNvfTaovDvsjHX7Q3r2g==
     *      最终生成sign：mfaqQ5YzVpP8QfFzb4RepFutXLTKRnC3QISxb1GSk8rKzZx8x/KE2SByK9KWI+v0bOWPHU4og5q1h6Ol6vlY/g==
     *
     * @return
     */
    public static Request.Builder addHeaders(Context context) {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive");
//                .header("token", token)
        return builder;
    }
    //获取随机数
    private static String getRandom(){
        return String.valueOf(new Random().nextLong());
    }
}
