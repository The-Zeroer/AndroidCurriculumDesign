package com.thezeroer.exercise.android.curriculumdesign.admin.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.AccountType;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.util.concurrent.CompletableFuture;

/**
 * 账户管理仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/06
 */
public class AccountManagementRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public CompletableFuture<Resource<Void>> register(String accountId, String accountPassword, AccountType accountType) {
        CompletableFuture<Resource<Void>> result = new CompletableFuture<>();
        networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountManagement_Register)
                        .attach(new TextPayload(TextConverter.fromArray(accountId, accountPassword, String.valueOf(accountType.getCode())))))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        return result;
    }

    public CompletableFuture<Resource<Void>> unregister(String accountId) {
        CompletableFuture<Resource<Void>> result = new CompletableFuture<>();
        networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.AccountManagement_Unregister)
                        .attach(new TextPayload(accountId)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        result.complete(Resource.success(response.getDisplayMessage()));
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        return result;
    }
}
