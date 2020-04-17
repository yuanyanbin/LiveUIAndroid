package com.baijiayun.live.module;

/**
 * Copyright (C)
 * FileName: TimeUtil
 * Author: 员外
 * Date: 2020-02-20 12:23
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class TimeUtil {

    /**
     * 根据秒数获取时长
     *
     * @param length 单位秒
     * @return 格式 23:34
     */
    public static String getTimeString(long length) {
        long sec = length % 60;
        long min = length / 60;
        String secStr;
        String minStr;
        if (sec >= 10) {
            secStr = String.valueOf(sec);
        } else {
            secStr = "0" + sec;
        }
        if (min >= 10) {
            minStr = String.valueOf(min);
        } else {
            minStr = "0" + min;
        }
        return minStr + ":" + secStr;
    }
}
