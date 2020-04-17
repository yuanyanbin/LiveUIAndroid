package com.baijiayun.live.module;

/**
 * ClassName: DownTimerListener
 * Description: TODO<倒计时监听类>
 * Author: Zuobb
 * Date: 2019/5/18 14:49
 * Version: v1.0
 */
public interface DownTimerListener {

    /**
     * 倒计时每秒调用方法
     *
     * @param millisUntilFinished 倒计时剩余秒数
     */
    void onTick(long millisUntilFinished);

    /**
     * 倒计时完成方法
     */
    void onFinish();
}

