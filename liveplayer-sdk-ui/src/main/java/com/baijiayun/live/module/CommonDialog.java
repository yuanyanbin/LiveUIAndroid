package com.baijiayun.live.module;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.baijiayun.live.ui.R;


/**
 * 通用Dialog
 * Created by drowtram on 2017/10/18.
 */

public class CommonDialog extends Dialog {

    private String titleStr;
    private String contentStr;
    private String cancelMsg,confirmMsg;
    private OnCancelListener cancelListener;//点击按钮监听
    private OnConfirmListener confirmListener;//点击按钮监听
    private TextView dialog_common_title_tv;
    private TextView dialog_common_content_tv;
    private TextView dialog_common_cancel_tv;
    private TextView dialog_common_ok_tv;
    private boolean isHighLight; //是否全部高亮显示按钮
    private ImageView dialog_common_close_iv;
    private boolean isShowClose; //是否显示关闭按钮

    /**
     * 构造方法
     * @param context
     */
    public CommonDialog(@NonNull Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        // 设置按空白处不消失dialog
        setCanceledOnTouchOutside(false);
        initView();
        initData();
        initEvent();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        dialog_common_title_tv = findViewById(R.id.dialog_common_title_tv);
        dialog_common_content_tv = findViewById(R.id.dialog_common_content_tv);
        dialog_common_cancel_tv = findViewById(R.id.dialog_common_cancel_tv);
        dialog_common_ok_tv = findViewById(R.id.dialog_common_ok_tv);
        dialog_common_close_iv = findViewById(R.id.dialog_common_close_iv);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (!TextUtils.isEmpty(titleStr)) {
            dialog_common_title_tv.setVisibility(View.VISIBLE);
            dialog_common_title_tv.setText(titleStr);
        }
        if (!TextUtils.isEmpty(contentStr)) {
            dialog_common_content_tv.setText(contentStr);
        }
        if (!TextUtils.isEmpty(confirmMsg)) {
            dialog_common_ok_tv.setText(confirmMsg);
        }
        if (!TextUtils.isEmpty(cancelMsg)) {
            dialog_common_cancel_tv.setVisibility(View.VISIBLE);
            if (isHighLight) {
                dialog_common_cancel_tv.setTextColor(getContext().getResources().getColor(R.color.main_color));
            }
            dialog_common_cancel_tv.setText(cancelMsg);
        }
        if (isShowClose) {
            dialog_common_close_iv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置事件监听
     */
    private void initEvent() {
        dialog_common_ok_tv.setOnClickListener(v -> {
            if (confirmListener != null) {
                confirmListener.onClick(CommonDialog.this);
            }
            CommonDialog.this.dismiss();
        });
        dialog_common_cancel_tv.setOnClickListener(v -> {
            if (cancelListener != null) {
                cancelListener.onClick(CommonDialog.this);
            }
            CommonDialog.this.dismiss();
        });
        //关闭
        dialog_common_close_iv.setOnClickListener(v -> CommonDialog.this.dismiss());
    }

    /**
     * 设置dialog标题
     * @param title
     */
    public void setTitle(String title) {
        titleStr = title;
    }

    /**
     * 设置dialog标题
     * @param title
     */
    public void setTitle(int title) {
        titleStr = getContext().getResources().getString(title);
    }

    /**
     * 设置dialog消息内容
     * @param content
     */
    public void setContent(String content) {
        contentStr = content;
    }

    /**
     * 设置dialog消息内容
     * @param content
     */
    public void setContent(int content) {
        contentStr = getContext().getResources().getString(content);
    }

    /**
     * 设置取消按钮高亮显示
     * @param isHighLight 是否全部高亮显示，默认false
     */
    public void setHighLight(boolean isHighLight) {
        this.isHighLight = isHighLight;
    }

    /**
     * 设置是否显示关闭按钮
     * @param isShowClose
     */
    public void setShowClose(boolean isShowClose) {
        this.isShowClose = isShowClose;
    }

    /**
     * 设置按钮点击监听
     * @param msg
     * @param listener
     */
    public void setOnCancelListener(String msg, OnCancelListener listener) {
        if (!TextUtils.isEmpty(msg)) {
            this.cancelMsg = msg;
        }
        this.cancelListener = listener;
    }

    /**
     * 设置按钮点击监听
     * @param msg
     * @param listener
     */
    public void setOnConfirmListener(String msg, OnConfirmListener listener) {
        if (!TextUtils.isEmpty(msg)) {
            this.confirmMsg = msg;
        }
        this.confirmListener = listener;
    }

    /**
     * 更改显示中的文字
     */
    public void setContentOnShowing(String content) {
        if (dialog_common_content_tv != null && !TextUtils.isEmpty(content)) {
            dialog_common_content_tv.setText(content);
        }
    }

    /**
     * 点击事件接口
     */
    public interface OnCancelListener {
        void onClick(CommonDialog dialog);
    }

    /**
     * 点击事件接口
     */
    public interface OnConfirmListener {
        void onClick(CommonDialog dialog);
    }

}
