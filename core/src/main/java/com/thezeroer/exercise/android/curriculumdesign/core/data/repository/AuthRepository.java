package com.thezeroer.exercise.android.curriculumdesign.core.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.AccountType;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.util.concurrent.CompletableFuture;

/**
 * 认证仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class AuthRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public CompletableFuture<Resource<String>> login(String accountId, String accountPassword, boolean rememberMe, boolean autoLogin, AccountType accountType) {
        CompletableFuture<Resource<String>> result = new CompletableFuture<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Auth_Login)
                        .attach(new TextPayload(TextConverter.fromArray(
                                accountId, accountPassword, String.valueOf(rememberMe), String.valueOf(autoLogin), String.valueOf(accountType.getCode())))))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success());
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
