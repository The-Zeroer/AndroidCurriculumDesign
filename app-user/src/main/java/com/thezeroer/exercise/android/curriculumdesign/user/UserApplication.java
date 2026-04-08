package com.thezeroer.exercise.android.curriculumdesign.user;

import com.thezeroer.exercise.android.curriculumdesign.core.BaseApplication;
import com.thezeroer.exercise.android.curriculumdesign.user.nexalithic.NexalithicService;

import java.io.IOException;

/**
 * 用户应用
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public class UserApplication extends BaseApplication<NexalithicService> {
    @Override
    protected NexalithicService onCreateNexalithicService() throws IOException {
        return new NexalithicService();
    }
}
