package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.mode.Account;
import com.thezeroer.exercise.android.curriculumdesign.user.R;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends BaseFragment<ContactsViewMode> {

    private RecyclerView rvContacts;
    private ContactAdapter adapter;

    // 初始化集合，防止适配器创建时传入 null 导致崩溃
    private final List<ContactItem> contactList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        // 1. 返回布局文件
        return R.layout.fragment_contacts;
    }

    @Override
    protected void onInitView(View view) {
        // 2. 绑定控件并设置布局管理器
        rvContacts = view.findViewById(R.id.rv_contacts);
        rvContacts.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void onInitHandler() {
        // 3. 初始化事件监听与适配器配置
        adapter = new ContactAdapter(contactList, new ContactAdapter.OnContactClickListener() {
            @Override
            public void onCallClick(ContactItem contact, int position) {
                Toast.makeText(getContext(), "呼叫 " + contact.getName(), Toast.LENGTH_SHORT).show();
                // 实际拨打逻辑：
                // Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.getPhone()));
                // startActivity(intent);
            }

            @Override
            public void onItemClick(ContactItem contact, int position) {
                Toast.makeText(getContext(), "查看 " + contact.getName() + " 的详情", Toast.LENGTH_SHORT).show();

                // 跳转到联系人详情页
                ContactDetailFragment detailFragment = new ContactDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putString("contact_name", contact.getName());
                bundle.putString("target_id", contact.getId());
                detailFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        rvContacts.setAdapter(adapter);
    }

    @Override
    protected void onInitObserve() {
        // 观察联系人列表
        viewModel.getContactsList().observe(getViewLifecycleOwner(), accounts -> {
            if (accounts != null) {
                contactList.clear();
                // 将底层返回的 Account 实体转换为 UI 需要的 ContactItem
                for (Account account : accounts) {
                    contactList.add(new ContactItem(
                            account.getAccountId(),
                            account.getAccountName(),
                            R.drawable.ic_contacts // 兜底默认头像资源
                    ));
                }
                adapter.notifyDataSetChanged();
            }
        });

        // 观察错误提示
        viewModel.getToastEvent().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });

        // 观察 Loading 状态（若有进度条控件可在此处绑定显示/隐藏）
        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // pbLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    @Override
    protected void onInitData() {
        // 5. 触发首屏数据加载
        if (viewModel != null) {
            viewModel.fetchContacts();
        }
    }
}