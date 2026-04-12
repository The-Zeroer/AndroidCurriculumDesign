package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu;

import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 主布局
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        root.setBackgroundColor(0xFFFFFFFF);
        root.setPadding(60, 80, 60, 40);

        // ------------------------------
        // 文字内容（居中 偏上）
        // ------------------------------
        LinearLayout contentLayout = new LinearLayout(this);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
        contentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f
        ));
        contentLayout.setGravity(Gravity.CENTER);

        TextView title = new TextView(this);
        title.setText("关于");
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);

        TextView info = new TextView(this);
        info.setText("版本：v1.0.0\n课程设计");
        info.setTextSize(16);
        info.setGravity(Gravity.CENTER);
        info.setPadding(0, 20, 0, 0);

        contentLayout.addView(title);
        contentLayout.addView(info);

        // ------------------------------
        // 返回按钮（底部）
        // ------------------------------
        Button btnBack = new Button(this);
        btnBack.setText("返回");
        btnBack.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        btnBack.setOnClickListener(v -> finish());

        // 组装
        root.addView(contentLayout);
        root.addView(btnBack);

        setContentView(root);
    }
}