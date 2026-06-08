package com.thezeroer.exercise.android.curriculumdesign.admin.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.BaseRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.HandlerPath;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 审计日志仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/08
 */
public class AuditsRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    /**
     * 拉取日志文件列表
     */
    public CompletableFuture<Resource<List<String>>> getLogList() {
        CompletableFuture<Resource<List<String>>> result = new CompletableFuture<>();
        networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Audits_GetList))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        if (response.firstPayload() instanceof TextPayload textPayload) {
                            List<String> list = TextConverter.toList(textPayload.value());
                            result.complete(Resource.success(list != null ? list : new ArrayList<>()));
                        } else {
                            result.complete(Resource.success(new ArrayList<>()));
                        }
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        return result;
    }

    /**
     * 拉取具体的日志文件内容
     */
    public CompletableFuture<Resource<String>> getLogContent(String fileName) {
        CompletableFuture<Resource<String>> result = new CompletableFuture<>();
        networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(HandlerPath.Audits_GetContent).attach(new TextPayload(fileName)))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Success) {
                        if (response.firstPayload() instanceof TextPayload textPayload) {
                            result.complete(Resource.success(textPayload.value(), ""));
                        } else {
                            result.complete(Resource.success("日志内容为空或解析失败"));
                        }
                    } else {
                        result.complete(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> result.complete(Resource.failed("服务器响应超时")))
                .onFailed(exception -> result.complete(Resource.error(exception))));
        return result;
    }
}