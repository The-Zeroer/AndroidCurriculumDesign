package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends BaseFragment {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;
    private List<ContactItem> contactList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));

        loadMockData();

        adapter = new ContactAdapter(contactList, new ContactAdapter.OnContactClickListener() {
            @Override
            public void onCallClick(ContactItem contact, int position) {
                Toast.makeText(getContext(), "呼叫 " + contact.getName(), Toast.LENGTH_SHORT).show();
                // 实际拨打逻辑：Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
                // startActivity(intent);
            }

            @Override
            public void onItemClick(ContactItem contact, int position) {
                Toast.makeText(getContext(), "查看 " + contact.getName() + " 的详情", Toast.LENGTH_SHORT).show();
                // 可跳转到联系人详情页
            }
        });

        rvContacts.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void onInitView(View view) {

    }

    @Override
    protected void onInitHandler() {

    }

    private void loadMockData() {
        contactList = new ArrayList<>();
        contactList.add(new ContactItem("张伟", "13812345678", R.drawable.ic_contacts));
        contactList.add(new ContactItem("李娜", "13987654321", R.drawable.ic_contacts));
        contactList.add(new ContactItem("王磊", "15011223344", R.drawable.ic_contacts));
        contactList.add(new ContactItem("赵敏", "17722334455", R.drawable.ic_contacts));
        contactList.add(new ContactItem("陈晨", "18833445566", R.drawable.ic_contacts));
    }
}