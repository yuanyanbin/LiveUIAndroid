package com.ggd.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.baijiayun.live.httputils.util.ToastUtil;
import com.baijiayun.live.module.AnswerSDKWithUI;

public class MainActivity extends AppCompatActivity {
    private String subject = "12";
    private String imageUrl = "http://ggda-test.oss-cn-beijing.aliyuncs.com/problem/ef04794100358596c3c77eccdbb7c7a0.jpeg";
    private String description = "北师测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnswerSDKWithUI.setHttpSite(false);
        AnswerSDKWithUI.getIncompleteQuestion(this, new AnswerSDKWithUI.AnswerSDKListener() {
            @Override
            public void onError(String msg) {
                ToastUtil.showLong(MainActivity.this, msg);
            }

            @Override
            public void onLiveFinish(String id, String teacherName, long duration) {
                ToastUtil.showLong(MainActivity.this, String.valueOf(duration));
            }

            @Override
            public void onLiveCancel(String msg) {
                ToastUtil.showLong(MainActivity.this, msg);
            }
        });

        findViewById(R.id.hello_button).setOnClickListener(view -> {
            AnswerSDKWithUI.enterAnswer(this, "test", "2129460", "3002153", "279", "11",
                    "13453390514", subject, imageUrl, description, new AnswerSDKWithUI.AnswerSDKListener() {
                        @Override
                        public void onError(String msg) {
                            ToastUtil.showLong(MainActivity.this, msg);
                        }

                        @Override
                        public void onLiveFinish(String id, String teacherName, long duration) {
                            ToastUtil.showLong(MainActivity.this, String.valueOf(duration));
                        }

                        @Override
                        public void onLiveCancel(String msg) {
                            ToastUtil.showLong(MainActivity.this, msg);
                        }
                    });
        });

    }
}
