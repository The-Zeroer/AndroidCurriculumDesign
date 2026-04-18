package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.profile;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.thezeroer.exercise.android.curriculumdesign.user.R;

public class ProfilePopup {

    private final Context context;
    private PopupWindow popupWindow;
    private View popupView;

    // 可自定义的用户信息
    private String userName = "张伟";
    private String userEmail = "zhang.wei@example.com";
    private String accountId = "1002345";
    private int avatarResId = R.drawable.ic_contacts;

    public ProfilePopup(Context context) {
        this.context = context;
        initPopup();
    }

    private void initPopup() {
        // 加载布局
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_profile, null);

        // 绑定控件
        ImageView ivAvatar = popupView.findViewById(R.id.iv_popup_avatar);
        TextView tvName = popupView.findViewById(R.id.tv_popup_name);
        TextView tvEmail = popupView.findViewById(R.id.tv_popup_email);
        TextView tvAccountId = popupView.findViewById(R.id.tv_popup_account_id);

        // 设置数据
        ivAvatar.setImageResource(avatarResId);
        tvName.setText(userName);
        tvEmail.setText(userEmail);
        tvAccountId.setText("账号ID: " + accountId);

        // 创建 PopupWindow
        popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
    }

    /**
     * 在指定 View 下方居中显示弹窗
     * @param anchorView 锚点 View（通常是点击的头像）
     */
    public void showBelow(View anchorView) {
        // 先测量弹窗宽度
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();

        // 获取锚点位置
        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorCenterX = location[0] + anchorView.getWidth() / 2;
        int x = anchorCenterX - popupWidth / 2;
        int y = location[1] + anchorView.getHeight() + 10; // 向下偏移10dp

        popupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, x, y);
    }

    /**
     * 关闭弹窗
     */
    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    /**
     * 设置用户信息（可选，用于动态更新）
     */
    public void setUserInfo(String name, String email, String accountId) {
        this.userName = name;
        this.userEmail = email;
        this.accountId = accountId;
        // 如果弹窗已初始化，更新控件
        if (popupView != null) {
            ((TextView) popupView.findViewById(R.id.tv_popup_name)).setText(name);
            ((TextView) popupView.findViewById(R.id.tv_popup_email)).setText(email);
            ((TextView) popupView.findViewById(R.id.tv_popup_account_id)).setText("账号ID: " + accountId);
        }
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
        if (popupView != null) {
            ((ImageView) popupView.findViewById(R.id.iv_popup_avatar)).setImageResource(avatarResId);
        }
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }
}