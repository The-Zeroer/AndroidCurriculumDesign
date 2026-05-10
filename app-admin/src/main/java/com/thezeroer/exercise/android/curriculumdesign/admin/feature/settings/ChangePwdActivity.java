package com.thezeroer.exercise.android.curriculumdesign.admin.feature.settings;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;

/**
 * 修改密码页面
 */
public class ChangePwdActivity extends BaseActivity {

    // 静态跳转方法
    public static Intent newIntent(Context context) {
        return new Intent(context, ChangePwdActivity.class);
    }

    private EditText etOldPwd, etNewPwd, etConfirmPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_pwd;
    }

    @Override
    protected void onInitView() {

        // 返回按钮
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        // 输入框
        etOldPwd = findViewById(R.id.et_old_pwd);
        etNewPwd = findViewById(R.id.et_new_pwd);
        etConfirmPwd = findViewById(R.id.et_confirm_pwd);

        // 提交按钮
        findViewById(R.id.btn_submit).setOnClickListener(v -> changePassword());
    }

    @Override
    protected void onInitHandler() {

    }

    /**
     * 修改密码逻辑
     */
    private void changePassword() {

        String oldPwd = etOldPwd.getText().toString().trim();
        String newPwd = etNewPwd.getText().toString().trim();
        String confirmPwd = etConfirmPwd.getText().toString().trim();

        // 1. 空值验证
        if (oldPwd.isEmpty() || newPwd.isEmpty() || confirmPwd.isEmpty()) {
            Toast.makeText(this, "请填写完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        // 2. 新旧密码是否相同
        if (oldPwd.equals(newPwd)) {
            Toast.makeText(this, "新密码不能与旧密码相同", Toast.LENGTH_SHORT).show();
            return;
        }

        // 3. 确认密码验证
        if (!newPwd.equals(confirmPwd)) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        /*// 保存新密码
        getSharedPreferences("user_info", MODE_PRIVATE)
                .edit()
                .putString("password", newPwd)
                .apply();

        Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();

        finish();*/
    }
}