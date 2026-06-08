package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageItem {
    private String accountId;
    private String name;
    private String lastMessage;
    private String time;
    private int avatarResId;
    private int unreadCount; // 未读消息数，0 表示无未读
}