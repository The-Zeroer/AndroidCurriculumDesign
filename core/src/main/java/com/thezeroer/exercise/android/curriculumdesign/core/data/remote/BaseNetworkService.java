package com.thezeroer.exercise.android.curriculumdesign.core.data.remote;

import com.thezeroer.nexalithic.client.NexalithicClient;
import com.thezeroer.nexalithic.client.lifecycle.LifecycleManager;
import com.thezeroer.nexalithic.client.manager.LinkStatusManager;
import com.thezeroer.nexalithic.client.security.EmptyClientSecurityPolicy;
import com.thezeroer.nexalithic.core.event.NexalithicEventBus;
import com.thezeroer.nexalithic.core.io.thread.LoopThread;
import com.thezeroer.nexalithic.core.messaging.task.NexalithicTask;
import com.thezeroer.nexalithic.core.messaging.task.TaskFuture;
import com.thezeroer.nexalithic.core.model.packet.business.BusinessPacket;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 基础Nexalithic服务
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public abstract class BaseNetworkService {
    public static final short[] Path_AuthLogin = new short[]{11, 11};
    public static final short[] Path_AuthLogout = new short[]{11, 12};

    protected final NexalithicClient nexalithicClient;

    public BaseNetworkService() throws IOException {
        nexalithicClient = initNexalithicClient();
    }

    public void start() {
        nexalithicClient.start();
    }
    public void stop() {
        nexalithicClient.stop();
    }

    public void linkServer(InetSocketAddress remote) throws Exception {
        nexalithicClient.link(remote);
    }

    public boolean pushBusinessPacket(BusinessPacket packet) {
        return nexalithicClient.push(packet);
    }
    public TaskFuture submitTask(NexalithicTask.Builder taskBuilder) {
        return nexalithicClient.submit(taskBuilder);
    }

    public NexalithicEventBus getEventBus() {
        return nexalithicClient.getEventBus();
    }
    public LifecycleManager.State getState() {
        return nexalithicClient.getState();
    }
    public LinkStatusManager.Status getLinkStatus() {
        return nexalithicClient.getLinkStatus();
    }

    private NexalithicClient initNexalithicClient() throws IOException {
        return NexalithicClient
                .builder()
                .apply(LoopThread.OPTIONS.GlobalLoopBufferPool_Capacity, 2)
                .apply(LoopThread.OPTIONS.LocalLoopBufferPool_Capacity, 16)
                .securityPolicy(EmptyClientSecurityPolicy.INSTANCE())
                .build();
    }
}
