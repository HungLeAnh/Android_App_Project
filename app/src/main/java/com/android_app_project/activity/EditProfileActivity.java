package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android_app_project.R;
import com.android_app_project.adapter.ViewPagerAdapter;
import com.android_app_project.databinding.ActivityEditProfileBinding;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
}