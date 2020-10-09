package com.baijiayun.live.module;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baijiayun.glide.Glide;
import com.baijiayun.live.httputils.util.ToastUtil;
import com.baijiayun.live.module.data.QuestionDetailBean;
import com.baijiayun.live.ui.LiveSDKWithUI;
import com.baijiayun.live.ui.R;


/**
 * Copyright (C)
 * FileName: AnswerAwaitActivity
 * Author: 员外
 * Date: 2020-02-10 10:39
 * Description: TODO<秒答等待Activity>
 * Version: 1.0
 */
public class AnswerAwaitActivity extends BaseActivity implements AnswerWaitContract.View, DownTimerListener {
    /* 房间外部回调 */
    private static AnswerSDKWithUI.AnswerSDKListener answerSDKListener;

    private AnswerWaitPresenter presenter;
    private String id;
    private int timeout;
    private DownTimer mDownTimer;
    private long millisUntilFinished;
    private CommonDialog cancelDialog;
    private String joinCode;

    private TextView countDownTv;
    private RelativeLayout countDownRl;
    private RelativeLayout enterLiveRl;
    private ImageView roundImageView;
    private TextView nickNameTv;
    private TextView subjectNameTv;
    private TextView teachDurationTv;
    private TextView starClassTv;

    private String subject;
    private String imageUrl;
    private String description;
    private String name;
    private String sign;
    private String userId;
    private String classId;
    private String schoolId;
    private String grade;
    private String phone;

    private long startTime;
    private long endTime;
    private String teacherNick;
    private String teacherId;
    private boolean isStartClass;
    private CommonDialog enterDialog;

    public static void setSDKListener(AnswerSDKWithUI.AnswerSDKListener listener) {
        answerSDKListener = listener;
    }


    @Override
    protected int getContentViewID() {
        return R.layout.activity_answer_await;
    }

    @Override
    protected void initViewsAndEvents() {
        presenter = new AnswerWaitPresenter(this);
        presenter.attachView(this);

        QuestionDetailBean data = (QuestionDetailBean) getIntent().getSerializableExtra("data");
        getIntentData();
        initView();

        if (data != null) {
            setQuestionStatus(data);
        } else {
            showLoadingDialog();
            presenter.channelUserLogin(name,sign, userId, classId, schoolId, grade, phone);
        }

    }

    private void setQuestionStatus(QuestionDetailBean data) {
        mDownTimer = new DownTimer();
        mDownTimer.setListener(this); //开启定时器
        int status = data.getStatus();//问题状态(-1:已失效; 0:未应答; 1:解答中; 2:待支付; 3:已完成)
        timeout = data.getRemainSec();
        id = data.getId();
        if (status == 0) {
            //等待中
            isStartClass = false;
            mDownTimer.startDown((timeout + 5) * 1000);
            countDownRl.setVisibility(View.VISIBLE);
            enterLiveRl.setVisibility(View.GONE);

        } else if (status == 1) {
            //进入课堂
            isStartClass = true;
            countDownRl.setVisibility(View.GONE);
            enterLiveRl.setVisibility(View.VISIBLE);
            mDownTimer.stopDown();
            setData(data);
        }
    }

    private void getIntentData() {
        name = getIntent().getStringExtra("name");
        sign = getIntent().getStringExtra("sign");
        userId = getIntent().getStringExtra("userId");
        classId = getIntent().getStringExtra("classId");
        schoolId = getIntent().getStringExtra("schoolId");
        grade = getIntent().getStringExtra("grade");
        phone = getIntent().getStringExtra("phone");
        subject = getIntent().getStringExtra("subject");
        imageUrl = getIntent().getStringExtra("imageUrl");
        description = getIntent().getStringExtra("description");
    }

    private void initView() {
        countDownTv = findViewById(R.id.countDown_tv);
        countDownRl = findViewById(R.id.countDown_rl);
        enterLiveRl = findViewById(R.id.enter_live_rl);
        roundImageView = findViewById(R.id.teacher_head_iv);
        nickNameTv = findViewById(R.id.nickName_tv);
        subjectNameTv = findViewById(R.id.subject_name_tv);
        teachDurationTv = findViewById(R.id.teachDuration_tv);
        starClassTv = findViewById(R.id.starClass_tv);

        findViewById(R.id.cancel_tv).setOnClickListener(view -> {
            //放弃等待
            showCancelDialog();
        });
        findViewById(R.id.continue_tv).setOnClickListener(view -> {
            //继续等待
            if (millisUntilFinished == 0L) {
                presenter.resubmitQuestion(id);
            } else {
                ToastUtil.showLong(mContext, "系统正在向老师派单，请您耐心等待");
            }
        });
        findViewById(R.id.enter_live_tv).setOnClickListener(view -> {
            enterLive();
        });

    }

    //进入教室
    private void enterLive() {
        if (startTime == 0L){
            startTime = System.currentTimeMillis();
        }

        LiveSDKWithUI.setLiveSDKFinishListener(new LiveSDKWithUI.LiveSDKFinishListener() {
            @Override
            public void onBack() {
                //进入课堂
                isStartClass = true;
                countDownRl.setVisibility(View.GONE);
                enterLiveRl.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                //上课完成
                endTime = System.currentTimeMillis();
                long duration = endTime - startTime;
                answerSDKListener.onLiveFinish(teacherId, teacherNick, duration);
                finish();
            }
        });

        LiveSDKWithUI.enterRoom(AnswerAwaitActivity.this, joinCode, TextUtils.isEmpty(name) ? "beishi" : name,id, s -> {
            ToastUtil.showShort(AnswerAwaitActivity.this, s);
        });
    }


