package com.thezeroer.exercise.android.curriculumdesign.user.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// 🟢 核心修改：将单独的主键升级为联合主键，确保同一个对方ID在不同登录用户下产生独立记录
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "conversations", primaryKeys = {"ownerId", "accountId"})
public class ConversationEntity {
    @NonNull
    private String ownerId;       // ✨ 新增：当前登录的账号ID（这条会话属于谁）
    @NonNull
    private String accountId;     // 对方的账号ID
    private String name;          // 对方的昵称
    private String lastMessage;   // 最后一条消息内容
    private long timestamp;       // 最后一条消息的时间戳
    private int unreadCount;      // 未读消息数
}