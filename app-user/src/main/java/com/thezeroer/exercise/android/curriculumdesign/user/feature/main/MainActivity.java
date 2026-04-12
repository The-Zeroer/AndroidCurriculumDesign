package com.thezeroer.exercise.android.curriculumdesign.user.feature.main;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
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
        // 实现跳转到 ProfileActivity
        ivAvatar.setOnClickListener(this::showProfilePopup);
    }
    private void showProfilePopup(View anchorView) {
        // 加载个人信息弹窗布局
        View popupView = LayoutInflater.from(this).inflate(R.layout.popup_profile, null);

        // 初始化弹窗内的控件（可绑定动态数据，这里使用静态演示）
        TextView tvName = popupView.findViewById(R.id.tv_popup_name);
        TextView tvEmail = popupView.findViewById(R.id.tv_popup_email);
        TextView tvAccountId = popupView.findViewById(R.id.tv_popup_account_id);

        // 可根据登录用户信息动态设置
        tvName.setText("张伟");
        tvEmail.setText("zhang.wei@example.com");
        tvAccountId.setText("账号ID: 1002345");

        // 创建 PopupWindow
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        // 必须设置背景，否则 outsideTouchable 无效
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);  // 点击外部关闭
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog); // 淡入淡出效果

        // 计算显示位置：锚点正下方居中
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorCenterX = location[0] + anchorView.getWidth() / 2;
        int popupWidth = popupView.getMeasuredWidth(); // 注意此时尚未测量，需先调用 measure
        // 手动测量弹窗宽度
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        popupWidth = popupView.getMeasuredWidth();
        int x = anchorCenterX - popupWidth / 2;
        int y = location[1] + anchorView.getHeight() + 10; // 向下偏移10dp

        popupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, x, y);
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