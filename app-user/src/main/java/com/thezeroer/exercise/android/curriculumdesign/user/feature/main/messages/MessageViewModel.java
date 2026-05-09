package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class MessageViewModel extends ViewModel {

    private MessageRepository repository;
    private LiveData<List<MessageItem>> messages;

    public MessageViewModel() {
        // 实际项目中建议通过工厂模式注入，这里简化直接 new
        repository = new MessageRepository();
        messages = repository.getMessages();
    }

    /**
     * 获取消息列表的 LiveData，Activity/Fragment 观察它即可更新 UI
     */
    public LiveData<List<MessageItem>> getMessages() {
        return messages;
    }

    // 可选：刷新数据、标记已读等操作可继续扩展
}