package com.thezeroer.exercise.android.curriculumdesign.admin;

import com.thezeroer.exercise.android.curriculumdesign.admin.data.local.AdminDataBase;
import com.thezeroer.exercise.android.curriculumdesign.admin.data.remote.AdminNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AccountManagementRepository;
import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AccountProfileRepository;
import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AuditsRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.base.BaseApplication;
import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;

import java.io.IOException;

public class AdminApplication extends BaseApplication {
    @Override
    protected BaseDataBase onCreateDatabase() {
        return AdminDataBase.getInstance(this);
    }

    @Override
    protected BaseNetworkService onCreateNetworkService() throws IOException {
        BaseNetworkService networkService = new AdminNetworkService();
        networkService.start();
        return networkService;
    }

    @Override
    protected void onInit() {
        AppInjector.registerRepository(new AccountProfileRepository());
        AppInjector.registerRepository(new AccountManagementRepository());
        AppInjector.registerRepository(new AuditsRepository());
    }
}
