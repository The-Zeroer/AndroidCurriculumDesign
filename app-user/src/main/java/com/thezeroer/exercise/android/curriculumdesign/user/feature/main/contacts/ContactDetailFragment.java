package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.BaseViewModel;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.chat.ChatActivity;

public class ContactDetailFragment extends BaseFragment<NoViewModel> {

    private TextView tvName;
    private TextView tvId;
    private Button btnRemark;
    private Button btnQzone;
    private Button btnSendMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact_detail;
    }

    @Override
    protected void onInitView(View view) {
        tvName = view.findViewById(R.id.tv_name);
        tvId = view.findViewById(R.id.tv_id);
        btnRemark = view.findViewById(R.id.btn_remark);
        btnQzone = view.findViewById(R.id.btn_qzone);
        btnSendMsg = view.findViewById(R.id.btn_send_msg);
    }

    @Override
    protected void onInitHandler() {
        btnRemark.setOnClickListener(v ->
                Toast.makeText(getContext(), "设置好友备注", Toast.LENGTH_SHORT).show()
        );

        btnQzone.setOnClickListener(v ->
                Toast.makeText(getContext(), "查看动态", Toast.LENGTH_SHORT).show()
        );

        btnSendMsg.setOnClickListener(v -> {
            if (getActivity() == null) return;
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            String contactName = tvName.getText().toString();
            String contactId = tvId.getText().toString().replace("ID：", "");
            intent.putExtra("contact_name", contactName);
            intent.putExtra("target_id", contactId);
            startActivity(intent);
        });
    }

    @Override
    protected void onInitData() {
        Bundle args = getArguments();
        if (args != null) {
            String name = args.getString("contact_name", "未知用户");
            String id = args.getString("target_id", "00000000");
            tvName.setText(name);
            tvId.setText("ID：" + id);
        }
    }
}