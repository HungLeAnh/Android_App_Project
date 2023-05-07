package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.api.LoginAPIService;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivitySignupVerifyEmailBinding;
import com.android_app_project.entities.Account;
import com.android_app_project.entities.Customer;
import com.android_app_project.model.SignupResponse;
import com.android_app_project.model.VerifyEmailResponse;
import com.google.gson.Gson;

import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupVerifyEmailActivity extends AppCompatActivity {

    private ActivitySignupVerifyEmailBinding binding;
    Account account;
    Customer customer;
    LoginAPIService loginAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        if(intent!=null){
            account = (Account) intent.getSerializableExtra("account");
            customer = (Customer) intent.getSerializableExtra("customer");
            Log.i("logg i", "onCreate: get bundle: "+account.getUsername()+"/n"+customer.getEmail());
        }

        binding.idSignupVerifyButton.setOnClickListener(v -> {
            String otp = binding.idSignupOTPEditText.getText().toString().trim();
            verifyEmailOTP(otp);

        });
        binding.idSignupResendOTPTextView.setOnClickListener(v -> {
            Gson gson = new Gson();
            String jsonAccount = gson.toJson(account);
            loginAPIService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
            loginAPIService.resendOTP(customer.getEmail(),jsonAccount).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        Log.i("logg", "raw: " + response.raw());
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String message = response.body().string();
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });
    }

    private void verifyEmailOTP(String otp) {
        Log.d("loggs", "userLogin: " + Constants.URL_REGISTRATION);
        loginAPIService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
        Gson gson = new Gson();
        String jsonAccount = gson.toJson(account);
        String jsonCustomer = gson.toJson(customer);
        loginAPIService.verify(otp, jsonAccount, jsonCustomer).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        Log.i("logg", "raw: " + response.raw());
                        assert response.body() != null ;
                        VerifyEmailResponse verifyEmailResponse = new VerifyEmailResponse(response.body().string());
                        Toast.makeText(getApplicationContext(),verifyEmailResponse.getMessage(),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        if(verifyEmailResponse.getMessage().contains("success"))
                            intent.putExtra("success", true);
                        else
                            intent.putExtra("success", false);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                    else {
                        Log.i("logg", "onResponse: "+response.errorBody().string());
                        Toast.makeText(getApplicationContext(),"response fail: "+response.message() , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("logg",t.getMessage());

            }
        });
    }
}