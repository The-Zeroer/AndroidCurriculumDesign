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
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
        holder.ivAvatar.setImageResource(contact.getAvatarResId());

        // 条目点击
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(contact, position);
            }
        });

        // 拨号按钮点击
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