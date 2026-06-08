package com.thezeroer.exercise.android.curriculumdesign.user.mode;

import com.thezeroer.exercise.android.curriculumdesign.core.enums.MessageType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 信息
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long messageId;
    private String conversationId;
    private String senderId;
    private String targetId;
    private String content;
    private MessageType type;
    private Long timestamp;
}
