package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thezeroer.exercise.android.curriculumdesign.core.base.view.BaseFragment;
import com.thezeroer.exercise.android.curriculumdesign.core.base.viewmodel.NoViewModel;  // 改成 NoViewModel
import com.thezeroer.exercise.android.curriculumdesign.user.feature.settings.SettingsActivity;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.about.AboutActivity;

// 关键修改：BaseFragment<BaseViewModel> 改成 BaseFragment<NoViewModel>
public class MenuFragment extends BaseFragment<NoViewModel> {

    private TextView avatar;
    private TextView name;
    private TextView account;
    private TextView itemSetting;
    private TextView itemAbout;
    private LinearLayout root;

    public MenuFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return android.R.layout.activity_list_item;
    }

    @Override
    protected void onInitView(View view) {
        // 清空自带内容
        ((ViewGroup) view).removeAllViews();

        // 创建你的界面
        root = new LinearLayout(getContext());
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

        avatar = new TextView(getContext());
        avatar.setLayoutParams(new LinearLayout.LayoutParams(dp2px(80), dp2px(80)));
        avatar.setBackgroundColor(0xFFDDDDDD);
        avatar.setGravity(Gravity.CENTER);
        avatar.setText("头像");
        avatar.setTextSize(14);
        avatar.setTextColor(0xFF666666);

        LinearLayout textGroup = new LinearLayout(getContext());
        textGroup.setOrientation(LinearLayout.VERTICAL);
        textGroup.setPadding(dp2px(15), 0, 0, 0);

        name = new TextView(getContext());
        name.setText("张三");
        name.setTextSize(20);
        name.setTextColor(0xFF000000);

        account = new TextView(getContext());
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

        // 初始化设置和关于菜单项
        itemSetting = createItem("设置");
        itemAbout = createItem("关于");

        // 组装布局
        root.addView(userLayout);
        root.addView(line);
        root.addView(itemSetting);
        root.addView(itemAbout);

        // 把你的布局加到Fragment的根View里
        ((ViewGroup) view).addView(root);
    }

    @Override
    protected void onInitHandler() {
        // 设置按钮点击事件
        itemSetting.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        // 关于按钮点击事件
        itemAbout.setOnClickListener(v -> {
            if (getActivity() != null) {
                Intent intent = new Intent(getActivity(), AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    private TextView createItem(String text) {
        TextView item = new TextView(getContext());
        item.setText(text);
        item.setTextSize(16);
        item.setPadding(dp2px(15), dp2px(18), dp2px(15), dp2px(18));
        item.setBackgroundColor(0xFFFFFFFF);
        item.setTextColor(0xFF333333);
        item.setGravity(Gravity.CENTER_VERTICAL);
        return item;
    }

    private int dp2px(int dp) {
        if (getContext() == null || getResources() == null) {
            return dp;
        }
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }
}