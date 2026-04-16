package com.thezeroer.exercise.android.curriculumdesign.user;

import com.thezeroer.exercise.android.curriculumdesign.core.base.BaseApplication;
import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.user.data.local.UserDataBase;
import com.thezeroer.exercise.android.curriculumdesign.user.data.remote.UserNetworkService;

import java.io.IOException;

/**
 * 用户应用
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public class UserApplication extends BaseApplication {
    @Override
    protected BaseDataBase onCreateDatabase() {
        return UserDataBase.getInstance(this);
    }

    @Override
    protected BaseNetworkService onCreateNetworkService() throws IOException {
        BaseNetworkService networkService = new UserNetworkService();
        networkService.start();
        return networkService;
    }

    @Override
    protected void onInit() {
    }
}
