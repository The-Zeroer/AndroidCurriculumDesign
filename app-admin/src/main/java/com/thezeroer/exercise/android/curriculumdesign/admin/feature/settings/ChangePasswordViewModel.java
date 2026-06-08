package com.thezeroer.exercise.android.curriculumdesign.admin.feature.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.thezeroer.exercise.android.curriculumdesign.admin.data.repository.AccountProfileRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;

/**
 * 修改密码视图模型
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/04
 */
public class ChangePasswordViewModel extends BaseViewModel {
    private final AccountProfileRepository accountProfileRepository;
    private final MutableLiveData<Resource<Void>> _changePasswordStatus = new MutableLiveData<>();
    public final LiveData<Resource<Void>> changePasswordStatus = _changePasswordStatus;

    public ChangePasswordViewModel(AccountProfileRepository accountProfileRepository) {
        this.accountProfileRepository = accountProfileRepository;
    }

    public void changePassword(String oldPassword, String newPassword) {
        _changePasswordStatus.setValue(Resource.loading());
        accountProfileRepository.changePassword(oldPassword, newPassword)
                .thenAccept(_changePasswordStatus::postValue)
                .exceptionally(ex -> {
                    _changePasswordStatus.postValue(Resource.failed("系统内部异常: " + ex.getMessage()));
                    return null;
                });
    }
}