package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.user.R;

import java.util.ArrayList;
import java.util.List;

public class MessageRepository {

    public LiveData<List<MessageItem>> getMessages() {
        MutableLiveData<List<MessageItem>> data = new MutableLiveData<>();
        // 模拟网络或数据库加载
        List<MessageItem> list = new ArrayList<>();
        list.add(new MessageItem("张伟", "好的，明天见！", "10:30", R.drawable.ic_contacts, 2));
        list.add(new MessageItem("李娜", "文件我已经发给你了", "昨天", R.drawable.ic_contacts, 0));
        list.add(new MessageItem("项目组", "王磊：收到请回复", "昨天", R.drawable.ic_contacts, 5));
        list.add(new MessageItem("赵敏", "周末有空吗？", "周三", R.drawable.ic_contacts, 0));
        list.add(new MessageItem("陈晨", "谢谢！", "周一", R.drawable.ic_contacts, 0));
        data.setValue(list);
        return data;
    }
}