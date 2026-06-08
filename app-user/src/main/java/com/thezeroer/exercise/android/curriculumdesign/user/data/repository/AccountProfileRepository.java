package com.thezeroer.exercise.android.curriculumdesign.user.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.FilePayload;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

/**
 * 账户仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/04
 */
public class AccountProfileRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public CompletableFuture<Resource<Void>> changePassword(String oldPassword, String newPassword) {
        CompletableFuture<Resource<Void>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountProfile_ChangePassword)
                        .attach(new TextPayload(TextConverter.fromArray(oldPassword, newPassword))))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }

    public CompletableFuture<Resource<Void>> changeName(String newName) {
        CompletableFuture<Resource<Void>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountProfile_ChangeName)
                        .attach(new TextPayload(newName)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }

    public CompletableFuture<Resource<Void>> changeAvatar(File newAvatarFile) throws FileNotFoundException {
        CompletableFuture<Resource<Void>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountProfile_ChangeAvatar)
                        .attach(new FilePayload(newAvatarFile)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }

    public CompletableFuture<Resource<String>> getName(String accountId) {
        CompletableFuture<Resource<String>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountProfile_GetName)
                        .attach(new TextPayload(accountId)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage(), response.getWay()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        if (future == null) {
            result.complete(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }
}
