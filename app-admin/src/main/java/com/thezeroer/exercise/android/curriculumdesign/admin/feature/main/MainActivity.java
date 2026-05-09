package com.thezeroer.exercise.android.curriculumdesign.admin.feature.main;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.audits.AuditsFragment;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.dashboard.DashboardFragment;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.management.ManagementFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseActivity;
import com.thezeroer.exercise.android.curriculumdesign.admin.R;
import com.thezeroer.exercise.android.curriculumdesign.admin.feature.main.menu.MenuFragment;
// 管理员主页面（格式与用户版完全一致）
public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main; // 用你自己的布局
    }

    @Override
    protected void onInitView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // 设置 Toolbar 为 ActionBar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        fragmentManager = getSupportFragmentManager();

        // 默认显示 仪表盘 Fragment
        loadFragment(new DashboardFragment(), "仪表盘");
    }

    @Override
    protected void onInitHandler() {
        // 底部导航栏切换监听（管理员4个菜单）
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                loadFragment(new DashboardFragment(), "仪表盘");
                return true;
            } else if (id == R.id.nav_manage) {
                loadFragment(new ManagementFragment(), "管理");
                return true;
            } else if (id == R.id.nav_audit) {
                loadFragment(new AuditsFragment(), "审计");
                return true;
            } else if (id == R.id.nav_menu) {
                loadFragment(new MenuFragment(), "菜单");
                return true;
            }
            return false;
        });
    }

    /**
     * 加载 Fragment 并更新标题
     */
    private void loadFragment(Fragment fragment, String title) {
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}