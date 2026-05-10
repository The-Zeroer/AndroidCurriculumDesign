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

public class PersonalInfoActivity extends BaseActivity {

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

        // 加载已保存的昵称
        String nickname = getSharedPreferences("user", MODE_PRIVATE)
                .getString("nickname", "默认昵称");
        etNickname.setText(nickname);

        // 点击头像（仅提示，不实现图片选择）
        ivAvatar.setOnClickListener(v -> {
            Toast.makeText(this, "头像修改功能待实现", Toast.LENGTH_SHORT).show();
        });

        // 保存
        findViewById(R.id.btn_save).setOnClickListener(v -> {
            String newName = etNickname.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            // 保存昵称
            getSharedPreferences("user", MODE_PRIVATE)
                    .edit()
                    .putString("nickname", newName)
                    .apply();

            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onInitHandler() {

    }
}