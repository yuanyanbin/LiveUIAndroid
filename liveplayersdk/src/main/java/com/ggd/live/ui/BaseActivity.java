package com.ggd.live.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


/**
 * Copyright (C)
 * FileName: BaseActivity
 * Author: 员外
 * Date: 2020-02-20 11:27
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext = null;
    private LoadingDialog mLoadingDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getContentViewID() != 0) {
            setContentView(getContentViewID());
        }
        mContext = this;
        createLoadingDialog(mContext);
        initViewsAndEvents();
    }


    /**
     * 创建LoadingDialog
     */
    public void createLoadingDialog(Context mContext) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(mContext);
            mLoadingDialog.setCanceledOnTouchOutside(false);
            mLoadingDialog.setLoadText("正在加载...");
        }
    }

    /**
     * 设置dialog显示的文字
     */
    public void setLoadingDialodText(String msg) {
        mLoadingDialog.setLoadText(msg);
    }

    /**
     * 显示LoadingDialog
     */
    public void showLoadingDialog() {
        if (mContext != null && !mContext.isFinishing()
                && mLoadingDialog != null && !mLoadingDialog.isShowing()) {
            mLoadingDialog.show();
        }
    }

    /**
     * 关闭LoadingDialog
     */
    public void closeLoadingDialog() {
        if (mContext != null && !mContext.isFinishing()
                && mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            setLoadingDialodText("正在加载...");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * bind layout resource file
     */
    protected abstract int getContentViewID();

    /**
     * init views and events here
     */
    protected abstract void initViewsAndEvents();
}
