package com.thezeroer.exercise.android.curriculumdesign.user.feature.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.AccountProfileRepository;

/**
 * 个人信息查看模式
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/07
 */
public class PersonalInfoViewMode extends BaseViewModel {

    private final AccountProfileRepository accountProfileRepository = new AccountProfileRepository();

    // 状态下发数据流
    private final MutableLiveData<Boolean> changeNameStatusLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> toastLiveData = new MutableLiveData<>();

    public LiveData<Boolean> getChangeNameStatus() {
        return changeNameStatusLiveData;
    }

    public LiveData<String> getToastEvent() {
        return toastLiveData;
    }

    /**
     * 调用远程服务修改账户昵称
     *
     * @param newName 新昵称
     */
    public void changeName(String newName) {
        accountProfileRepository.changeName(newName)
                .thenAccept(resource -> {
                    if (resource.isSuccess()) {
                        changeNameStatusLiveData.postValue(true);
                    } else {
                        changeNameStatusLiveData.postValue(false);
                        toastLiveData.postValue("修改失败: " + resource.getMessage());
                    }
                }).exceptionally(throwable -> {
                    changeNameStatusLiveData.postValue(false);
                    toastLiveData.postValue("网络异常，请稍后重试");
                    return null;
                });
    }
}