package com.ggd.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ggd.live.httputils.util.ToastUtil;
import com.ggd.live.ui.AnswerAwaitActivity;
import com.ggd.live.ui.AnswerSDKWithUI;

public class MainActivity extends AppCompatActivity {
    private String subject = "1";
    private String imageUrl = "http://ggda-test.oss-cn-beijing.aliyuncs.com/problem/ef04794100358596c3c77eccdbb7c7a0.jpeg";
    private String description = "北师测试";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hello_button).setOnClickListener(view -> {
            AnswerSDKWithUI.enterAnswer(this, "test", "2033068", "3000553", "89", "11",
                    "13453390514", subject, imageUrl, description, new AnswerSDKWithUI.AnswerSDKListener() {
                        @Override
                        public void onError(String msg) {
                            ToastUtil.showLong(MainActivity.this, msg);
                        }

                        @Override
                        public void onLiveFinish(String msg) {
                            ToastUtil.showLong(MainActivity.this, msg);
                        }

                        @Override
                        public void onLiveCancel(String msg) {
                            ToastUtil.showLong(MainActivity.this, msg);
                        }
                    });
        });

    }
}
