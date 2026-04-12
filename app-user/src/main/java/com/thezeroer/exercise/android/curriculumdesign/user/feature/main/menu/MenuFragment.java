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

public class MenuFragment extends Fragment {

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 根布局
        LinearLayout root = new LinearLayout(getContext());
        root.setOrientation(LinearLayout.VERTICAL);
        root.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        root.setBackgroundColor(0xFFF5F5F5);
        root.setPadding(dp2px(20), 0, dp2px(20), 0);

        // 用户信息区域
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

        // 文字区域
        LinearLayout textGroup = new LinearLayout(getContext());
        textGroup.setOrientation(LinearLayout.VERTICAL);
        textGroup.setPadding(dp2px(15), 0, 0, 0);

        TextView name = new TextView(getContext());
        name.setText("张三");
        name.setTextSize(20);

        TextView account = new TextView(getContext());
        account.setText("帐号：zhangsan123");
        account.setTextSize(14);
        account.setTextColor(0xFF666666);
        account.setPadding(0, dp2px(5), 0, 0);

        textGroup.addView(name);
        textGroup.addView(account);
        userLayout.addView(avatar);
        userLayout.addView(textGroup);

        // 分割线
        View line = new View(getContext());
        line.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
        line.setBackgroundColor(0xFFE0E0E0);

        // 设置
        // 点击 设置 → 跳转到组长的 SettingActivity
        TextView itemSetting = createItem("设置", v -> {
            Intent intent = new Intent(getActivity(),
                    com.thezeroer.exercise.android.curriculumdesign.user.feature.settings.SettingsActivity.class);
            startActivity(intent);
        });

        // 关于
        TextView itemAbout = createItem("关于", v -> {
            startActivity(new Intent(getActivity(), AboutActivity.class));
        });

        root.addView(userLayout);
        root.addView(line);
        root.addView(itemSetting);
        root.addView(itemAbout);

        return root;
    }

    private TextView createItem(String text, View.OnClickListener click) {
        TextView item = new TextView(getContext());
        item.setText(text);
        item.setTextSize(16);
        item.setPadding(dp2px(15), dp2px(18), dp2px(15), dp2px(18));
        item.setBackgroundColor(0xFFFFFFFF);
        item.setOnClickListener(click);
        return item;
    }

    private int dp2px(int dp) {
        // 防止在测试环境中 getResources 为 null 导致闪退
        if (getResources() == null) return dp;
        float d = getResources().getDisplayMetrics().density;
        return (int) (dp * d + 0.5f);
    }
}