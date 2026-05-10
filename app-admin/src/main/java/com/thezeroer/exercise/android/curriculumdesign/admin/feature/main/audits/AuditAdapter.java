package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.thezeroer.exercise.android.curriculumdesign.admin.R;

import java.util.List;

public class AuditAdapter extends RecyclerView.Adapter<AuditAdapter.ViewHolder> {

    private List<AuditItem> list;
    private OnAuditListener listener;

    public AuditAdapter(List<AuditItem> list, OnAuditListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_audit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AuditItem item = list.get(position);
        holder.tvTitle.setText(item.getTitle());
        holder.tvDesc.setText(item.getDesc());

        holder.btnAccept.setOnClickListener(v -> {
            listener.onAccept(position);
        });

        holder.btnReject.setOnClickListener(v -> {
            listener.onReject(position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        Button btnAccept, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_desc);
            btnAccept = itemView.findViewById(R.id.btn_accept);
            btnReject = itemView.findViewById(R.id.btn_reject);
        }
    }

    public interface OnAuditListener {
        void onAccept(int position);
        void onReject(int position);
    }
}