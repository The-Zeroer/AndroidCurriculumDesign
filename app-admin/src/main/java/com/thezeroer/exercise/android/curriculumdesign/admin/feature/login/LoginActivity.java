//package com.thezeroer.exercise.android.curriculumdesign.admin.feature.login;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.Handler;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Toast;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.thezeroer.exercise.android.curriculumdesign.admin.R;
//import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.MainActivity;
//import com.thezeroer.exercise.android.curriculumdesign.core.BaseActivity;
//
//public class LoginActivity extends BaseActivity {
//
//    private EditText etAccount, etPwd, etServer;
//    private CheckBox cbRemember, cbAuto;
//    private Button btnLogin;
//    private boolean isLogging = false; // 防重复点击
//    private SharedPreferences sp;
//
//    //注释
//
////    @SuppressLint("MissingInflatedId")
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_login);
////
////        // 绑定控件
////        etAccount = findViewById(R.id.et_account);
////        etPwd = findViewById(R.id.et_password);
////        etServer = findViewById(R.id.et_server_url);
////        cbRemember = findViewById(R.id.cb_remember_pwd);
////        cbAuto = findViewById(R.id.cb_auto_login);
////        btnLogin = findViewById(R.id.btn_login);
////
////        // 本地配置存储
////        sp = getSharedPreferences("LoginConfig", MODE_PRIVATE);
////        loadConfig(); // 加载保存的信息
////
////        // 登录点击
////        btnLogin.setOnClickListener(v -> {
////            if (isLogging) {
////                Toast.makeText(this, "正在登录中，请稍候...", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            String account = etAccount.getText().toString().trim();
////            String password = etPwd.getText().toString().trim();
////            String server = etServer.getText().toString().trim();
////
////            if (account.isEmpty()) {
////                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
////                return;
////            }
////            if (password.isEmpty()) {
////                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
////                return;
////            }
////            if (server.isEmpty()) {
////                Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
////                return;
////            }
////
////            // 保存配置
////            saveConfig(account, password, server);
////
////            // 模拟登录过程（防重复点击）
////            isLogging = true;
////            btnLogin.setEnabled(false);
////            btnLogin.setText("登录中...");
////
////            // 模拟1.5秒服务器响应
////            new Handler().postDelayed(() -> {
////                isLogging = false;
////                btnLogin.setEnabled(true);
////                btnLogin.setText("登 录");
////                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
////            }, 1500);
////        });
////    }
//
//
//    //注释
//
//    @Override
//    protected int getLayoutId() {
//        // 正确返回你的登录布局ID，替换0！
//        return R.layout.activity_login;
//    }
//    @Override
//    protected void onInitView() {
//        // 绑定控件
//        etAccount = findViewById(R.id.et_account);
//        etPwd = findViewById(R.id.et_password);
//        etServer = findViewById(R.id.et_server_url);
//        cbRemember = findViewById(R.id.cb_remember_pwd);
//        cbAuto = findViewById(R.id.cb_auto_login);
//        btnLogin = findViewById(R.id.btn_login);
//    }
//
//
//    @Override
//    protected void onInitHandler() {
//        btnLogin.setOnClickListener(v -> {
//            if (isLogging) {
//                Toast.makeText(this, "正在登录中，请稍候...", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String account = etAccount.getText().toString().trim();
//            String password = etPwd.getText().toString().trim();
//            String server = etServer.getText().toString().trim();
//
//            if (account.isEmpty()) {
//                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (password.isEmpty()) {
//                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (server.isEmpty()) {
//                Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            saveConfig(account, password, server);
//
//            isLogging = true;
//            btnLogin.setEnabled(false);
//            btnLogin.setText("登录中...");
//
//            new Handler().postDelayed(() -> {
//                isLogging = false;
//                btnLogin.setEnabled(true);
//                btnLogin.setText("登 录");
//                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
//
//// 👇 跳转到主页
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                // 关闭登录页
//                finish();
//            }, 1500);
//        });
//    }
//
//
//    @Override
//    protected void onInitData() {
//        // 在这里加载本地配置（替代原来的loadConfig()）
//        sp = getSharedPreferences("LoginConfig", MODE_PRIVATE);
//        loadConfig();
//        // 自动登录逻辑也可以放在这里
//    }
//
//    // 保存记住密码、自动登录、服务器地址
//    private void saveConfig(String account, String pwd, String server) {
//        SharedPreferences.Editor ed = sp.edit();
//        ed.putString("server", server);
//        ed.putBoolean("remember", cbRemember.isChecked());
//        ed.putBoolean("auto", cbAuto.isChecked());
//
//        if (cbRemember.isChecked()) {
//            ed.putString("account", account);
//            ed.putString("pwd", pwd);
//        } else {
//            ed.remove("account");
//            ed.remove("pwd");
//        }
//        ed.apply();
//    }
//
//    // 加载保存的信息
//    private void loadConfig() {
//        etServer.setText(sp.getString("server", ""));
//        cbRemember.setChecked(sp.getBoolean("remember", false));
//        cbAuto.setChecked(sp.getBoolean("auto", false));
//
//        if (cbRemember.isChecked()) {
//            etAccount.setText(sp.getString("account", ""));
//            etPwd.setText(sp.getString("pwd", ""));
//
//            // 自动登录
//            if (cbAuto.isChecked()) {
//                btnLogin.postDelayed(() -> btnLogin.performClick(), 500);
//            }
//        }
//    }
//}
//
//
//
//
//
//
//
package com.thezeroer.exercise.android.curriculumdesign.admin.feature.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.MainActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.BaseActivity;

