package com.thezeroer.exercise.android.curriculumdesign.core;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * 基础活动
 * </br>对应的布局文件中的根id请统一定义为 android:id="@id/main_content_root"
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/04/08
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. 开启全屏/沉浸式边缘支持 (EdgeToEdge)
        EdgeToEdge.enable(this);
        // 2.设置内容布局
        setContentView(getLayoutId());
        // 3. 自动处理系统栏（状态栏、导航栏）内边距，防止 UI 被遮挡
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_content_root), (v, insets) -> {
            Insets system_bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(system_bars.left, system_bars.top, system_bars.right, system_bars.bottom);
            return insets;
        });
        // 4. 调用初始化方法
        onInitView();
        onInitHandler();
        onInitData();
    }

    /**
     * 子类实现：获取布局ID
     *
     * @return int
     */
    @LayoutRes
    protected abstract int getLayoutId();

    /**
     * 子类实现：初始化 UI 控件
     * 此处仅执行 findViewById，不写逻辑
     */
    protected abstract void onInitView();

    /**
     * 子类实现：绑定组件事件
     * 所有的点击事件、监听器在此处设置
     */
    protected abstract void onInitHandler();

    /**
     * 子类可选实现：初始化数据/请求
     */
    protected void onInitData() {}
}
