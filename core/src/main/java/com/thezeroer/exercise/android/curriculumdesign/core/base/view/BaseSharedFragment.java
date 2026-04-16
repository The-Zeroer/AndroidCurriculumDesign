package com.thezeroer.exercise.android.curriculumdesign.core.base.view;

import android.os.Bundle;
import android.util.Log;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.ViewModelHelper;

/**
 * 基底共享片段
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public abstract class BaseSharedFragment<AVM extends BaseViewModel, FVM extends BaseViewModel> extends BaseFragment<FVM> {
    protected AVM activityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // 这会先执行 BaseFragment 的 initViewModel()
        initActivityViewModel();           // 接着执行 AVM 的初始化
    }

    private void initActivityViewModel() {
        try {
            activityViewModel = ViewModelHelper.createViewModel(requireActivity(), ViewModelHelper.getViewModelClass(this, 0));
        } catch (Exception e) {
            Log.e("BaseSharedFragment", "ActivityViewModel 初始化失败", e);
        }
    }
}
