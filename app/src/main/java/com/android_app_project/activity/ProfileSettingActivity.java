package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android_app_project.R;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.databinding.ActivityProfileSettingBinding;

public class ProfileSettingActivity extends AppCompatActivity {

    private ActivityProfileSettingBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ProfilesettingBtnLogout.setOnClickListener(v->{
            SharePrefManager.getInstance(this).logout();
        });
    }
}