    @Override
    public void userLoginSuccess() {
        presenter.createQuestion(imageUrl, description, subject);
    }

    @Override
    public void userLoginFailed(int errCode, String errorMsg) {
        closeLoadingDialog();
        if (answerSDKListener != null) {
            finish();
            answerSDKListener.onError(errorMsg);
        }
    }

    @Override
    public void resubmitQuestionSuccess() {
        mDownTimer.startDown((timeout + 5) * 1000);
    }

    @Override
    public void cancelQuestionSuccess() {
        closeLoadingDialog();
        mDownTimer.stopDown();
        answerSDKListener.onLiveCancel("放弃提问");
        finish();
    }

    @Override
    public void cancelQuestionFailed(int errCode, String errorMsg) {
        closeLoadingDialog();
        mDownTimer.stopDown();
        answerSDKListener.onLiveCancel("放弃提问");
        finish();
    }

    @Override
    public void failed(int errCode, String errorMsg) {
        closeLoadingDialog();
        ToastUtil.showLong(mContext, errorMsg);
    }

    @Override
    public void getIncompleteQuestionSuccess(QuestionDetailBean data) {
        if (data.getTimeout() == 0) {
            return;
        }
        int status = data.getStatus();//问题状态(-1:已失效; 0:未应答; 1:解答中; 2:待支付; 3:已完成)
        timeout = data.getTimeout();
        if (status == 1) {
            //解答中
            isStartClass = true;
            mDownTimer.stopDown();
            countDownRl.setVisibility(View.GONE);
            enterLiveRl.setVisibility(View.VISIBLE);
            setData(data);
        } else {
            isStartClass = false;
            countDownRl.setVisibility(View.VISIBLE);
            enterLiveRl.setVisibility(View.GONE);
        }
    }

    @Override
    public void createQuestionSuccess(QuestionDetailBean data) {
        closeLoadingDialog();
        countDownRl.setVisibility(View.VISIBLE);
        if (data != null) {
            this.id = data.getId();
            this.timeout = data.getTimeout();
            mDownTimer = new DownTimer();
            mDownTimer.setListener(this); //开启定时器
            mDownTimer.startDown((timeout + 5) * 1000);
        }
    }

    @Override
    public void createQuestionFailed(int errCode, String errorMsg) {
        closeLoadingDialog();
        finish();
    }

    private void setData(QuestionDetailBean data) {
        Glide.with(mContext).load(data.getTeacherAvatar()).into(roundImageView);
        teacherId = data.getTeacherId();
        teacherNick = data.getTeacherNick();
        nickNameTv.setText(data.getTeacherNick());
        subjectNameTv.setText(getSubjectName(data.getSubjectName()));
        teachDurationTv.setText(data.getTeacherDuration() + "分钟");
        starClassTv.setText(data.getTeacherStar() + "星");
        joinCode = data.getStudentCode();
    }

    @Override
    public void onTick(long millisUntilFinished) {
        this.millisUntilFinished = millisUntilFinished;
        long sec = millisUntilFinished / 1000;
        if (countDownTv != null)
            countDownTv.setText(TimeUtil.getTimeString(sec));
        if (sec % 5 == 0) {
            presenter.getIncompleteQuestion();
        }
    }

    @Override
    public void onFinish() {
        //关闭定时器
        this.millisUntilFinished = 0L;
        mDownTimer.stopDown();
        countDownTv.setText("00:00");
        presenter.resubmitQuestion(id);
    }

    @Override
    public void onBackPressed() {
        //放弃等待
        if (isStartClass) {
            showEnterDialog();
        } else {
            showCancelDialog();
        }

    }

    /**
     * 放弃等待
     */
    private void showCancelDialog() {
        if (cancelDialog == null) {
            cancelDialog = new CommonDialog(this);
            cancelDialog.setContent("是否放弃等待");
        }
        cancelDialog.setOnCancelListener("取消", dialog -> dialog.dismiss());
        cancelDialog.setOnConfirmListener("确认", dialog -> {
            dialog.dismiss();
            presenter.cancelQuestion(id);
        });
        cancelDialog.show();
    }

    /**
     * 进入教室
     */
    private void showEnterDialog() {
        if (enterDialog == null) {
            enterDialog = new CommonDialog(this);
            enterDialog.setContent("老师已接单与面对面沟通");
        }
        enterDialog.setOnCancelListener("取消", dialog -> dialog.dismiss());
        enterDialog.setOnConfirmListener("进入教室", dialog -> {
            dialog.dismiss();
            enterLive();
        });
        enterDialog.show();
    }


    private String getSubjectName(String subjectName) {
        if (!TextUtils.isEmpty(subjectName)) {
            return subjectName.substring(0, 1);
        }
        return "";
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDownTimer != null){
            mDownTimer.stopDown();
        }
        presenter.detachView();
    }


}
