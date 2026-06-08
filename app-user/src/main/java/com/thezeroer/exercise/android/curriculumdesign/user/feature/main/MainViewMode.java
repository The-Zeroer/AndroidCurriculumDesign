package com.thezeroer.exercise.android.curriculumdesign.user.feature.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.AccountProfileRepository;

/**
 * 主视图模式
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/06
 */
public class MainViewMode extends BaseViewModel {
    private final AccountProfileRepository accountProfileRepository;

    // 1. 定义向外部暴露的账户名称与错误提示的 LiveData
    private final MutableLiveData<String> accountNameLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();

    public MainViewMode(AccountProfileRepository accountProfileRepository) {
        this.accountProfileRepository = accountProfileRepository;
    }

    public LiveData<String> getAccountName() {
        return accountNameLiveData;
    }

    public LiveData<String> getToastEvent() {
        return toastLiveData;
    }

    /**
     * 根据 accountId 异步获取账户名称
     *
     * @param accountId 账户ID
     */
    public void fetchAccountName(String accountId) {
        if (accountId == null || accountId.isEmpty()) {
            return;
        }

        accountProfileRepository.getName(accountId)
                .thenAccept(resource -> {
                    if (resource.isSuccess() && resource.getData() != null) {
                        // 获取成功，将名字投递给 UI 观察者
                        accountNameLiveData.postValue(resource.getData());
                    } else {
                        toastLiveData.postValue("获取用户名失败: " + resource.getMessage());
                    }
                })
                .exceptionally(throwable -> {
                    toastLiveData.postValue("网络异常，无法获取用户信息");
                    return null;
                });
    }
}