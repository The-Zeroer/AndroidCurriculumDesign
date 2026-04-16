package com.thezeroer.exercise.android.curriculumdesign.core.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账户实体
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "accounts")
public class AccountEntity {
    @PrimaryKey
    @NonNull
    private String accountId;
    private String accountName;
    private String accountAvatarPath;
}
