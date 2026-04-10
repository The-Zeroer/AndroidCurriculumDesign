package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class LoginActivity extends AppCompatActivity {

    private ImageView ivAvatar;
    private EditText etAccount, etPassword;
    private CheckBox cbRememberPwd, cbAutoLogin;
    private Button btnLogin;
    private TextView tvNetworkConfig;

    // 保存网络配置地址
    private String currentServerAddress = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initNetworkConfig();
    }

    private void initViews() {
        ivAvatar = findViewById(R.id.iv_avatar);
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        cbRememberPwd = findViewById(R.id.cb_remember_pwd);
        cbAutoLogin = findViewById(R.id.cb_auto_login);
        btnLogin = findViewById(R.id.btn_login);
        tvNetworkConfig = findViewById(R.id.tv_network_config);
    }

    // ===================== 网络配置点击 & 接收结果 =====================
    private void initNetworkConfig() {
        tvNetworkConfig.setOnClickListener(v -> {
            NetworkConfigDialog dialog = new NetworkConfigDialog(LoginActivity.this);
            dialog.setOnConfigSubmitListener(address -> {
                // 这里收到弹窗返回的地址！
                currentServerAddress = address;

                // 演示：收到后可以打印/存储/使用
                Toast.makeText(LoginActivity.this,
                        "已配置：" + currentServerAddress,
                        Toast.LENGTH_LONG).show();
            });
            dialog.show();
        });
    }
}