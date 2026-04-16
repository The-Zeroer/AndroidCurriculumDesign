package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import android.content.Intent;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;

/**
 * 登录活动
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public class LoginActivity extends BaseActivity<LoginViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {

    }

    @Override
    protected void onInitHandler() {

    }

    @Override
    protected void onInitObserve() {
        viewModel.loginStatus.observe(this, resource -> {
            if (resource == null || resource.isLoading()) {
                return;
            }
            if (resource.isFailed()) {
                Toast.makeText(this, "登录失败: " + (resource.message != null ? resource.message : "未知连接错误"), Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }

    @Override
    protected void onInitData() {
        viewModel.login("123", "123", true, true, "aly.thezeroer.com", 7709);
    }
}
