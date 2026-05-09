package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.menu;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;
import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.settings.SettingsActivity;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.about.AboutActivity;

public class MenuFragment extends BaseFragment<NoViewModel> {

    private TextView tvMenuSetting;
    private TextView tvMenuAbout;

    public MenuFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_admin_menu;
    }

    @Override
    protected void onInitView(View view) {
        tvMenuSetting = view.findViewById(R.id.tv_menu_setting);
        tvMenuAbout = view.findViewById(R.id.tv_menu_about);
    }

    @Override
    protected void onInitHandler() {
        tvMenuSetting.setOnClickListener(v -> goToSettings());
        tvMenuAbout.setOnClickListener(v -> {
            if (getActivity() == null) return;
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });
    }

    private void goToSettings() {
        if (getActivity() == null) return;
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }
}