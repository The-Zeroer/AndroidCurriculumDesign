package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.user.R;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    private List<ContactItem> contactList;
    private OnContactClickListener listener;

    public interface OnContactClickListener {
        void onCallClick(ContactItem contact, int position);
        void onItemClick(ContactItem contact, int position);
    }

    public ContactAdapter(List<ContactItem> contactList, OnContactClickListener listener) {
        this.contactList = contactList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContactItem contact = contactList.get(position);

        // 1. 绑定姓名与头像
        holder.tvName.setText(contact.getName());
        holder.ivAvatar.setImageResource(contact.getAvatarResId());

        // 2. 【优化点】由于没有手机号数据，将 tvPhone 用作展示账号 ID，规避编译报错
        holder.tvPhone.setText("账号: " + contact.getId());

        // 条目点击事件
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(contact, position);
            }
        });

        // 拨号/呼叫按钮点击事件
        holder.ivCall.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCallClick(contact, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList != null ? contactList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar, ivCall;
        TextView tvName, tvPhone;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_contact_avatar);
            ivCall = itemView.findViewById(R.id.iv_call);
            tvName = itemView.findViewById(R.id.tv_contact_name);
            tvPhone = itemView.findViewById(R.id.tv_contact_phone);
        }
    }
}