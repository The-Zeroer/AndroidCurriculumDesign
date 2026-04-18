package com.thezeroer.exercise.android.curriculumdesign.user.feature.about;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class AboutActivity extends BaseActivity<NoViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onInitView() {
        // 空实现
    }


}