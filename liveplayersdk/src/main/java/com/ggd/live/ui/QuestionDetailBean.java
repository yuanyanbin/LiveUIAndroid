package com.ggd.live.ui;

import java.io.Serializable;

/**
 * Copyright (C)
 * FileName: QuestionDetailBean
 * Author: 员外
 * Date: 2019-08-23 19:19
 * Description: TODO<秒答详情>
 * Version: 1.0
 */
public class QuestionDetailBean implements Serializable {

    /**
     * id : 72
     * subject : 4
     * subjectName : 物理
     * description : 123
     * imageUrl : http://ggda-test.oss-cn-beijing.aliyuncs.com/problem/28d1691f5358f34076f9b7e908d889be.jpeg
     * status : 0
     * isPay : false
     * createdTime : 2019-08-23 19:08:09
     * remainSec : 48
     * timeout : 60
     */

    private String id;
    private int subject;
    private String subjectName;
    private String description;
    private String imageUrl;
    private int status;
    private boolean isPay;
    private String createdTime;
    private int remainSec;
    private int timeout;
    private String studentCode;
    private int duration;
    private String teacherAvatar;
    private String teacherNick;
    private String teacherId;
    private int teacherStar;
    private int teacherDuration;

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherDuration(int teacherDuration) {
        this.teacherDuration = teacherDuration;
    }

    public int getTeacherDuration() {
        return teacherDuration;
    }

    public void setTeacherStar(int teacherStar) {
        this.teacherStar = teacherStar;
    }

    public int getTeacherStar() {
        return teacherStar;
    }

    public void setTeacherNick(String teacherNick) {
        this.teacherNick = teacherNick;
    }

    public String getTeacherNick() {
        return teacherNick;
    }

    public void setTeacherAvatar(String teacherAvatar) {
        this.teacherAvatar = teacherAvatar;
    }

    public String getTeacherAvatar() {
        return teacherAvatar;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSubject() {
        return subject;
    }

    public void setSubject(int subject) {
        this.subject = subject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isPay() {
        return isPay;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public int getRemainSec() {
        return remainSec;
    }

    public void setRemainSec(int remainSec) {
        this.remainSec = remainSec;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
