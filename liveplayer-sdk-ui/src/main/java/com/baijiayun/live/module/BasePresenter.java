package com.baijiayun.live.module;

/**
 * Copyright (C)
 * FileName: BasePresenter
 * Author: 员外
 * Date: 2019-06-21 15:16
 * Description: TODO<MVP中所有Presenter的接口，完成view的绑定和解除>
 * Version: 1.0
 */
public interface BasePresenter<T extends BaseView> {
    /**
     * 注入View，使之能够与View相互响应
     *
     * @param view
     */
    void attachView(T view);

    /**
     * 释放资源
     */
    void detachView();

}