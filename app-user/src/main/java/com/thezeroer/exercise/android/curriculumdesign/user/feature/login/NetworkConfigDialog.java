package com.thezeroer.exercise.android.curriculumdesign.user.feature.login;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class NetworkConfigDialog extends Dialog {

    private EditText etServerHost;
    private EditText etServerPort;
    private Button btnCancel, btnConfirm;
    private OnConfigSubmitListener listener;

    public NetworkConfigDialog(@NonNull Context context) {
        super(context);
    }

    // 回调接口：同时返回 host 和 port
    public interface OnConfigSubmitListener {
        void onSubmit(String host, int port);
    }

    public void setOnConfigSubmitListener(OnConfigSubmitListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_network_config);

        // 绑定控件
        etServerHost = findViewById(R.id.et_server_host);
        etServerPort = findViewById(R.id.et_server_port);
        btnCancel = findViewById(R.id.btn_cancel);
        btnConfirm = findViewById(R.id.btn_confirm);

        // 取消按钮
        btnCancel.setOnClickListener(v -> dismiss());

        // 确定按钮
        btnConfirm.setOnClickListener(v -> {
            String host = etServerHost.getText().toString().trim();
            String portStr = etServerPort.getText().toString().trim();

            // 校验
            if (host.isEmpty()) {
                Toast.makeText(getContext(), "请输入服务器地址", Toast.LENGTH_SHORT).show();
                return;
            }
            if (portStr.isEmpty()) {
                Toast.makeText(getContext(), "请输入服务器端口", Toast.LENGTH_SHORT).show();
                return;
            }

            int port;
            try {
                port = Integer.parseInt(portStr);
            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "端口必须是有效数字", Toast.LENGTH_SHORT).show();
                return;
            }

            // 回调给 Activity
            if (listener != null) {
                listener.onSubmit(host, port);
            }

            dismiss();
            Toast.makeText(getContext(), "网络配置成功", Toast.LENGTH_SHORT).show();
        });
    }

    // 可选：设置默认值
    public void setDefaultConfig(String host, String port) {
        if (etServerHost != null) {
            etServerHost.setText(host);
        }
        if (etServerPort != null) {
            etServerPort.setText(port);
        }
    }
}