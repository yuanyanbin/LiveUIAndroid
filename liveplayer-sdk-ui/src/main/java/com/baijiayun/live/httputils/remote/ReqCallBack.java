package com.baijiayun.live.httputils.remote;


/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<>
 * Version: 1.0
 */

public interface ReqCallBack<T> {
    /**
     * 响应成功
     */
    void onReqSuccess(T result);

    /**
     * 响应失败
     */
    void onReqFailed(int errCode, String errorMsg);
}
