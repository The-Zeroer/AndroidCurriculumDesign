package com.thezeroer.exercise.android.curriculumdesign.user.feature.main;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thezeroer.exercise.android.curriculumdesign.core.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.contacts.ContactsFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu.MenuFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.messages.MessagesFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.organization.OrganizationFragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.profile.ProfileActivity;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private ImageView ivAvatar;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

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
        // 可选：隐藏默认标题，使用自定义 TextView
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        fragmentManager = getSupportFragmentManager();

        loadFragment(new MessagesFragment(), "消息");
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

        // 点击头像跳转个人资料页（假设 ProfileActivity 已存在）
        ivAvatar.setOnClickListener(v -> {
            // 实现跳转到 ProfileActivity
//             startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });
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