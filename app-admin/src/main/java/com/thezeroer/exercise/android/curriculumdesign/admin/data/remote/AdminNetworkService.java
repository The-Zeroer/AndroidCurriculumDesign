package com.thezeroer.exercise.android.curriculumdesign.admin.data.remote;

import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.nexalithic.client.NexalithicClient;

import java.io.IOException;

/**
 * 行政Nexalithic服务
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class AdminNetworkService extends BaseNetworkService {
    public AdminNetworkService() throws IOException {
    }

    @Override
    protected NexalithicClient.Builder onInitNexalithicClient(NexalithicClient.Builder nexalithicClientBuilder) throws IOException {
        return nexalithicClientBuilder;
    }
}
