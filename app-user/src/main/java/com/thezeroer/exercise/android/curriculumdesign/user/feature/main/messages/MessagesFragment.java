package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
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

        // 准备一个可变的列表，Adapter 将持有此引用
        messageList = new ArrayList<>();

        // 设置点击监听：点击条目跳转到聊天界面
        adapter = new MessageAdapter(messageList, (message, position) -> {
            Toast.makeText(getContext(), "打开与 " + message.getName() + " 的聊天", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("contact_name", message.getName());
                startActivity(intent);
            }
        });
        rvMessages.setAdapter(adapter);

        // 获取 ViewModel 并观察消息数据
        MessageViewModel viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        viewModel.getMessages().observe(getViewLifecycleOwner(), newMessages -> {
            // 同一个列表引用，先清空再填充，通知 Adapter 刷新
            messageList.clear();
            messageList.addAll(newMessages);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    protected void onInitHandler() {
        // 预留，可添加搜索、添加联系人等功能
    }
}