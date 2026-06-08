package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import java.util.List;

/**
 * 审计日志适配器
 *
 * @author TBRTZ
 * @version 1.0.0
 * @since 2026/06/08
 */
public class AuditLogAdapter extends RecyclerView.Adapter<AuditLogAdapter.ViewHolder> {

    private final List<String> fileNames;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String fileName);
    }

    public AuditLogAdapter(List<String> fileNames, OnItemClickListener listener) {
        this.fileNames = fileNames;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 可以直接复用一个简单的原生布局，或者自定义 item_audit_log.xml
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String fileName = fileNames.get(position);
        holder.tvName.setText(fileName);
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(fileName);
        });
    }

    @Override
    public int getItemCount() {
        return fileNames == null ? 0 : fileNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(android.R.id.text1);
        }
    }
}