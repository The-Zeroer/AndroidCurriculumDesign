package com.thezeroer.exercise.android.curriculumdesign.admin.feature.settings;

import android.widget.ImageView;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;

/**
 * 设置活动
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public class SettingsActivity extends BaseActivity<NoViewModel> {
    @Override
    protected int getLayoutId() {
        // 绑定activity_settings.xml设置布局
        return R.layout.activity_settings;
    }

    @Override
    protected void onInitView() {
        // 1. 实现返回按钮逻辑
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish()); // 点击返回上一页

        // 2. 绑定“修改密码”并跳转对应页面
        findViewById(R.id.tv_change_pwd).setOnClickListener(v -> {
            // 跳转到修改密码页
            startActivity(ChangePasswordActivity.newIntent(this));
        });
    }

    @Override
    protected void onInitHandler() {

    }
}
