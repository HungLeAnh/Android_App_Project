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
import com.android_app_project.model.SignupResponse;
import com.android_app_project.model.VerifyEmailResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupVerifyEmailActivity extends AppCompatActivity {

    private ActivitySignupVerifyEmailBinding binding;

    LoginAPIService loginAPIService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupVerifyEmailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idVerifyButton.setOnClickListener(v -> {
            String otp = binding.idOTPEditText.getText().toString().trim();
            verifyOTP(otp);
        });
    }

    private void verifyOTP(String otp) {
        Log.d("loggs", "userLogin: " + Constants.URL_REGISTRATION);
        loginAPIService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
        loginAPIService.verify(otp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        Log.i("logg", "raw: " + response.raw());
                        assert response.body() != null;
                        VerifyEmailResponse verifyEmailResponse = new VerifyEmailResponse(response.body().string());
                        Toast.makeText(getApplicationContext(),verifyEmailResponse.getMessage(),Toast.LENGTH_SHORT).show();
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