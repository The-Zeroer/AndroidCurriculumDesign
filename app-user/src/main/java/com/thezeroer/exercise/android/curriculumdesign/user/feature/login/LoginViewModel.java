package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.AuthRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.ConnectionRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.AccountType;

import java.util.concurrent.CompletableFuture;

/**
 * 登录视图模型
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/15
 */
public class LoginViewModel extends BaseViewModel {
    private final MediatorLiveData<Resource<String>> _loginStatus = new MediatorLiveData<>();
    public final LiveData<Resource<String>> loginStatus = _loginStatus;

    private final ConnectionRepository connectionRepository;
    private final AuthRepository authRepository;

    public LoginViewModel(ConnectionRepository connectionRepository, AuthRepository authRepository) {
        this.connectionRepository = connectionRepository;
        this.authRepository = authRepository;
    }

    public void login(String accountId, String accountPassword, boolean rememberMe, boolean autoLogin, String host, int port) {
        _loginStatus.setValue(Resource.loading());
        connectionRepository.connect(host, port)
                .thenCompose(connectResource -> {
                    if (connectResource.isFailed()) {
                        return CompletableFuture.completedFuture(
                                Resource.failed("连接服务器失败: " + connectResource.message)
                        );
                    }
                    return authRepository.login(accountId, accountPassword, rememberMe, autoLogin, AccountType.USER);
                })
                .thenAccept(_loginStatus::postValue)
                .exceptionally(ex -> {
                    _loginStatus.postValue(Resource.failed("系统异常: " + ex.getMessage()));
                    return null;
                });
    }
}
