package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.chat.ChatActivity;

import java.util.ArrayList;
import java.util.List;

public class MessagesFragment extends BaseFragment {

    private RecyclerView rvMessages;
    private MessageAdapter adapter;
    private List<MessageItem> messageList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_messages;
    }

    @Override
    protected void onInitView(View view) {
        rvMessages = view.findViewById(R.id.rv_messages);
        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));

        loadMockData();

        // 设置点击监听：点击条目跳转到聊天界面
        adapter = new MessageAdapter(messageList, (message, position) -> {
            // 1. 弹 Toast 确认点击生效
            Toast.makeText(getContext(), "打开与 " + message.getName() + " 的聊天", Toast.LENGTH_SHORT).show();

            // 2. 安全跳转（防止 Activity 为空）
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("contact_name", message.getName());
                startActivity(intent);
            }
        });

        rvMessages.setAdapter(adapter);
    }

    @Override
    protected void onInitHandler() {
        // 预留，可添加搜索、添加联系人等功能
    }

    private void loadMockData() {
        messageList = new ArrayList<>();
        messageList.add(new MessageItem("张伟", "好的，明天见！", "10:30", R.drawable.ic_contacts, 2));
        messageList.add(new MessageItem("李娜", "文件我已经发给你了", "昨天", R.drawable.ic_contacts, 0));
        messageList.add(new MessageItem("项目组", "王磊：收到请回复", "昨天", R.drawable.ic_contacts, 5));
        messageList.add(new MessageItem("赵敏", "周末有空吗？", "周三", R.drawable.ic_contacts, 0));
        messageList.add(new MessageItem("陈晨", "谢谢！", "周一", R.drawable.ic_contacts, 0));
    }
}