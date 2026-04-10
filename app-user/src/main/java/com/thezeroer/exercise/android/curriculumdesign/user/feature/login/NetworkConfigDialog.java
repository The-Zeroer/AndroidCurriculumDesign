package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class NetworkConfigDialog extends Dialog {

    private EditText etServerAddress;
    private Button btnCancel, btnConfirm;
    private OnConfigSubmitListener listener;

    public NetworkConfigDialog(@NonNull Context context) {
        super(context);
    }

    // 回调接口：把地址返回给 LoginActivity
    public interface OnConfigSubmitListener {
        void onSubmit(String address);
    }

    // 设置回调
    public void setOnConfigSubmitListener(OnConfigSubmitListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_network_config);

        etServerAddress = findViewById(R.id.et_server_address);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_confirm);

        // 取消
        btnCancel.setOnClickListener(v -> dismiss());

        // 确定 → 返回地址给登录界面
        btnConfirm.setOnClickListener(v -> {
            String addr = etServerAddress.getText().toString().trim();
            if (addr.isEmpty()) {
                Toast.makeText(getContext(), "请输入服务器地址", Toast.LENGTH_SHORT).show();
                return;
            }

            if (listener != null) {
                listener.onSubmit(addr); // 把地址返回
            }
            dismiss();
            Toast.makeText(getContext(), "网络配置成功", Toast.LENGTH_SHORT).show();
        });
    }
}