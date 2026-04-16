package com.thezeroer.exercise.android.curriculumdesign.core.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 认证实体
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "auth_info")
public class AuthEntity {
    @PrimaryKey
    @NonNull
    private String accountId;
    private String accessToken;
    private String refreshToken;
    private long expireTime;
}
