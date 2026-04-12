package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.settings.SettingsActivity;

public class MenuFragment extends Fragment {

    public MenuFragment() {
        // 必须保留空构造
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // 1. 根布局（微信灰色背景）
        LinearLayout root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        root.setBackgroundColor(0xFFF5F5F5);
        root.setPadding(dp2px(20), 0, dp2px(20), 0);

        // 2. 用户信息区域（头像+用户名+账号）
        LinearLayout userLayout = new LinearLayout(getContext());
        userLayout.setOrientation(LinearLayout.HORIZONTAL);
        userLayout.setGravity(Gravity.CENTER_VERTICAL);
        userLayout.setPadding(0, dp2px(40), 0, dp2px(30));

        // 头像
        TextView avatar = new TextView(getContext());
        avatar.setLayoutParams(new LinearLayout.LayoutParams(dp2px(80), dp2px(80)));
        avatar.setBackgroundColor(0xFFDDDDDD);
        avatar.setGravity(Gravity.CENTER);
        avatar.setText("头像");
        avatar.setTextSize(14);
        avatar.setTextColor(0xFF666666);

        // 文字区域
        LinearLayout textGroup = new LinearLayout(getContext());
        textGroup.setOrientation(LinearLayout.VERTICAL);
        textGroup.setPadding(dp2px(15), 0, 0, 0);

        TextView name = new TextView(getContext());
        name.setText("张三");
        name.setTextSize(20);
        name.setTextColor(0xFF000000);

        TextView account = new TextView(getContext());
        account.setText("帐号：zhangsan123");
        account.setTextSize(14);
        account.setTextColor(0xFF666666);
        account.setPadding(0, dp2px(5), 0, 0);

        textGroup.addView(name);
        textGroup.addView(account);
        userLayout.addView(avatar);
        userLayout.addView(textGroup);

        // 3. 分割线
        View line = new View(getContext());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(0xFFE0E0E0);

        // 4. 设置按钮（跳转到组长的SettingsActivity）
        TextView itemSetting = createItem("设置", v -> {
            Intent intent = new Intent(getActivity(), SettingsActivity.class);
            startActivity(intent);
        });

        // 5. 关于按钮（跳转到弹窗样式的AboutActivity）
        TextView itemAbout = createItem("关于", v -> {
            Intent intent = new Intent(getActivity(), AboutActivity.class);
            startActivity(intent);
        });

        // 6. 组装所有控件
        root.addView(userLayout);
        root.addView(line);
        root.addView(itemSetting);
        root.addView(itemAbout);

        return root;
    }

    // 创建菜单项工具方法
    private TextView createItem(String text, View.OnClickListener click) {
        TextView item = new TextView(getContext());
        item.setText(text);
        item.setTextSize(16);
        item.setPadding(dp2px(15), dp2px(18), dp2px(15), dp2px(18));
        item.setBackgroundColor(0xFFFFFFFF);
        item.setTextColor(0xFF333333);
        item.setGravity(Gravity.CENTER_VERTICAL);
        item.setOnClickListener(click);
        return item;
    }

    // dp转px工具方法
    private int dp2px(int dp) {
        if (getResources() == null) return dp;
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}