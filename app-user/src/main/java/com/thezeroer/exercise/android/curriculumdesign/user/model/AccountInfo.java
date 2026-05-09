package com.thezeroer.exercise.android.curriculumdesign.user.model;

public class AccountInfo {
    private String name;
    private String email;
    private String accountId;
    private int avatarResId; // 本地资源 ID，也可替换为 String avatarUrl

    // 无参构造方法
    public AccountInfo() {
    }

    // 带全部参数的构造方法
    public AccountInfo(String name, String email, String accountId, int avatarResId) {
        this.name = name;
        this.email = email;
        this.accountId = accountId;
        this.avatarResId = avatarResId;
    }

    // Getter & Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getAvatarResId() {
        return avatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }
}