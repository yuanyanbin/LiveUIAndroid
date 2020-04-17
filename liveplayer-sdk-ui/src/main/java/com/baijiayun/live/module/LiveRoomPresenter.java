package com.baijiayun.live.module;

import android.content.Context;

import com.baijiayun.live.httputils.remote.HttpManager;
import com.baijiayun.live.httputils.remote.ReqCallBack;


/**
 * Copyright (C)
 * FileName: LiveRoomPresenter
 * Author: 员外
 * Date: 2020/3/31 9:35 AM
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class LiveRoomPresenter implements LiveRoomContract.Presenter {

    private Context mContext;
    private LiveRoomContract.View mView;

    public LiveRoomPresenter(Context context) {
        this.mContext = context;
    }

    /**
     * 学生app进入秒答教室
     *
     * @param id
     */
    @Override
    public void enterQuestion(String id) {
        HttpManager.getInstance(mContext).enterQuestion(id, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {

            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {

            }
        });
    }

    /**
     * 直播间上课日志记录
     *
     * @param id
     */
    @Override
    public void finishLive( String id) {
        //完成上课
        HttpManager.getInstance(mContext).finishQuestion(id, new ReqCallBack<String>() {
            @Override
            public void onReqSuccess(String result) {
               if (mView != null){
                   mView.onFinish();
               }
            }

            @Override
            public void onReqFailed(int errCode, String errorMsg) {
                if (mView != null){
                    mView.onFinish();
                }
            }
        });
    }

    @Override
    public void attachView(BaseView view) {
        this.mView = (LiveRoomContract.View) view;
    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
