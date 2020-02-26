package com.ggd.live.httputils.remote;


/**
 * Copyright (C)
 * FileName: HttpManager
 * Author: 员外
 * Date: 2019-05-23 16:53
 * Description: TODO<>
 * Version: 1.0
 */
public abstract class ProgressCallBack<T> implements ReqCallBack{

    public abstract void onProgress(long totalBytes, long remainingBytes, boolean done);
}