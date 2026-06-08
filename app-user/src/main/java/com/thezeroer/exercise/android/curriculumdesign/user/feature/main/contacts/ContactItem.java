package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactItem {
    private String id;
    private String name;
    private int avatarResId; // 本地资源ID，也可用网络图片URL
}