package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class AuditsFragment extends BaseFragment<AuditsViewMode> {

    private RecyclerView rvLogs;
    private AuditLogAdapter adapter;
    private final List<String> logFileList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audits; // 需要你自己创建一个包含 RecyclerView 的基础布局
    }

    @Override
    protected void onInitView(View view) {
        rvLogs = view.findViewById(R.id.rv_logs);
        rvLogs.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new AuditLogAdapter(logFileList, fileName -> {
            // 点击跳转到日志详情页
            AuditDetailFragment detailFragment = new AuditDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString("file_name", fileName);
            detailFragment.setArguments(bundle);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, detailFragment) // 确保 ID 与主 Activity 对应
                    .addToBackStack(null)
                    .commit();
        });
        
        rvLogs.setAdapter(adapter);
    }

    @Override
    protected void onInitHandler() { }

    @Override
    protected void onInitObserve() {
        if (viewModel == null) return;
        
        viewModel.getLogList().observe(getViewLifecycleOwner(), files -> {
            logFileList.clear();
            if (files != null) {
                logFileList.addAll(files);
            }
            adapter.notifyDataSetChanged();
        });

        viewModel.getToastEvent().observe(getViewLifecycleOwner(), msg -> 
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onInitData() {
        if (viewModel != null) {
            viewModel.fetchLogList();
        }
    }
}