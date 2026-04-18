package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    private ImageView ivAvatar;
    private EditText etAccount, etPassword;
    private CheckBox cbRememberPwd, cbAutoLogin;
    private Button btnLogin;
    private Button btnNetworkConfig;

    private String serverHost = "47.100.78.49";

    private Integer serverPort = 7709;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {
        ivAvatar = findViewById(R.id.iv_avatar);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
        btnLogin = findViewById(R.id.btn_login);
        btnNetworkConfig = findViewById(R.id.tv_network_config);

    }

    @Override
    protected void onInitHandler() {
        super.onInitHandler();
        btnNetworkConfig.setOnClickListener(v -> {
            NetworkConfigDialog dialog = new NetworkConfigDialog(LoginActivity.this);

            // 这里要改成两个参数：host 和 port
            dialog.setOnConfigSubmitListener((host, port) -> {
                serverHost = host;
                serverPort = port;

                // 演示：收到后弹出 Toast
                Toast.makeText(LoginActivity.this,
                        "已配置：" + host + ":" + port,
                        Toast.LENGTH_LONG).show();
            });

            dialog.show();
        });
        btnLogin.setOnClickListener(v -> {
            // 1. 获取输入框内容
            String account = etAccount.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 2. 基础校验
            if (account.isEmpty()) {
                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (serverHost == null || serverPort == null) {
                Toast.makeText(this, "请先配置服务器地址和端口", Toast.LENGTH_SHORT).show();
                return;
            }

            // 3. 调用 ViewModel 登录（完全使用你原有的方法）
            boolean rememberPwd = cbRememberPwd.isChecked();
            boolean autoLogin = cbAutoLogin.isChecked();

            viewModel.login(account, password, rememberPwd, autoLogin, serverHost, serverPort);
        });
    }

    @Override
    protected void onInitObserve() {
        super.onInitObserve();
        viewModel.loginStatus.observe(this, resource -> {
            if (resource == null) return;

            switch (resource.status) {
                case LOADING -> {
                    btnLogin.setEnabled(false);
                    Toast.makeText(this, "正在登录...", Toast.LENGTH_SHORT).show();
                    return;
                }
                case SUCCESS -> {
                    // ========== 登录成功跳转主页 ==========
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish(); // 关闭登录页，按返回键不会回来
                }
                // 这里可以跳转到主页
                case FAILED -> {
                    Toast.makeText(this, "登录失败：" + resource.message, Toast.LENGTH_LONG).show();
                }
            }
            btnLogin.setEnabled(true);
        });

    }
    // ===================== 以下是新增代码（不修改你原有代码） =====================
    /**
     * 登录按钮点击（绑定布局中的 btn_login 即可）
     */


    /**
     * 监听登录结果（完全使用你原有的 loginStatus）
     */
    private void observeLoginResult() {

    }

}
