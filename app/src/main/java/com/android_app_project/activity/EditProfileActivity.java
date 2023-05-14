package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android_app_project.R;
import com.android_app_project.adapter.ViewPagerAdapter;
import com.android_app_project.databinding.ActivityEditProfileBinding;
import com.android_app_project.entities.Customer;

public class EditProfileActivity extends AppCompatActivity {

    private ActivityEditProfileBinding binding;
    Customer customer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        customer = (Customer) intent.getSerializableExtra("customer");
        if (customer!=null){
            binding.editProfileEtEmail.setText(customer.getEmail());
            binding.editProfileEtFirstName.setText(customer.getFirstName());
            binding.editProfileEtLastName.setText(customer.getLastName());
            binding.editProfileEtPhone.setText(customer.getPhone());
            binding.editProfileEtEWallet.setText(customer.geteWallet());
        }
        binding.editProfileBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}