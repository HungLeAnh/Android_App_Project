package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.android_app_project.R;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.databinding.ActivityProfileSettingBinding;
import com.android_app_project.entities.Customer;

import java.io.Serializable;

public class ProfileSettingActivity extends AppCompatActivity {

    private ActivityProfileSettingBinding binding;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileSettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");

        binding.ProfilesettingBtnLogout.setOnClickListener(v->{
            SharePrefManager.getInstance(this).logout();
            finish();
        });

        binding.ProfilesettingBtnEditProfile.setOnClickListener(v -> {
            Intent newintent = new Intent(getApplicationContext(),EditProfileActivity.class);
            newintent.putExtra("customer",(Serializable) customer);
            startActivity(newintent);
        });
    }
}