package com.baijiayun.live.module;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.baijiayun.live.httputils.constant.Constants;
import com.baijiayun.live.httputils.remote.HttpManager;
import com.baijiayun.live.httputils.remote.ReqCallBack;
import com.baijiayun.live.httputils.util.GsonUtil;
import com.baijiayun.live.httputils.util.LogUtil;
import com.baijiayun.live.module.data.QuestionDetailBean;
import com.baijiayun.live.module.data.UnfinishedAnswerBean;


/**
 * Copyright (C)
 * FileName: AnswerSDKWithUI
 * Author: 员外
 * Date: 2020-02-24 13:32
 * Description: TODO<入口类>
 * Version: 1.0
 */
public class AnswerSDKWithUI {

    /**
     * 进入秒答
     *
     * @param context
     * @param name        客户名称
     * @param sign        加密信息
     * @param userId      第三方标识
     * @param classId     班级标识
     * @param schoolId    学校标识
     * @param grade       年级
     * @param phone       手机号
     * @param subject     学科
     * @param imageUrl    图片链接url
     * @param description 问题描述
     * @param listener
     */
    public static void enterAnswer(Context context, String userId,
                                   String grade, String phone, String subject, String imageUrl, String description, AnswerSDKListener listener) {
        if (TextUtils.isEmpty(userId)) {
            listener.onError("thirdId is empty");
            return;
        }
        if (TextUtils.isEmpty(grade)) {
            listener.onError("grade is empty");
            return;
        }
        if (TextUtils.isEmpty(subject)) {
            listener.onError("subject is empty");
            return;
        }

        Intent intent = new Intent(context, AnswerAwaitActivity.class);
        intent.putExtra("userId", userId);
        intent.putExtra("grade", grade);
        intent.putExtra("phone", phone);
        intent.putExtra("subject", subject);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("description", description);
        context.startActivity(intent);
        AnswerAwaitActivity.setSDKListener(listener);
    }

    /**
     * 检查秒答状态
     *
     * @param context
     */
    public static void getIncompleteQuestion(Context context, AnswerSDKListener listener) {
        HttpManager.getInstance(context).getIncompleteQuestion(new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UnfinishedAnswerBean unfinishedAnswerBean = GsonUtil.getGson().fromJson(result, UnfinishedAnswerBean.class);
                QuestionDetailBean data = unfinishedAnswerBean.getData();
                if (TextUtils.isEmpty(data.getId())) {
                    return;
                }
                int status = data.getStatus();//问题状态(-1:已失效; 0:未应答; 1:解答中; 2:待支付; 3:已完成)
                if (status == 0 || status == 1) {
                    Intent intent = new Intent(context, AnswerAwaitActivity.class);
                    intent.putExtra("data", data);
                    context.startActivity(intent);
                    AnswerAwaitActivity.setSDKListener(listener);
                }

            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {

            }
        });
    }

    /**
     * 设置网络环境
     *
     * @param b true:正式环境
     */
    public static void setHttpSite(boolean b) {
        Constants.HTTP_SITE = b;
        LogUtil.setLog(!b);
    }

    /**
     * 设置公司标识
     * @param company
     * @param companyToken
     */
    public static void setCompanyName(String company,String companyToken) {
        Constants.company = company;
        Constants.companyToken = companyToken;
    }

    public interface AnswerSDKListener {

        /**
         * 错误回调
         *
         * @param msg
         */
        void onError(String msg);

        /**
         * 完成秒答回调
         *
         * @param id          老师id
         * @param teacherName 老师名称
         * @param duration    时长
         */
        void onLiveFinish(String id, String teacherName, long duration);

        /**
         * 取消秒答回调
         *
         * @param msg
         */
        void onLiveCancel(String msg);
    }


}
