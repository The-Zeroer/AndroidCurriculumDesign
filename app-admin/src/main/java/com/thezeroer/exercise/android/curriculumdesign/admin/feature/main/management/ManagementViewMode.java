package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.management;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AccountManagementRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.AccountType;

/**
 * 管理视图模式
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/08
 */
public class ManagementViewMode extends BaseViewModel {

    private final AccountManagementRepository repository;

    // UI 状态反馈流
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> actionSuccessLiveData = new MutableLiveData<>();

    public ManagementViewMode(AccountManagementRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> getToastEvent() {
        return toastLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoadingLiveData;
    }

    public LiveData<Boolean> getActionSuccessEvent() {
        return actionSuccessLiveData;
    }

    /**
     * 注册账号
     */
    public void registerAccount(String accountId, String password, AccountType type) {
        if (accountId == null || accountId.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            toastLiveData.setValue("账号或密码不能为空");
            return;
        }

        isLoadingLiveData.setValue(true);
        repository.register(accountId, password, type)
                .thenAccept(resource -> {
                    isLoadingLiveData.postValue(false);
                    if (resource.isSuccess()) {
                        actionSuccessLiveData.postValue(true);
                        toastLiveData.postValue("注册成功: " + accountId);
                    } else {
                        toastLiveData.postValue("注册失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isLoadingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，注册失败");
                    return null;
                });
    }

    /**
     * 注销账号
     */
    public void unregisterAccount(String accountId) {
        if (accountId == null || accountId.trim().isEmpty()) {
            toastLiveData.setValue("目标账号ID不能为空");
            return;
        }

        isLoadingLiveData.setValue(true);
        repository.unregister(accountId)
                .thenAccept(resource -> {
                    isLoadingLiveData.postValue(false);
                    if (resource.isSuccess()) {
                        actionSuccessLiveData.postValue(true);
                        toastLiveData.postValue("注销成功: " + accountId);
                    } else {
                        toastLiveData.postValue("注销失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    isLoadingLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，注销失败");
                    return null;
                });
    }
}