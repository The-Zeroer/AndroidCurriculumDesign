package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.entity.ConversationEntity;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.ContactsRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.chat.ChatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MessagesFragment extends BaseFragment<MessagesViewMode> {

    private RecyclerView rvMessages;
    private MessageAdapter adapter;
    private final List<MessageItem> messageList = new ArrayList<>();
    private final SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void onInitView(View view) {
        rvMessages = view.findViewById(R.id.rv_messages);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));

        // 设置点击监听：短按跳转，长按弹窗删除
        adapter = new MessageAdapter(messageList, new MessageAdapter.OnMessageClickListener() {
            @Override
            public void onItemClick(MessageItem message, int position) {
                if (getActivity() != null) {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    intent.putExtra("target_id", message.getAccountId());
                    intent.putExtra("contact_name", message.getName());
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(MessageItem message, int position) {
                // 🟢 长按回调：拉起确认删除的选择弹窗
                showDeleteDialog(message.getAccountId(), message.getName());
            }
        });

        rvMessages.setAdapter(adapter);
    }

    /**
     * 弹出原生确认删除对话框
     */
    private void showDeleteDialog(String accountId, String nickname) {
        if (getContext() == null) return;

        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("确定要删除与 \"" + nickname + "\" 的聊天会话吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    if (viewModel != null) {
                        // 驱动 ViewModel 去删本地数据库，LiveData 监听到数据变动会自动无缝刷新列表
                        viewModel.deleteConversation(accountId);
                        Toast.makeText(getContext(), "会话已删除", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", null)
                .show();
    }

    @Override
    protected void onInitHandler() {
        // 预留，可添加搜索、添加联系人等功能
    }

    @Override
    protected void onInitObserve() {
        if (viewModel == null) return;

        viewModel.getConversationList().observe(getViewLifecycleOwner(), conversations -> {
            if (conversations != null) {
                messageList.clear();

                // 将本地存储的 PO/Entity 转换为 Adapter 渲染需要的专属 MessageItem
                for (ConversationEntity entity : conversations) {
                    String readableTime = timeFormatter.format(new Date(entity.getTimestamp()));
                    messageList.add(new MessageItem(
                            entity.getAccountId(),
                            ContactsRepository.getCachedName(entity.getAccountId(), entity.getName()),
                            entity.getLastMessage(),
                            readableTime,
                            R.drawable.ic_contacts,
                            entity.getUnreadCount()
                    ));
                }

                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onInitData() {
        super.onInitData();
    }
}