package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.user.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private List<MessageItem> messageList;
    private OnMessageClickListener listener;

    public interface OnMessageClickListener {
        void onItemClick(MessageItem message, int position);
    }

    public MessageAdapter(List<MessageItem> messageList, OnMessageClickListener listener) {
        this.messageList = messageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MessageItem message = messageList.get(position);
        holder.tvName.setText(message.getName());
        holder.tvLastMessage.setText(message.getLastMessage());
        holder.tvTime.setText(message.getTime());
        holder.ivAvatar.setImageResource(message.getAvatarResId());

        int unread = message.getUnreadCount();
        if (unread > 0) {
            holder.tvUnreadBadge.setVisibility(View.VISIBLE);
            holder.tvUnreadBadge.setText(String.valueOf(unread));
        } else {
            holder.tvUnreadBadge.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(message, holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return messageList != null ? messageList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivAvatar;
        TextView tvName, tvLastMessage, tvTime, tvUnreadBadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.iv_message_avatar);
            tvName = itemView.findViewById(R.id.tv_message_name);
            tvLastMessage = itemView.findViewById(R.id.tv_message_last);
            tvTime = itemView.findViewById(R.id.tv_message_time);
            tvUnreadBadge = itemView.findViewById(R.id.tv_unread_badge);
        }
    }
}