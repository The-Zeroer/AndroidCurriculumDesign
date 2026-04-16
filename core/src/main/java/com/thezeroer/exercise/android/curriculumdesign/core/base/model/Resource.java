package com.thezeroer.exercise.android.curriculumdesign.core.base.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;

/**
 * 统一资源状态包装类
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class Resource<T> {
    public enum Status { SUCCESS, FAILED, LOADING }

    @NonNull
    public final Status status;

    @Nullable
    public final T data;

    @Nullable
    public final String message;

    @Nullable
    public final BusinessPacket.Way way;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable BusinessPacket.Way way) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.way = way;
    }

    public static <T> Resource<T> success() {
        return new Resource<>(Status.SUCCESS, null, null, null);
    }
    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(Status.SUCCESS, data, null, null);
    }

    public static <T> Resource<T> success(@Nullable T data, @Nullable BusinessPacket.Way way) {
        return new Resource<>(Status.SUCCESS, data, null, way);
    }

    public static <T> Resource<T> failed(@NonNull String msg, @Nullable BusinessPacket.Way way) {
        return new Resource<>(Status.FAILED, null, msg, way);
    }

    public static <T> Resource<T> failed(@Nullable String msg) {
        return new Resource<>(Status.FAILED, null, (msg == null || msg.isEmpty()) ? "操作失败，请稍后重试" : msg, null);
    }

    public static <T> Resource<T> error(@Nullable Exception exception) {
        if (exception == null) {
            return failed("未知系统异常");
        }
        return failed(exception.getMessage());
    }

    public static <T> Resource<T> loading() {
        return new Resource<>(Status.LOADING, null, null, null);
    }

    public boolean isSuccess() { return status == Status.SUCCESS; }
    public boolean isFailed() { return status == Status.FAILED; }
    public boolean isLoading() { return status == Status.LOADING; }
}