public class LoginActivity extends BaseActivity {

    private EditText etAccount, etPwd, etServer;
    private CheckBox cbRemember, cbAuto;
    private Button btnLogin;
    private boolean isLogging = false; // 防重复点击
    private SharedPreferences sp;

    //注释

//    @SuppressLint("MissingInflatedId")
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        // 绑定控件
//        etAccount = findViewById(R.id.et_account);
//        etPwd = findViewById(R.id.et_password);
//        etServer = findViewById(R.id.et_server_url);
//        cbRemember = findViewById(R.id.cb_remember_pwd);
//        cbAuto = findViewById(R.id.cb_auto_login);
//        btnLogin = findViewById(R.id.btn_login);
//
//        // 本地配置存储
//        sp = getSharedPreferences("LoginConfig", MODE_PRIVATE);
//        loadConfig(); // 加载保存的信息
//
//        // 登录点击
//        btnLogin.setOnClickListener(v -> {
//            if (isLogging) {
//                Toast.makeText(this, "正在登录中，请稍候...", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            String account = etAccount.getText().toString().trim();
//            String password = etPwd.getText().toString().trim();
//            String server = etServer.getText().toString().trim();
//
//            if (account.isEmpty()) {
//                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (password.isEmpty()) {
//                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (server.isEmpty()) {
//                Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            // 保存配置
//            saveConfig(account, password, server);
//
//            // 模拟登录过程（防重复点击）
//            isLogging = true;
//            btnLogin.setEnabled(false);
//            btnLogin.setText("登录中...");
//
//            // 模拟1.5秒服务器响应
//            new Handler().postDelayed(() -> {
//                isLogging = false;
//                btnLogin.setEnabled(true);
//                btnLogin.setText("登 录");
//                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
//            }, 1500);
//        });
//    }


    //注释

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onInitView() {
        etAccount = findViewById(R.id.et_account);
        etPwd = findViewById(R.id.et_password);
        etServer = findViewById(R.id.et_server_url);
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

            String account = etAccount.getText().toString().trim();
            String password = etPwd.getText().toString().trim();
            String server = etServer.getText().toString().trim();

            if (account.isEmpty()) {
                Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (server.isEmpty()) {
                Toast.makeText(this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
                return;
            }

            saveConfig(account, password, server);

            isLogging = true;
            btnLogin.setEnabled(false);
            btnLogin.setText("登录中...");

            new Handler().postDelayed(() -> {
                isLogging = false;
                btnLogin.setEnabled(true);
                btnLogin.setText("登 录");
                Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }, 1500);
        });
    }

    @Override
    protected void onInitData() {
        sp = getSharedPreferences("LoginConfig", MODE_PRIVATE);
        loadConfig();
    }

    private void saveConfig(String account, String pwd, String server) {
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("server", server);
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

    private void loadConfig() {
        etServer.setText(sp.getString("server", ""));
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