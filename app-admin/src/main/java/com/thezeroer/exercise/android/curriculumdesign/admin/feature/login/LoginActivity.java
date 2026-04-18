package com.thezeroer.exercise.android.curriculumdesign.admin.feature.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.MainActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;

public class LoginActivity extends BaseActivity<LoginViewModel> {

    // 控件变量名与XML ID保持一致
    private EditText etAccount, etPwd, etServerIp, etServerPort;
    private CheckBox cbRemember, cbAuto;
    private Button btnLogin;

    private boolean isLogging = false;
    private SharedPreferences sp;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {
        // 绑定控件（和你XML里的ID完全一致）
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_password);
        etServerIp = findViewById(R.id.et_login_server_ip);
        etServerPort = findViewById(R.id.et_login_server_port);
        cbRemember = findViewById(R.id.cb_remember_pwd);
        cbAuto = findViewById(R.id.cb_auto_login);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void onInitHandler() {
        btnLogin.setOnClickListener(v -> {
            if (isLogging) {
                Toast.makeText(this, "正在登录中，请稍候...", Toast.LENGTH_SHORT).show();
                return;
            }

            // 获取输入
            String account = etAccount.getText().toString().trim();
            String password = etPwd.getText().toString().trim();
            String serverIp = etServerIp.getText().toString().trim();
            String serverPortStr = etServerPort.getText().toString().trim();

            // 非空校验
            if (account.isEmpty()) {
                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
//            if (serverIp.isEmpty()) {
//                Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (serverPortStr.isEmpty()) {
//                Toast.makeText(this, "请输入服务器端口", Toast.LENGTH_SHORT).show();
//                return;
//            }

            serverIp = "aly.thezeroer.com";
            serverPortStr = "7709";

            // 保存配置
            saveConfig(account, password, serverIp, serverPortStr);

            // 防重复点击
            isLogging = true;
            btnLogin.setEnabled(false);
            btnLogin.setText("登录中...");

            // ===================== 严格匹配ViewModel的login方法签名 =====================
            int serverPort;
            try {
                serverPort = Integer.parseInt(serverPortStr);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "端口号必须是数字", Toast.LENGTH_SHORT).show();
                isLogging = false;
                btnLogin.setEnabled(true);
                btnLogin.setText("登 录");
                return;
            }

            viewModel.login(
                    account,
                    password,
                    cbRemember.isChecked(),
                    cbAuto.isChecked(),
                    serverIp,
                    serverPort
            );
        });
    }

    //登录状态判断
    @Override
    protected void onInitObserve() {
        viewModel.loginStatus.observe(this, resource -> {
            if (resource == null ) {
                return;
            }

            // 恢复按钮状态
            isLogging = false;
            switch (resource.status) {
                case LOADING -> {
                    btnLogin.setEnabled(false);
                    Toast.makeText(this, "正在登录...", Toast.LENGTH_SHORT).show();
                    btnLogin.setText("登 录");
                }
                case SUCCESS -> {
                    Toast.makeText(this, "登录成功！", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                // 这里可以跳转到主页
                case FAILED -> {
                    btnLogin.setEnabled(true);
                    Toast.makeText(this, "登录失败：" + resource.message, Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    @Override
    protected void onInitData() {
        sp = getSharedPreferences("LoginConfig", MODE_PRIVATE);
        loadConfig();
    }

    // 保存配置（支持IP+端口+记住密码/自动登录）
    private void saveConfig(String account, String pwd, String serverIp, String serverPort) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("server_ip", serverIp);
        ed.putString("server_port", serverPort);
        ed.putBoolean("remember", cbRemember.isChecked());
        ed.putBoolean("auto", cbAuto.isChecked());

        if (cbRemember.isChecked()) {
            ed.putString("account", account);
            ed.putString("pwd", pwd);
        } else {
            ed.remove("account");
            ed.remove("pwd");
        }
        ed.apply();
    }

    // 读取配置
    private void loadConfig() {
        etServerIp.setText(sp.getString("server_ip", ""));
        etServerPort.setText(sp.getString("server_port", ""));
        cbRemember.setChecked(sp.getBoolean("remember", false));
        cbAuto.setChecked(sp.getBoolean("auto", false));

        if (cbRemember.isChecked()) {
            etAccount.setText(sp.getString("account", ""));
            etPwd.setText(sp.getString("pwd", ""));

            if (cbAuto.isChecked()) {
                btnLogin.postDelayed(() -> btnLogin.performClick(), 500);
            }
        }
    }
}