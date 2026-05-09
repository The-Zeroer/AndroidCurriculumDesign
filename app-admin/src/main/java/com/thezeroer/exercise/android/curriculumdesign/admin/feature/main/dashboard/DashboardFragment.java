package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits.AuditsFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;

public class DashboardFragment extends BaseFragment {

    private TextView tvUserCount;
    private TextView tvAuditCount;
    private Button btnToAudit;

    // 1. 必须写：布局文件
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dashboard;
    }

    // 2. 必须写：初始化控件
    @Override
    protected void onInitView(View view) {
        tvUserCount = view.findViewById(R.id.tv_user_count);
        tvAuditCount = view.findViewById(R.id.tv_audit_count);
        btnToAudit = view.findViewById(R.id.btn_to_audit);
    }

    // 3. 必须写：点击事件
    @Override
    protected void onInitHandler() {
//        btnToAudit.setOnClickListener(v -> {
//            getParentFragmentManager()
//                    .beginTransaction()
//                    .replace(R.id.container, new AuditsFragment())
//                    .addToBackStack(null)
//                    .commit();
//        });
    }

    // 4. 加载数据
    @Override
    protected void onInitData() {
        tvUserCount.setText("136");
        tvAuditCount.setText("23");
    }
}