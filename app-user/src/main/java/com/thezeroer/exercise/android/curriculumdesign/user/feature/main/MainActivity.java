package com.thezeroer.exercise.android.curriculumdesign.user.feature.main;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.core.mode.Account;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts.ContactsFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu.MenuFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages.MessagesFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.organization.OrganizationFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.profile.ProfilePopup;

public class MainActivity extends BaseActivity<MainViewMode> {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private ImageView ivAvatar;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private ProfilePopup profilePopup;

    public static volatile Account account;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        toolbar = findViewById(R.id.toolbar);
        tvToolbarTitle = findViewById(R.id.tv_toolbar_title);
        ivAvatar = findViewById(R.id.iv_avatar);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 设置 Toolbar 为 ActionBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        fragmentManager = getSupportFragmentManager();

        // 默认显示消息 Fragment
        loadFragment(new MessagesFragment(), "消息");

        // 初始化个人信息弹窗
        profilePopup = new ProfilePopup(this);
    }

    @Override
    protected void onInitHandler() {
        // 底部导航栏切换监听
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_messages) {
                loadFragment(new MessagesFragment(), "消息");
                return true;
            } else if (id == R.id.nav_contacts) {
                loadFragment(new ContactsFragment(), "联系人");
                return true;
            } else if (id == R.id.nav_organization) {
                loadFragment(new OrganizationFragment(), "组织");
                return true;
            } else if (id == R.id.nav_menu) {
                loadFragment(new MenuFragment(), "菜单");
                return true;
            }
            return false;
        });

        // 点击头像显示个人信息弹窗
        ivAvatar.setOnClickListener(v -> {
            if (profilePopup != null) {
                profilePopup.setUserInfo(account.getAccountName(), null, account.getAccountId());
                profilePopup.showBelow(ivAvatar);
            }
        });
    }

    @Override
    protected void onInitObserve() {
        if (viewModel == null) return;

        // 监听账户名称获取结果
        viewModel.getAccountName().observe(this, name -> {
            if (name != null && account != null) {
                account.setAccountName(name);
                profilePopup.setUserInfo(name, null, account.getAccountId());
            }
        });

        // 监听错误/异常提示
        viewModel.getToastEvent().observe(this, message -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    protected void onInitData() {
        String accountId = getIntent().getStringExtra("account_id");

        // 初始化本地 account 实体
        account = new Account(accountId, null);

        // 核心：数据就绪后，立刻驱动 ViewModel 去服务端拉取真正的用户昵称
        if (viewModel != null) {
            viewModel.fetchAccountName(accountId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭弹窗，避免窗口泄漏
        if (profilePopup != null && profilePopup.isShowing()) {
            profilePopup.dismiss();
        }
    }

    /**
     * 加载 Fragment 并更新标题
     */
    private void loadFragment(Fragment fragment, String title) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        tvToolbarTitle.setText(title);
    }
}