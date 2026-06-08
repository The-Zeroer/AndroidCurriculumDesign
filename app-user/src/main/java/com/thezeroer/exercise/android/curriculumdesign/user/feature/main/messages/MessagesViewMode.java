package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import androidx.lifecycle.LiveData;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.entity.ConversationEntity;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.ConversationRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;

import java.util.List;

/**
 * 消息中心会话列表视图模型
 */
public class MessagesViewMode extends BaseViewModel {

    private final ConversationRepository conversationRepository;
    private final LiveData<List<ConversationEntity>> conversationListLiveData;

    // 假设你的 BaseViewModel 或底层支持传入或能够获取 Application/Context
    public MessagesViewMode(ConversationRepository conversationRepository) {
        // 直接持有 Room 返回的响应式 LiveData 流
        this.conversationRepository = conversationRepository;
        this.conversationListLiveData = conversationRepository.getConversationList(MainActivity.account.getAccountId());
    }

    public LiveData<List<ConversationEntity>> getConversationList() {
        return conversationListLiveData;
    }

    /**
     * 供 UI 层调用的删除会话接口
     */
    public void deleteConversation(String accountId) {
        if (accountId != null) {
            String currentOwnerId = MainActivity.account != null ? MainActivity.account.getAccountId() : "";
            conversationRepository.deleteConversation(currentOwnerId, accountId);
        }
    }
}