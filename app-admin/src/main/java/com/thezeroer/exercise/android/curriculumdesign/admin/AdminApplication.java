package com.thezeroer.exercise.android.curriculumdesign.admin;

import com.thezeroer.exercise.android.curriculumdesign.admin.nexalithic.NexalithicService;
import com.thezeroer.exercise.android.curriculumdesign.core.BaseApplication;

import java.io.IOException;

/**
 * 管理员应用程序
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public class AdminApplication extends BaseApplication<NexalithicService> {
    @Override
    protected NexalithicService onCreateNexalithicService() throws IOException {
        return new NexalithicService();
    }
}
