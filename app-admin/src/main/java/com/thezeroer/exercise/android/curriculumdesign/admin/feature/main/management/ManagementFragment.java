package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.management;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.enums.AccountType;

/**
 * 管理片段
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public class ManagementFragment extends BaseFragment<ManagementViewMode> {

    private EditText etRegId, etRegPwd, etUnregId;
    private RadioGroup rgAccountType;
    private Button btnRegister, btnUnregister;
    private ProgressBar pbLoading;

    @Override
    protected int getLayoutId() {
        // 替换为实际的管理布局
        return R.layout.fragment_management;
    }

    @Override
    protected void onInitView(View view) {
        etRegId = view.findViewById(R.id.et_reg_id);
        etRegPwd = view.findViewById(R.id.et_reg_pwd);
        rgAccountType = view.findViewById(R.id.rg_account_type);
        btnRegister = view.findViewById(R.id.btn_register);

        etUnregId = view.findViewById(R.id.et_unreg_id);
        btnUnregister = view.findViewById(R.id.btn_unregister);

        pbLoading = view.findViewById(R.id.pb_loading);
    }

    @Override
    protected void onInitHandler() {
        // 注册按钮点击事件
        btnRegister.setOnClickListener(v -> {
            String id = etRegId.getText().toString().trim();
            String pwd = etRegPwd.getText().toString().trim();

            // 获取选择的账号类型
            AccountType selectedType = AccountType.USER;
            if (rgAccountType.getCheckedRadioButtonId() == R.id.rb_type_admin) {
                selectedType = AccountType.ADMIN;
            }

            if (viewModel != null) {
                viewModel.registerAccount(id, pwd, selectedType);
            }
        });

        // 注销按钮点击事件
        btnUnregister.setOnClickListener(v -> {
            String id = etUnregId.getText().toString().trim();
            if (viewModel != null) {
                viewModel.unregisterAccount(id);
            }
        });
    }

    @Override
    protected void onInitObserve() {
        super.onInitObserve();
        if (viewModel == null) return;

        // 监听 Toast 提示
        viewModel.getToastEvent().observe(getViewLifecycleOwner(), message -> {
            if (getContext() != null) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        // 监听加载动画状态
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            if (pbLoading != null) {
                pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);

                // 加载时禁用按钮防止重复点击
                btnRegister.setEnabled(!isLoading);
                btnUnregister.setEnabled(!isLoading);
            }
        });

        // 监听操作成功事件（成功后清空输入框）
        viewModel.getActionSuccessEvent().observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                etRegId.setText("");
                etRegPwd.setText("");
                etUnregId.setText("");
                etRegId.clearFocus();
                etUnregId.clearFocus();
            }
        });
    }

    @Override
    protected void onInitData() {
        super.onInitData();
    }
}