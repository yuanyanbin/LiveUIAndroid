package com.ggd.live.ui;


/**
 * Copyright (C)
 * FileName: AnswerWaitContract
 * Author: 员外
 * Date: 2019-07-11 15:01
 * Description: TODO<秒答等待接口>
 * Version: 1.0
 */
public interface AnswerWaitContract {

    interface View extends BaseView {

        void resubmitQuestionSuccess();

        void cancelQuestionSuccess();

        void failed(int errCode, String errorMsg);

        void cancelQuestionFailed(int errCode, String errorMsg);

        void getIncompleteQuestionSuccess(QuestionDetailBean data);

        void createQuestionSuccess(QuestionDetailBean data);

        void createQuestionFailed(int errCode, String errorMsg);

        void userLoginFailed(int errCode, String errorMsg);

        void userLoginSuccess();
    }

    interface Presenter extends BasePresenter {
        /**
         * 渠道用户登录
         *
         * @param name     客户名称
         * @param thirdId  第三方标识
         * @param classId  班级标识
         * @param schoolId 学校标识
         * @param grade    年级
         * @param phone    手机号
         */
        void channelUserLogin(String name, String thirdId, String classId, String schoolId, String grade, String phone);

        /**
         * 创建秒答
         *
         * @param imageUrl    图片链接url
         * @param description 描述
         * @param subjectId   学科
         */
        void createQuestion(String imageUrl, String description, String subjectId);

        /**
         * 重新提交秒答
         *
         * @param id
         */
        void resubmitQuestion(String id);

        /**
         * 取消秒答
         *
         * @param id
         */
        void cancelQuestion(String id);

        /**
         * 获取未完成的秒答详情
         */
        void getIncompleteQuestion();

        /**
         * 学生app进入秒答教室
         *
         * @param id
         */
        void enterQuestion(String id);

        /**
         * 完成秒答
         *
         * @param id
         */
        void finishQuestion(String id);

    }

}
