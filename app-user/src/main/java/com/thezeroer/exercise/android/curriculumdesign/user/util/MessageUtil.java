package com.thezeroer.exercise.android.curriculumdesign.user.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.concurrent.atomic.AtomicLong;

public class MessageUtil {
    private static final int SEQ_BITS = 16;
    private static final int MAX_SEQ = (1 << SEQ_BITS) - 1; // 65535

    private static final AtomicLong state = new AtomicLong(0L);

    public static long generateMessageId(long time) {
        while (true) {
            long oldState = state.get();
            long lastTime = oldState >>> SEQ_BITS;
            int seq = (int) (oldState & MAX_SEQ);
            if (time == lastTime) {
                seq += 1;
                if (seq > MAX_SEQ) {
                    // 同毫秒序号溢出，等待下一毫秒
                    time = waitNextMillis(lastTime);
                    seq = 0;
                }
            } else {
                seq = 0;
            }
            long newState = (time << SEQ_BITS) | (seq & MAX_SEQ);
            newState &= 0x7FFFFFFFFFFFFFFFL;  // 固定最高位为 0
            if (state.compareAndSet(oldState, newState)) {
                return newState;
            }
        }
    }

    public static String generateConversationId(String uidA, String uidB) {
        String first = uidA.compareTo(uidB) < 0 ? uidA : uidB;
        String second = uidA.compareTo(uidB) < 0 ? uidB : uidA;
        String rawKey = first + "&&" + second;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(rawKey.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hash error", e);
        }
    }

    public static long getTime(long messageId) {
        return messageId >>> SEQ_BITS;
    }
    public static int getSequence(long messageId) {
        return (int) (messageId & MAX_SEQ);
    }

    private static long waitNextMillis(long lastTs) {
        long now;
        do {
            now = System.currentTimeMillis();
            Thread.yield(); // 放弃 CPU，让其他线程执行
        } while (now <= lastTs);
        return now;
    }
}
