package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

public class ContactItem {
    private String name;
    private String phone;
    private int avatarResId; // 本地资源ID，也可用网络图片URL

    public ContactItem(String name, String phone, int avatarResId) {
        this.name = name;
        this.phone = phone;
        this.avatarResId = avatarResId;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public int getAvatarResId() { return avatarResId; }
}