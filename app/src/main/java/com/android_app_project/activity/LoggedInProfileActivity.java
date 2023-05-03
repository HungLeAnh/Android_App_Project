package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android_app_project.R;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.databinding.ActivityLoggedInProfileBinding;
import com.android_app_project.databinding.ActivityMainBinding;

public class LoggedInProfileActivity extends AppCompatActivity {

    private ActivityLoggedInProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoggedInProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(!SharePrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
            finish();
        }

        binding.LoggedInProfileBtnProfileSetting.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),ProfileSettingActivity.class);
            startActivity(intent);
        });
    }
}