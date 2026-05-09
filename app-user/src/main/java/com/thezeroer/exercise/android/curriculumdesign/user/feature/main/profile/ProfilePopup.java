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

import androidx.lifecycle.LifecycleOwner;

import com.thezeroer.exercise.android.curriculumdesign.user.R;
import com.thezeroer.exercise.android.curriculumdesign.user.feature.main.MainViewModel;
import com.thezeroer.exercise.android.curriculumdesign.user.model.AccountInfo;

public class ProfilePopup {

    private final Context context;
    private final MainViewModel mainViewModel;
    private final LifecycleOwner lifecycleOwner;
    private PopupWindow popupWindow;
    private View popupView;

    private ImageView ivAvatar;
    private TextView tvName;
    private TextView tvEmail;
    private TextView tvAccountId;

    public ProfilePopup(Context context, MainViewModel mainViewModel, LifecycleOwner lifecycleOwner) {
        this.context = context;
        this.mainViewModel = mainViewModel;
        this.lifecycleOwner = lifecycleOwner;
        initPopup();
        observeAccountInfo();
    }

    private void initPopup() {
        popupView = LayoutInflater.from(context).inflate(R.layout.popup_profile, null);

        ivAvatar = popupView.findViewById(R.id.iv_popup_avatar);
        tvName = popupView.findViewById(R.id.tv_popup_name);
        tvEmail = popupView.findViewById(R.id.tv_popup_email);
        tvAccountId = popupView.findViewById(R.id.tv_popup_account_id);

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

    private void observeAccountInfo() {
        mainViewModel.getAccountInfo().observe(lifecycleOwner, this::updateUI);
    }

    private void updateUI(AccountInfo accountInfo) {
        if (accountInfo == null) return;

        tvName.setText(accountInfo.getName());
        tvEmail.setText(accountInfo.getEmail());
        tvAccountId.setText("账号ID: " + accountInfo.getAccountId());

        // 根据实际情况设置头像，如果 AccountInfo 中提供的是资源 ID
        if (accountInfo.getAvatarResId() != 0) {
            ivAvatar.setImageResource(accountInfo.getAvatarResId());
        } else {
            // 可设置默认头像
            ivAvatar.setImageResource(R.drawable.ic_contacts);
        }
    }

    public void showBelow(View anchorView) {
        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupWidth = popupView.getMeasuredWidth();

        int[] location = new int[2];
        anchorView.getLocationOnScreen(location);
        int anchorCenterX = location[0] + anchorView.getWidth() / 2;
        int x = anchorCenterX - popupWidth / 2;
        int y = location[1] + anchorView.getHeight() + 10;

        popupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, x, y);
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public boolean isShowing() {
        return popupWindow != null && popupWindow.isShowing();
    }
}