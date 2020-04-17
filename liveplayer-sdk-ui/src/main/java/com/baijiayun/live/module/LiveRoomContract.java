package com.baijiayun.live.module;

/**
 * Copyright (C)
 * FileName: LiveRoomContract
 * Author: 员外
 * Date: 2020/3/31 9:32 AM
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public interface LiveRoomContract {

    interface Presenter extends BasePresenter {
        /**
         * 学生app进入秒答教室
         *
         * @param id
         */
        void enterQuestion(String id);

        /**
         * 直播间下课日志记录
         *
         * @param id
         */
        void finishLive( String id);
    }

    interface View extends BaseView {

        void onFinish();
    }

}
