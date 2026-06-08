package com.thezeroer.exercise.android.curriculumdesign.core.data.repository;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;
import com.thezeroer.nexalithic.client.manager.LinkStatusManager;
import com.thezeroer.nexalithic.core.event.EventHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * 连接仓库
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class ConnectionRepository extends BaseRepository {
    private final BaseNetworkService networkService = AppInjector.getNetworkService();

    public CompletableFuture<Resource<String>> connect(String host, int port) {
        CompletableFuture<Resource<String>> result = new CompletableFuture<>();

        // 1. 快捷通道：如果已经是连接成功的状态，直接无代价返回成功
        if (networkService.getLinkStatus() == LinkStatusManager.Status.LINKED) {
            result.complete(Resource.success());
            return result;
        }

        // 2. 订阅连接状态转移事件
        networkService.getEventBus().subscribe(LinkStatusManager.Events.StatusTransition.class,
                new EventHandler.ConditionalHandler<>() {
                    @Override
                    protected boolean handleTrigger(LinkStatusManager.Events.StatusTransition event) {
                        // 如果不是从 LINKING 转移过来的状态，说明不是本次触发的连接尝试，继续挂起监听
                        if (event.from() != LinkStatusManager.Status.LINKING) {
                            return true;
                        }

                        // 状态转移到已连接
                        if (event.to() == LinkStatusManager.Status.LINKED) {
                            result.complete(Resource.success());
                        } else {
                            // 状态转移到了其他状态（如 UNLINKED），说明连接彻底失败了
                            String message = event.reason().name();
                            if (event.attachment() instanceof Exception exception) {
                                message = message + ":" + exception.getMessage();
                            }
                            result.complete(Resource.failed(message));
                        }

                        // 重点：返回 false，代表此条件处理器任务终结，让 EventBus 将其自动卸载，防止内存泄漏
                        return false;
                    }
                });

        // 3. 异步触发连接动作
        // 提示：强烈建议将 new Thread() 替换为你框架内部的异步线程池，如 ForkJoinPool.commonPool() 或自定义 Executor
        CompletableFuture.runAsync(() -> {
            try {
                networkService.linkServer(new InetSocketAddress(host, port));
            } catch (Exception e) {
                // 如果 linkServer 方法同步阻塞抛出异常，在这里兜底
                result.complete(Resource.error(e));
            }
        });

        return result;
    }
}
