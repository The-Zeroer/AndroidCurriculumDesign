package com.thezeroer.exercise.android.curriculumdesign.core.base;

import android.app.Application;
import android.util.Log;

import com.thezeroer.exercise.android.curriculumdesign.core.data.local.BaseDataBase;
import com.thezeroer.exercise.android.curriculumdesign.core.data.remote.BaseNetworkService;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.AuthRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.ConnectionRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.di.AppInjector;

import java.io.IOException;

import lombok.Getter;

/**
 * 基础应用
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public abstract class BaseApplication extends Application {
    @Getter
    protected static volatile BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        AppInjector.setDatabase(onCreateDatabase());
        try {
            AppInjector.setNetworkService(onCreateNetworkService());
        } catch (IOException e) {
            Log.e("BaseApplication", "onCreate", e);
            throw new RuntimeException(e);
        }
        AppInjector.registerRepository(new ConnectionRepository());
        AppInjector.registerRepository(new AuthRepository());
        onInit();
    }

    protected abstract BaseDataBase onCreateDatabase();
    protected abstract BaseNetworkService onCreateNetworkService() throws IOException;

    protected abstract void onInit();
}