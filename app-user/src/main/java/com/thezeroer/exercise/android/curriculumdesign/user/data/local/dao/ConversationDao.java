package com.thezeroer.exercise.android.curriculumdesign.user.data.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.thezeroer.exercise.android.curriculumdesign.user.data.local.entity.ConversationEntity;

import java.util.List;

@Dao
public interface ConversationDao {

    // 🟢 核心过滤：只查询属于当前登录用户的会话列表
    @Query("SELECT * FROM conversations WHERE ownerId = :ownerId ORDER BY timestamp DESC")
    LiveData<List<ConversationEntity>> getAllConversations(String ownerId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(ConversationEntity conversation);

    // 🟢 精准定位：根据谁登录、在和谁聊来查询单条会话
    @Query("SELECT * FROM conversations WHERE ownerId = :ownerId AND accountId = :targetId LIMIT 1")
    ConversationEntity getConversationById(String ownerId, String targetId);

    // 🟢 精准更新未读数
    @Query("UPDATE conversations SET unreadCount = 0 WHERE ownerId = :ownerId AND accountId = :targetId")
    void clearUnreadCount(String ownerId, String targetId);

    // 🟢 精准删除
    @Query("DELETE FROM conversations WHERE ownerId = :ownerId AND accountId = :targetId")
    void deleteConversationById(String ownerId, String targetId);
}