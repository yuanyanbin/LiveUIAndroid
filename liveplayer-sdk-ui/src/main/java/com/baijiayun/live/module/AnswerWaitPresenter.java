package com.baijiayun.live.module;

import android.content.Context;
import com.baijiayun.live.httputils.constant.Constants;
import com.baijiayun.live.httputils.remote.HttpManager;
import com.baijiayun.live.httputils.remote.ReqCallBack;
import com.baijiayun.live.httputils.util.GsonUtil;
import com.baijiayun.live.httputils.util.SharedPreferencesUtil;
import com.baijiayun.live.httputils.util.ToastUtil;
import com.baijiayun.live.module.data.StudentDetailBean;
import com.baijiayun.live.module.data.UnfinishedAnswerBean;


/**
 * Copyright (C)
 * FileName: AnswerWaitPresenter
 * Author: 员外
 * Date: 2019-07-11 15:11
 * Description: TODO<秒答等待>
 * Version: 1.0
 */
public class AnswerWaitPresenter implements AnswerWaitContract.Presenter {
    private Context mContext;
    private AnswerWaitContract.View mView;

    public AnswerWaitPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void attachView(BaseView view) {
        this.mView = (AnswerWaitContract.View) view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    /**
     * 渠道用户登录
     *
     * @param userId   第三方标识
     * @param grade    年级
     * @param phone    手机号
     */
    @Override
    public void channelUserLogin(String userId,String grade, String phone) {
        HttpManager.getInstance(mContext).channelUserLogin( userId,grade, phone, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                if (mView != null) {
                    StudentDetailBean bean = GsonUtil.getGson().fromJson(result, StudentDetailBean.class);
                    String token = bean.getData().getLoginToken();
                    SharedPreferencesUtil.setString(mContext, Constants.USER_TOKEN, token);

                    mView.userLoginSuccess();
                }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {
                if (mView != null) {
                    mView.userLoginFailed(errCode, errorMsg);
                }
            }
        });
    }

    /**
     * 创建秒答
     *
     * @param imageUrl    图片链接url
     * @param description 描述
     * @param subjectId   学科
     */
    @Override
    public void createQuestion(String imageUrl, String description, String subjectId) {
        HttpManager.getInstance(mContext).createQuestion(imageUrl, description, subjectId, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UnfinishedAnswerBean unfinishedAnswerBean = GsonUtil.getGson().fromJson(result, UnfinishedAnswerBean.class);
                if (mView != null) {
                    mView.createQuestionSuccess(unfinishedAnswerBean.getData());
                }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {
                if (mView != null) {
                    ToastUtil.showLong(mContext, errorMsg);
                    mView.createQuestionFailed(errCode, errorMsg);
                }
            }
        });
    }

    /**
     * 重新提交秒答
     *
     * @param id
     */
    @Override
    public void resubmitQuestion(String id) {
        HttpManager.getInstance(mContext).resubmitQuestion(id, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                if (mView != null) {
                    mView.resubmitQuestionSuccess();
                }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {
                if (mView != null) {
                    ToastUtil.showShort(mContext, errorMsg);
                    mView.failed(errCode, errorMsg);
                }
            }
        });
    }

    /**
     * 取消秒答
     *
     * @param id
     */
    @Override
    public void cancelQuestion(String id) {
        HttpManager.getInstance(mContext).cancelQuestion(id, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                if (mView != null) {
                    mView.cancelQuestionSuccess();
                }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {
                if (mView != null) {
                    mView.cancelQuestionFailed(errCode, errorMsg);
                }
            }
        });
    }

    /**
     * 获取未完成的秒答详情
     */
    @Override
    public void getIncompleteQuestion() {
        HttpManager.getInstance(mContext).getIncompleteQuestion(new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
                UnfinishedAnswerBean unfinishedAnswerBean = GsonUtil.getGson().fromJson(result, UnfinishedAnswerBean.class);
                if (mView != null) {
                    mView.getIncompleteQuestionSuccess(unfinishedAnswerBean.getData());
                }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {

            }
        });
    }

}
