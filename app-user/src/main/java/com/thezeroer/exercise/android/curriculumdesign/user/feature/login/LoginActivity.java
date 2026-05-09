package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

    private String serverHost;
    private Integer serverPort;
    private SharedPreferences sp;

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

        // 初始化配置存储并读取
        sp = getSharedPreferences("UserLoginConfig", MODE_PRIVATE);
        loadNetworkConfig();
    }

    @Override
    protected void onInitHandler() {
        super.onInitHandler();

        // 网络配置按钮
        btnNetworkConfig.setOnClickListener(v -> {
            NetworkConfigDialog dialog = new NetworkConfigDialog(LoginActivity.this);

            dialog.setOnConfigSubmitListener((host, port) -> {
                serverHost = host;
                serverPort = port;
                saveNetworkConfig(host, String.valueOf(port));
                Toast.makeText(LoginActivity.this, "已配置：" + host + ":" + port, Toast.LENGTH_LONG).show();
            });

            dialog.show();
            dialog.setDefaultConfig(serverHost, String.valueOf(serverPort == null ? "" : serverPort));

        });

        // 登录按钮（修复了括号和分号问题）
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

            // 3. 调用 ViewModel 登录（修复了括号和分号）
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

            // 修复：用传统 case 语法，兼容所有 Java 版本
            switch (resource.status) {
                case LOADING:
                    btnLogin.setEnabled(false);
                    Toast.makeText(this, "正在登录...", Toast.LENGTH_SHORT).show();
                    break;
                case SUCCESS:
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                    break;
                case FAILED:
                    Toast.makeText(this, "登录失败：" + resource.message, Toast.LENGTH_LONG).show();
                    break;
            }
            btnLogin.setEnabled(true);
        });
    }

    // 保存网络配置到本地
    private void saveNetworkConfig(String serverIp, String serverPort) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("server_ip", serverIp);
        ed.putString("server_port", serverPort);
        ed.apply();
    }

    // 读取本地保存的网络配置
    private void loadNetworkConfig() {
        String host = sp.getString("server_ip", "");
        String portStr = sp.getString("server_port", "");

        if (!host.isEmpty() && !portStr.isEmpty()) {
            try {
                serverHost = host;
                serverPort = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                serverHost = null;
                serverPort = null;
            }
        }
    }
}