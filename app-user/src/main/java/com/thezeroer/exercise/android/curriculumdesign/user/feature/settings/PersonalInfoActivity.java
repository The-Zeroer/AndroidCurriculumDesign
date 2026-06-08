package com.thezeroer.exercise.android.curriculumdesign.user.feature.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;

public class PersonalInfoActivity extends BaseActivity<PersonalInfoViewMode> {

    public static Intent newIntent(Context context) {
        return new Intent(context, PersonalInfoActivity.class);
    }

    private ImageView ivAvatar;
    private EditText etNickname;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_personal_info;
    }

    @Override
    protected void onInitView() {
        // 返回按钮
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        ivAvatar = findViewById(R.id.iv_avatar);
        etNickname = findViewById(R.id.et_nickname);

        // 安全检查防备全局变量尚未就绪
        if (MainActivity.account != null) {
            etNickname.setText(MainActivity.account.getAccountName());
        }

        // 点击头像（仅提示，不实现图片选择）
        ivAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "头像修改功能待实现", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onInitHandler() {
        // 保存按钮逻辑移步至 Handler 钩子函数（更契合你底层的框架规范）
        findViewById(R.id.btn_save).setOnClickListener(v -> {
            String newName = etNickname.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 驱动 ViewModel 去做网络隔离修改
            if (viewModel != null) {
                viewModel.changeName(newName);
            }
        });
    }

    @Override
    protected void onInitObserve() {
        // 监听名字修改状态
        viewModel.getChangeNameStatus().observe(this, isSuccess -> {
            if (isSuccess) {
                String newName = etNickname.getText().toString().trim();
                // 核心：成功后同步刷新本地内存实体的数据，确保全局通讯录/弹窗同步刷新
                if (MainActivity.account != null) {
                    MainActivity.account.setAccountName(newName);
                }
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                finish(); // 关闭当前页面返回
            }
        });

        // 监听 Toast 错误提示
        viewModel.getToastEvent().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }
}