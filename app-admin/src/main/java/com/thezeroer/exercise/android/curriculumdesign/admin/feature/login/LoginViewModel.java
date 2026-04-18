package com.thezeroer.exercise.android.curriculumdesign.admin.feature.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.thezeroer.exercise.android.curriculumdesign.core.base.model.Resource;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.AuthRepository;
import com.thezeroer.exercise.android.curriculumdesign.core.data.repository.ConnectionRepository;

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
        LiveData<Resource<String>> connectSource = connectionRepository.connect(host, port);
        _loginStatus.addSource(connectSource, connectResource -> {
            if (connectResource.isLoading()) {
                return;
            }
            _loginStatus.removeSource(connectSource);
            if (connectResource.isFailed()) {
                _loginStatus.setValue(Resource.failed("连接服务器失败: " + connectResource.message));
                return;
            }
            LiveData<Resource<String>> authSource = authRepository.login(accountId, accountPassword, rememberMe, autoLogin);
            _loginStatus.addSource(authSource, authResource -> {
                if (authResource.isLoading()) {
                    return;
                }
                _loginStatus.removeSource(authSource);
                if (authResource.isFailed()) {
                    _loginStatus.setValue(Resource.failed("登录失败: " + authResource.message));
                    return;
                }
                _loginStatus.setValue(authResource);
            });
        });
    }
}
