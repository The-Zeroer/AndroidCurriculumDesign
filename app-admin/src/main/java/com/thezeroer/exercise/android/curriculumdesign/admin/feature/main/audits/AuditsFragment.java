package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

public class AuditsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private AuditAdapter adapter;
    private List<AuditItem> auditList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_audits;
    }

    @Override
    protected void onInitView(View view) {
        recyclerView = view.findViewById(R.id.recycler_audits);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void onInitHandler() {
        initData();
        setAdapter();
    }

    private void initData() {
        auditList = new ArrayList<>();
        auditList.add(new AuditItem(1, "用户实名认证", "申请人：小明  2025-01-10"));
        auditList.add(new AuditItem(2, "内容发布审核", "申请人：小红  2025-01-10"));
        auditList.add(new AuditItem(3, "订单退款审核", "申请人：小刚  2025-01-09"));
    }

    private void setAdapter() {
        adapter = new AuditAdapter(auditList, new AuditAdapter.OnAuditListener() {
            @Override
            public void onAccept(int position) {
                auditList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "审核通过", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onReject(int position) {
                auditList.remove(position);
                adapter.notifyItemRemoved(position);
                Toast.makeText(getContext(), "已拒绝", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}