package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.activity.admin.AdminMainActivity;
import com.android_app_project.api.CustomerAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityLoggedInProfileBinding;
import com.android_app_project.databinding.ActivityMainBinding;
import com.android_app_project.entities.Customer;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoggedInProfileActivity extends AppCompatActivity {

    private ActivityLoggedInProfileBinding binding;
    CustomerAPI customerAPI;
    Customer customer;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoggedInProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        context = getApplicationContext();
        if(!SharePrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(intent);
            finish();
        }
        customerAPI = RetrofitClient.getInstance().getRetrofit(Constants.ROOT_URL).create(CustomerAPI.class);
        Log.i("loggi","jwt: "+SharePrefManager.getInstance(getApplicationContext()).getJWT());
        String request = "Bearer "+SharePrefManager.getInstance(getApplicationContext()).getJWT();
        customerAPI.profile(request).enqueue(new Callback<Customer>() {
            @Override
            public void onResponse(Call<Customer> call, Response<Customer> response) {
                try {
                    Log.i("logg", "raw: " + response.raw());
                    if(response.isSuccessful()){
                        assert response.body() != null;
                        customer = response.body();
                        if(customer!=null)  {
                            Log.i("loggi", "onResponse: " + customer.getFirstName());
                            binding.LoggedInProfileProfileName.setText(customer.getFirstName()+" "+customer.getLastName());
                        }else {
                            Log.i("loggi", "onResponse: customer null" );
                            binding.LoggedInProfileProfileName.setText("error");
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Customer> call, Throwable t) {

            }
        });

        binding.LoggedInProfileBtnProfileSetting.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(),ProfileSettingActivity.class);
            intent.putExtra("customer",(Serializable) customer);
            startActivity(intent);
        });
        binding.LoggedInProfileBtnBuyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,OrderActivity.class);
                startActivity(intent);
            }
        });
        binding.LoggedInProfileBtnManagerApp.setOnClickListener(v -> {
            String role = SharePrefManager.getInstance(context).getRoll();
            if(role!=null && role.equals("ROLE_ADMIN")){
                Intent intent = new Intent(context, AdminMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}