package com.ggd.live.ui;

/**
 * Copyright (C)
 * FileName: UnfinishedAnswerBean
 * Author: 员外
 * Date: 2019-08-23 19:09
 * Description: TODO<未完成秒答>
 * Version: 1.0
 */
public class UnfinishedAnswerBean {


    /**
     * code : 0
     * data : {"id":72,"subject":4,"subjectName":"物理","description":"123","imageUrl":"http://ggda-test.oss-cn-beijing.aliyuncs.com/problem/28d1691f5358f34076f9b7e908d889be.jpeg","status":0,"isPay":false,"createdTime":"2019-08-23 19:08:09","remainSec":48,"timeout":60}
     */

    private int code;
    private QuestionDetailBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public QuestionDetailBean getData() {
        return data;
    }

    public void setData(QuestionDetailBean data) {
        this.data = data;
    }

}
