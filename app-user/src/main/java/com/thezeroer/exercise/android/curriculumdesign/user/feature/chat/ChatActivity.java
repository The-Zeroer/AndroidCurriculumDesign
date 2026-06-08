package com.thezeroer.exercise.android.curriculumdesign.user.feature.chat;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.mode.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity<ChatViewMode> {

    private Toolbar toolbar;
    private TextView tvChatTitle;
    private RecyclerView rvChat;
    private EditText etMessage;
    private ImageView ivAttach, ivSend;
    private SwipeRefreshLayout swipeRefreshLayout; // 新增：下拉刷新控件

    // 业务所需变量
    private ChatAdapter chatAdapter;
    private final List<Message> localMessageList = new ArrayList<>();

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
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_chat); // 绑定下拉控件

        // 设置 Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // 从 Intent 获取联系人姓名与ID并设置标题
        String targetId = getIntent().getStringExtra("target_id");
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

        String senderId = MainActivity.account.getAccountId();

        // 初始化列表组件
        chatAdapter = new ChatAdapter(localMessageList, senderId);
        rvChat.setLayoutManager(new LinearLayoutManager(this));
        rvChat.setAdapter(chatAdapter);

        // 初始化 ViewModel 配置并在其内部自动拉取一次数据
        if (viewModel != null) {
            viewModel.init(senderId, targetId);
            // 开启观察者数据订阅
            subscribeUi();
        }
    }

    /**
     * 订阅 ViewModel 暴露出来的 LiveData 状态
     */
    private void subscribeUi() {
        // 监听消息列表数据流
        viewModel.getMessageList().observe(this, newMessages -> {
            if (newMessages != null) {
                localMessageList.clear();
                localMessageList.addAll(newMessages);
                chatAdapter.notifyDataSetChanged();
                // 收到消息或发完消息自动滑到最底部
                if (!localMessageList.isEmpty()) {
                    rvChat.scrollToPosition(localMessageList.size() - 1);
                }
            }
        });

        // 监听下拉刷新动画状态
        viewModel.getIsRefreshing().observe(this, isRefreshing -> {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(isRefreshing);
            }
        });

        // 监听全局提示性 Toast 弹窗
        viewModel.getToastEvent().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });

        // 监听发送状态事件
        viewModel.getSendStatusEvent().observe(this, isSuccess -> {
            if (isSuccess) {
                etMessage.setText(""); // 发送成功后清空输入框内容
            }
        });
    }

    @Override
    protected void onInitHandler() {
        // 设置下拉刷新手势监听
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (viewModel != null) {
                    viewModel.pullMessages(); // 手动拉取新消息
                }
            });
        }

        // 发送按钮点击，只负责把数据递交给 ViewModel
        ivSend.setOnClickListener(v -> {
            if (viewModel != null) {
                viewModel.sendMessage(etMessage.getText().toString());
            }
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