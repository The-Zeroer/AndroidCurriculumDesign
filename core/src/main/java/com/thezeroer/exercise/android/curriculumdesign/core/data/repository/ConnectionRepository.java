package com.thezeroer.exercise.android.curriculumdesign.core.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.client.event.LinkStatusListener;

import java.net.InetSocketAddress;

/**
 * 连接仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class ConnectionRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public LiveData<Resource<String>> connect(String host, int port) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        if (networkService.getLinkStatus() == LinkStatusListener.Status.LINKED) {
            result.setValue(Resource.success());
        } else {
            networkService.onStatusTransitionOnce(LinkStatusListener.EventKey.of(LinkStatusListener.Status.LINKING, null), event -> {
                if (event.to() == LinkStatusListener.Status.LINKED) {
                    result.postValue(Resource.success());
                } else {
                    result.postValue(Resource.failed(event.reason().name()));
                }
            });
            new Thread(() -> {
                try {
                    networkService.linkServer(new InetSocketAddress(host, port));
                } catch (Exception e) {
                    result.postValue(Resource.error(e));
                }
            }).start();
        }
        return result;
    }
}
