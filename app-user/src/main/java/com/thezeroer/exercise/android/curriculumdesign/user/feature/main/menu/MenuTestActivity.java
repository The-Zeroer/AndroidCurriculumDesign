package com.thezeroer.exercise.android.curriculumdesign.user.feature.main.menu;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public class MenuTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MenuFragment fragment = new MenuFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .add(android.R.id.content, fragment) // android.R.id.content 是整个屏幕
                .commitNow();
    }
}
