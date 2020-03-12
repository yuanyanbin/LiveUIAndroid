package com.ggd.live.ui.data;

/**
 * Copyright (C)
 * FileName: StudentDetailBean
 * Author: 员外
 * Date: 2020-02-25 21:24
 * Description: TODO<Java类描述>
 * Version: 1.0
 */
public class StudentDetailBean {

    /**
     * code : 0
     * data : {"id":1380,"status":1,"sex":0,"inviteType":4,"inviterId":2033068,"inviteUrl":"http://test.gegeda.vip/website/share/inviteStudent.html","balance":30,"score":0,"grade":11,"isOpenPush":true,"gradeName":"一年级","loginToken":"246a0e77816bbf432bb065198e3ea93e","isCompleted":true,"questionPrice":3,"questionValidSeconds":92,"payValidSeconds":1800,"privateDomain":"b96133408","isNewUser":false}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1380
         * status : 1
         * sex : 0
         * inviteType : 4
         * inviterId : 2033068
         * inviteUrl : http://test.gegeda.vip/website/share/inviteStudent.html
         * balance : 30.0
         * score : 0
         * grade : 11
         * isOpenPush : true
         * gradeName : 一年级
         * loginToken : 246a0e77816bbf432bb065198e3ea93e
         * isCompleted : true
         * questionPrice : 3.0
         * questionValidSeconds : 92
         * payValidSeconds : 1800
         * privateDomain : b96133408
         * isNewUser : false
         */

        private int id;
        private int status;
        private int sex;
        private int inviteType;
        private int inviterId;
        private String inviteUrl;
        private double balance;
        private int score;
        private int grade;
        private boolean isOpenPush;
        private String gradeName;
        private String loginToken;
        private boolean isCompleted;
        private double questionPrice;
        private int questionValidSeconds;
        private int payValidSeconds;
        private String privateDomain;
        private boolean isNewUser;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public int getInviteType() {
            return inviteType;
        }

        public void setInviteType(int inviteType) {
            this.inviteType = inviteType;
        }

        public int getInviterId() {
            return inviterId;
        }

        public void setInviterId(int inviterId) {
            this.inviterId = inviterId;
        }

        public String getInviteUrl() {
            return inviteUrl;
        }

        public void setInviteUrl(String inviteUrl) {
            this.inviteUrl = inviteUrl;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getGrade() {
            return grade;
        }

        public void setGrade(int grade) {
            this.grade = grade;
        }

        public boolean isIsOpenPush() {
            return isOpenPush;
        }

        public void setIsOpenPush(boolean isOpenPush) {
            this.isOpenPush = isOpenPush;
        }

        public String getGradeName() {
            return gradeName;
        }

        public void setGradeName(String gradeName) {
            this.gradeName = gradeName;
        }

        public String getLoginToken() {
            return loginToken;
        }

        public void setLoginToken(String loginToken) {
            this.loginToken = loginToken;
        }

        public boolean isIsCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        public double getQuestionPrice() {
            return questionPrice;
        }

        public void setQuestionPrice(double questionPrice) {
            this.questionPrice = questionPrice;
        }

        public int getQuestionValidSeconds() {
            return questionValidSeconds;
        }

        public void setQuestionValidSeconds(int questionValidSeconds) {
            this.questionValidSeconds = questionValidSeconds;
        }

        public int getPayValidSeconds() {
            return payValidSeconds;
        }

        public void setPayValidSeconds(int payValidSeconds) {
            this.payValidSeconds = payValidSeconds;
        }

        public String getPrivateDomain() {
            return privateDomain;
        }

        public void setPrivateDomain(String privateDomain) {
            this.privateDomain = privateDomain;
        }

        public boolean isIsNewUser() {
            return isNewUser;
        }

        public void setIsNewUser(boolean isNewUser) {
            this.isNewUser = isNewUser;
        }
    }
}
