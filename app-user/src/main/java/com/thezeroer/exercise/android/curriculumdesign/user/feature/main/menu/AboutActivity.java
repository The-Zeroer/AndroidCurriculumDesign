package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AboutActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        ));
        root.setPadding(50, 50, 50, 50);

        TextView text = new TextView(this);
        text.setText("关于界面\n版本号：v1.0\n开发者：课程设计");
        text.setTextSize(20);
        root.addView(text);

        setContentView(root);
    }
}