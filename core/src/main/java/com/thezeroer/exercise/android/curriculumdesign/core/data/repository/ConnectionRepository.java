package com.thezeroer.exercise.android.curriculumdesign.core.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.client.manager.LinkStatusManager;
import com.thezeroer.nexalithic.core.event.EventSubscription;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicReference;

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
        if (networkService.getLinkStatus() == LinkStatusManager.Status.LINKED) {
            result.setValue(Resource.success());
        } else {
            AtomicReference<EventSubscription> subscriptionRef = new AtomicReference<>();
            EventSubscription subscription = networkService.getEventBus().subscribe(LinkStatusManager.Events.StatusTransition.class, event -> {
                if (event.from() != LinkStatusManager.Status.LINKING) {
                    return;
                }
                try {
                    if (event.to() == LinkStatusManager.Status.LINKED) {
                        result.postValue(Resource.success());
                    } else {
                        String message = event.reason().name();
                        if (event.attachment() instanceof Exception exception) {
                            message = message + ":" + exception.getMessage();
                        }
                        result.postValue(Resource.failed(message));
                    }
                } finally {
                    EventSubscription sub = subscriptionRef.get();
                    if (sub != null) {
                        sub.unsubscribe();
                    }
                }
            });
            subscriptionRef.set(subscription);
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
