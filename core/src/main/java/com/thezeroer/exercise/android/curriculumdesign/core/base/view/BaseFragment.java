package com.thezeroer.exercise.android.curriculumdesign.core.base.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.ViewModelHelper;

/**
 * 基底片段
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/16
 */
public abstract class BaseFragment<VM extends BaseViewModel> extends Fragment {
    protected View rootView;
    protected VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutId(), container, false);
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInitView(view);
        onInitHandler();
        onInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // 释放根视图引用
        rootView = null;
        // 如果在子类中有具体的 Button、RecyclerView 等引用，也建议在这里处理
    }

    /**
     * 子类实现：获取布局ID
     *
     * @return int
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 子类实现：初始化 UI 控件
     * 此处仅执行 findViewById，不写逻辑
     */
    protected abstract void onInitView(View view);

    /**
     * 子类实现：绑定组件事件
     * 所有的点击事件、监听器在此处设置
     */
    protected abstract void onInitHandler();

    /**
     * 子类可选实现：初始化数据/请求
     */
    protected void onInitData() {
    }

    private void initViewModel() {
        try {
            viewModel = ViewModelHelper.createViewModel(this, ViewModelHelper.getViewModelClass(this, 0));
        } catch (Exception e) {
            Log.e("BaseFragment", "ViewModel 初始化失败", e);
        }
    }
}
