package com.thezeroer.exercise.android.curriculumdesign.admin.feature.about;

import android.os.Bundle;
import android.view.View;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;
import com.thezeroer.exercise.android.curriculumdesign.admin.R;

public class AboutActivity extends BaseActivity<NoViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onInitView() {
        // 删掉了原来的 findViewById，这里不需要改动
    }

    @Override
    protected void onInitHandler() {
        // 新增：返回按钮点击事件
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 关闭当前页面，回到上一页
            }
        });
    }

    @Override
    protected void onInitObserve() {

    }
}