package com.thezeroer.exercise.android.curriculumdesign.core.enums;

import lombok.Getter;

/**
 * 消息类型
 *
 * @author tbrtz647@outlook.com
 * @version 1.0.0
 * @since 2026/06/06
 */
@Getter
public enum MessageType {
    TEXT(10, "文本"),
    FILE(20, "文件");

    private final int code;
    private final String description;

    MessageType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    private static final MessageType[] CODE_LOOKUP_TABLE;
    static {
        int maxCode = 0;
        for (MessageType type : MessageType.values()) {
            if (type.code > maxCode) {
                maxCode = type.code;
            }
        }
        CODE_LOOKUP_TABLE = new MessageType[maxCode + 1];
        for (MessageType type : MessageType.values()) {
            if (type.code >= 0) {
                CODE_LOOKUP_TABLE[type.code] = type;
            }
        }
    }

    public static MessageType valueOf(int code) {
        if (code < 0 || code >= CODE_LOOKUP_TABLE.length) {
            return null;
        }
        return CODE_LOOKUP_TABLE[code];
    }
}
