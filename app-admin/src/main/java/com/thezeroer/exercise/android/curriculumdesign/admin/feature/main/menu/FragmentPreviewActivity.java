package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.menu;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 纯预览用 Activity，没有任何 XML 布局文件
public class FragmentPreviewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            // 1. 纯代码创建一个布局容器（用来放 Fragment）
            FrameLayout container = new FrameLayout(this);
            container.setLayoutParams(new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            ));
            // 把代码创建的布局设为页面内容
            setContentView(container);
            // 给容器设置一个 id
            container.setId(android.view.View.generateViewId());

            // 2. 加载你的 MenuFragment，直接预览！
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container.getId(), new MenuFragment())
                    .commit();

    }
}