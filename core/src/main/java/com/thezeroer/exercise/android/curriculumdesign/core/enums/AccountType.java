package com.thezeroer.exercise.android.curriculumdesign.core.enums;

import lombok.Getter;

/**
 * 账户类型
 *
 * @author tbrtz647@outlook.com
 * @since 2026/04/18
 * @version 1.0.0
 */
@Getter
public enum AccountType {
    ADMIN(10, "管理员"),
    USER(20, "普通用户");

    private final int code;
    private final String description;

    AccountType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final AccountType[] CODE_LOOKUP_TABLE;
    static {
        int maxCode = 0;
        for (AccountType type : AccountType.values()) {
            if (type.code > maxCode) {
                maxCode = type.code;
            }
        }
        CODE_LOOKUP_TABLE = new AccountType[maxCode + 1];
        for (AccountType type : AccountType.values()) {
            if (type.code >= 0) {
                CODE_LOOKUP_TABLE[type.code] = type;
            }
        }
    }

    public static AccountType valueOf(int code) {
        if (code < 0 || code >= CODE_LOOKUP_TABLE.length) {
            return null;
        }
        return CODE_LOOKUP_TABLE[code];
    }
}