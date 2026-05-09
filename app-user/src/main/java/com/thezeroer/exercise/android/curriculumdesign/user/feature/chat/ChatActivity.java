package com.thezeroer.exercise.android.curriculumdesign.user.feature.chat;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class ChatActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvChatTitle;
    private RecyclerView rvChat;
    private EditText etMessage;
    private ImageView ivAttach, ivSend;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void onInitView() {
        // 绑定控件
        toolbar = findViewById(R.id.toolbar_chat);
        tvChatTitle = findViewById(R.id.tv_chat_title);
        rvChat = findViewById(R.id.rv_chat);
        etMessage = findViewById(R.id.et_message);
        ivAttach = findViewById(R.id.iv_attach);
        ivSend = findViewById(R.id.iv_send);

        // 设置 Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // 从 Intent 获取联系人姓名并设置标题
        String contactName = getIntent().getStringExtra("contact_name");
        if (contactName != null && !contactName.isEmpty()) {
            tvChatTitle.setText(contactName);
        } else {
            tvChatTitle.setText("聊天");
        }

        // 返回按钮点击关闭页面
        toolbar.setNavigationOnClickListener(v -> finish());

        // 点击消息列表区域收起键盘
        rvChat.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                hideKeyboard();
                etMessage.clearFocus();
            }
            return false; // 不拦截事件，保证列表正常滑动
        });
    }

    @Override
    protected void onInitHandler() {
        // 发送按钮点击（预留）
        ivSend.setOnClickListener(v -> {
            // TODO: 发送消息逻辑
        });

        // 附件按钮点击（预留）
        ivAttach.setOnClickListener(v -> {
            // TODO: 打开图片/文件选择器
        });
    }

    /**
     * 重写触摸事件分发，实现点击空白区域收起键盘
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard();
                if (v != null) {
                    v.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断触摸点是否在 EditText 区域外
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && v instanceof EditText) {
            int[] location = {0, 0};
            v.getLocationInWindow(location);
            int left = location[0];
            int top = location[1];
            int right = left + v.getWidth();
            int bottom = top + v.getHeight();
            return !(event.getRawX() > left && event.getRawX() < right
                    && event.getRawY() > top && event.getRawY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
}