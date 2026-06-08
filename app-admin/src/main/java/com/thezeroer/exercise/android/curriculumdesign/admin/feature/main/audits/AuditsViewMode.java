package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AuditsRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 审计视图模式
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/08
 */
public class AuditsViewMode extends BaseViewModel {

    private final AuditsRepository repository;

    private final MutableLiveData<List<String>> logListLiveData = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> logContentLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);

    public AuditsViewMode(AuditsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<String>> getLogList() { return logListLiveData; }
    public LiveData<String> getLogContent() { return logContentLiveData; }
    public LiveData<String> getToastEvent() { return toastLiveData; }
    public LiveData<Boolean> getIsLoading() { return isLoadingLiveData; }

    public void fetchLogList() {
        isLoadingLiveData.setValue(true);
        repository.getLogList()
                .thenAccept(resource -> {
                    isLoadingLiveData.postValue(false);
                    if (resource.isSuccess()) {
                        logListLiveData.postValue(resource.getData());
                    } else {
                        toastLiveData.postValue("获取日志列表失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isLoadingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，无法获取日志列表");
                    return null;
                });
    }

    public void fetchLogContent(String fileName) {
        isLoadingLiveData.setValue(true);
        repository.getLogContent(fileName)
                .thenAccept(resource -> {
                    isLoadingLiveData.postValue(false);
                    if (resource.isSuccess()) {
                        logContentLiveData.postValue(resource.getData());
                    } else {
                        toastLiveData.postValue("读取日志失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isLoadingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，无法读取日志内容");
                    return null;
                });
    }
}