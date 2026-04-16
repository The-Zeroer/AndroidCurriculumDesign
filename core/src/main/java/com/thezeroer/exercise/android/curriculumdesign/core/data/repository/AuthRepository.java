package com.thezeroer.exercise.android.curriculumdesign.core.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;
import com.thezeroer.nexalithic.core.model.packet.business.payload.TextPayload;
import com.thezeroer.nexalithic.core.util.TextConverter;

/**
 * 认证仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class AuthRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public LiveData<Resource<String>> login(String accountId, String accountPassword, boolean rememberMe, boolean autoLogin) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        TaskFuture future = networkService.submitTask(NexalithicTask.builder()
                .onRequest(BusinessPacket.create(BusinessPacket.Way.DEFAULT, BaseNetworkService.Path_AuthLogin)
                        .attach(new TextPayload(TextConverter.fromArray(new String[]{
                                accountId, accountPassword, String.valueOf(rememberMe), String.valueOf(autoLogin)}
                ))))
                .onResponse(response -> {
                    if (response.getWay() == BusinessPacket.Way.RESPONSE_Succeed) {
                        result.postValue(Resource.success());
                    } else {
                        result.postValue(Resource.failed(response.getDisplayMessage(), response.getWay()));
                    }
                })
                .onTimeout(() -> {
                    result.postValue(Resource.failed("服务器响应超时"));
                })
                .onFailed(exception -> {
                    result.postValue(Resource.error(exception));
                }));
        if (future == null) {
            result.setValue(Resource.failed("操作失败，请稍后重试"));
        }
        return result;
    }
}
