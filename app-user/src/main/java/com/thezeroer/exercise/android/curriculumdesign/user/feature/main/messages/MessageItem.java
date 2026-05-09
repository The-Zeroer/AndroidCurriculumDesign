package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

public class MessageItem {
    private String name;
    private String lastMessage;
    private String time;
    private int avatarResId;
    private int unreadCount; // 未读消息数，0 表示无未读

    public MessageItem(String name, String lastMessage, String time, int avatarResId, int unreadCount) {
        this.name = name;
        this.lastMessage = lastMessage;
        this.time = time;
        this.avatarResId = avatarResId;
        this.unreadCount = unreadCount;
    }

    public String getName() { return name; }
    public String getLastMessage() { return lastMessage; }
    public String getTime() { return time; }
    public int getAvatarResId() { return avatarResId; }
    public int getUnreadCount() { return unreadCount; }
}