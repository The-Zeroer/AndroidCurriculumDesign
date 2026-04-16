package com.thezeroer.exercise.android.curriculumdesign.core.nexalithic;

import com.thezeroer.nexalithic.client.NexalithicClient;
import com.thezeroer.nexalithic.client.security.DefaultClientSecurityPolicy;
import com.thezeroer.nexalithic.core.io.thread.LoopThread;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * 基础Nexalithic服务
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public abstract class BaseNexalithicService {

    protected NexalithicClient nexalithicClient;

    public BaseNexalithicService() throws IOException {
        nexalithicClient = initNexalithicClient();
    }
    private NexalithicClient initNexalithicClient() throws IOException {
        return NexalithicClient
                .builder()
                .apply(LoopThread.OPTIONS.GlobalLoopBufferPool_Capacity, 2)
                .apply(LoopThread.OPTIONS.LocalLoopBufferPool_Capacity, 16)
                .securityPolicy(new DefaultClientSecurityPolicy() {
                    @Override
                    public int getServerCertificatesLength() {
                        return 40;
                    }

                    @Override
                    public void CertificatesFormBuffer(ByteBuffer buffer) {

                    }

                    @Override
                    public boolean verifyOfLeafCertificate(ByteBuffer buffer) {
                        return true;
                    }
                })
                .build();
    }
}
