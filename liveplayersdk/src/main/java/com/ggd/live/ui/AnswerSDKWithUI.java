package com.ggd.live.ui;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

/**
 * Copyright (C)
 * FileName: AnswerSDKWithUI
 * Author: 员外
 * Date: 2020-02-24 13:32
 * Description: TODO<入口类>
 * Version: 1.0
 */
public class AnswerSDKWithUI {

    public static void enterAnswer(Context context, String name, String userId, String classId, String schoolId,
                                   String grade, String phone, String subject, String imageUrl, String description, AnswerSDKListener listener) {
        if (TextUtils.isEmpty(userId)) {
            listener.onError("thirdId is empty");
            return;
        }
        if (TextUtils.isEmpty(classId)) {
            listener.onError("classId is empty");
            return;
        }
        if (TextUtils.isEmpty(schoolId)) {
            listener.onError("schoolId is empty");
            return;
        }
        if (TextUtils.isEmpty(grade)) {
            listener.onError("grade is empty");
            return;
        }
        if (TextUtils.isEmpty(subject)) {
            listener.onError("subject is empty");
            return;
        }

        Intent intent = new Intent(context, AnswerAwaitActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("userId", userId);
        intent.putExtra("classId", classId);
        intent.putExtra("schoolId", schoolId);
        intent.putExtra("grade", grade);
        intent.putExtra("phone", phone);
        intent.putExtra("subject", subject);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("description", description);
        context.startActivity(intent);
        AnswerAwaitActivity.setSDKListener(listener);
    }

    public interface AnswerSDKListener {

        void onError(String msg);

        void onLiveFinish(String msg);

        void onLiveCancel(String msg);
    }


}
