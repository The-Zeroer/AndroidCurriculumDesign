package com.thezeroer.exercise.android.curriculumdesign.user.data.repository;

import androidx.lifecycle.LiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.UserDataBase;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.dao.ConversationDao;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.entity.ConversationEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConversationRepository extends BaseRepository {

    private final ConversationDao conversationDao;

    public ConversationRepository() {
        this.conversationDao = AppInjector.<UserDataBase>getDatabase().conversationDao();
    }

    public LiveData<List<ConversationEntity>> getConversationList(String ownerId) {
        return conversationDao.getAllConversations(ownerId);
    }

    /**
     * 收到别人消息落盘
     */
    public void saveIncomingSession(String ownerId, String senderId, String content) {
        ConversationEntity exist = conversationDao.getConversationById(ownerId, senderId);
        long now = System.currentTimeMillis();
        String realName = ContactsRepository.getCachedName(senderId, "用户 " + senderId);

        if (exist != null) {
            exist.setName(realName);
            exist.setLastMessage(content);
            exist.setTimestamp(now);
            exist.setUnreadCount(exist.getUnreadCount() + 1);
            conversationDao.insertOrUpdate(exist);
        } else {
            // 传入完整的双主键
            ConversationEntity newSession = new ConversationEntity(ownerId, senderId, realName, content, now, 1);
            conversationDao.insertOrUpdate(newSession);
        }
    }

    /**
     * 自己发消息成功落盘
     */
    public void saveOutgoingSession(String ownerId, String targetId, String content) {
        ConversationEntity exist = conversationDao.getConversationById(ownerId, targetId);
        long now = System.currentTimeMillis();
        String realName = ContactsRepository.getCachedName(targetId, "用户 " + targetId);

        if (exist != null) {
            exist.setName(realName);
            exist.setLastMessage(content);
            exist.setTimestamp(now);
            conversationDao.insertOrUpdate(exist);
        } else {
            ConversationEntity newSession = new ConversationEntity(ownerId, targetId, realName, content, now, 0);
            conversationDao.insertOrUpdate(newSession);
        }
    }

    /**
     * 清除未读红点
     */
    public void clearUnread(String ownerId, String targetId) {
        CompletableFuture.runAsync(() -> {
            if (conversationDao != null) {
                conversationDao.clearUnreadCount(ownerId, targetId);
            }
        });
    }

    /**
     * 删除单条会话
     */
    public void deleteConversation(String ownerId, String targetId) {
        CompletableFuture.runAsync(() -> {
            if (conversationDao != null) {
                conversationDao.deleteConversationById(ownerId, targetId);
            }
        });
    }
}