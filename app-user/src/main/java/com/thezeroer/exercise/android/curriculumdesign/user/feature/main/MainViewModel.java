package com.thezeroer.exercise.android.curriculumdesign.user.feature.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.thezeroer.exercise.android.curriculumdesign.user.data.repository.AccountRepository;
import com.thezeroer.exercise.android.curriculumdesign.user.model.AccountInfo;

public class MainViewModel extends ViewModel {

    private final AccountRepository accountRepository;
    private final MutableLiveData<AccountInfo> accountInfo = new MutableLiveData<>();

    // 构造方法中完成依赖初始化（课设环境可直接 new，实际项目建议注入）
    public MainViewModel() {
        accountRepository = new AccountRepository();
        loadAccountInfo();
    }

    // 从仓库加载账户信息
    private void loadAccountInfo() {
        // 实际开发中仓库方法可能返回 LiveData 或使用异步回调，此处演示同步获取
      //  AccountInfo info = accountRepository.getAccountInfo();
//        accountInfo.setValue(info);
    }

    // 公开不可变的 LiveData 供观察
    public LiveData<AccountInfo> getAccountInfo() {
        return accountInfo;
    }

    // 可供下拉刷新或重新请求
    public void refreshAccountInfo() {
        loadAccountInfo();
    }
}