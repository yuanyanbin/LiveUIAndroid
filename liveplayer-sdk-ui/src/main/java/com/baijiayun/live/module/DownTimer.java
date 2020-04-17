package com.baijiayun.live.module;

import android.os.CountDownTimer;

import com.baijiayun.live.httputils.util.LogUtil;


/**
 * ClassName: DownTimer
 * Description: TODO<倒计时类>
 * Author: 员外
 * Date: 2019/5/18 14:49
 * Version: v1.0
 */
public class DownTimer {

    private final String TAG = DownTimer.class.getSimpleName();
    private CountDownTimer mCountDownTimer;
    private DownTimerListener listener;

    /**
     * 开始倒计时功能（倒计为time长的时间，时间间隔为每秒）
     *
     * @param time 倒计时时长
     */
    public void startDown(long time) {
        startDown(time, 1000);
    }

    /**
     * 开始倒计时功能（倒计为time长的时间，时间间隔为mills）
     *
     * @param time  倒计时时长
     * @param mills 时间间隔
     */
    public void startDown(long time, long mills) {
        mCountDownTimer = new CountDownTimer(time, mills) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (listener != null) {
                    listener.onTick(millisUntilFinished);
                } else {
                    LogUtil.e(TAG, "DownTimerListener 监听不能为空");
                }
            }

            @Override
            public void onFinish() {
                if (listener != null) {
                    listener.onFinish();
                } else {
                    LogUtil.e(TAG, "DownTimerListener 监听不能为空");
                }
                if (mCountDownTimer != null) mCountDownTimer.cancel();
            }

        }.start();
    }

    /**
     * 停止倒计时功能
     */
    public void stopDown() {
        if (mCountDownTimer != null) mCountDownTimer.cancel();
    }

    /**
     * 设置倒计时监听
     */
    public void setListener(DownTimerListener listener) {
        this.listener = listener;
    }
}

