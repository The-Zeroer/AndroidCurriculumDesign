package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;

public class AuditDetailFragment extends BaseFragment<AuditsViewMode> {

    private TextView tvLogContent;
    private TextView tvTitle;
    private ProgressBar pbLoading;
    private String currentFileName;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audit_detail; // 建议包含标题、ScrollView包裹的TextView、ProgressBar
    }

    @Override
    protected void onInitView(View view) {
        tvLogContent = view.findViewById(R.id.tv_log_content);

        // 🟢 核心修复：赋予 TextView 原生滚动能力，完美兼容长按复制
//        tvLogContent.setMovementMethod(new android.text.method.ScrollingMovementMethod());

        tvTitle = view.findViewById(R.id.tv_audit_title);
        pbLoading = view.findViewById(R.id.pb_loading);

        if (getArguments() != null) {
            currentFileName = getArguments().getString("file_name");
            tvTitle.setText(currentFileName);
        }

        // 返回按钮逻辑
        view.findViewById(R.id.btn_back).setOnClickListener(v -> {
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    @Override
    protected void onInitHandler() { }

    @Override
    protected void onInitObserve() {
        if (viewModel == null) return;

        viewModel.getLogContent().observe(getViewLifecycleOwner(), content -> {
            tvLogContent.setText(content);
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getToastEvent().observe(getViewLifecycleOwner(), msg ->
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onInitData() {
        if (viewModel != null && currentFileName != null) {
            viewModel.fetchLogContent(currentFileName);
        }
    }
}