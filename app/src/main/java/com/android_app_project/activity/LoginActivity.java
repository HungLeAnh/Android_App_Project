package com.android_app_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.api.LoginAPIService;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityLoginBinding;
import com.android_app_project.model.UserLoginRequest;
import com.android_app_project.model.UserLoginResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    LoginAPIService loginApiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.idSignInButton.setOnClickListener(v -> userLogin());
        binding.idForgetPasswordTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),ForgetPasswordActivity.class);
            startActivity(intent);
        });
    }

    private void userLogin() {
        final String username = binding.idEmailEditText.getText().toString().trim();
        final String password = binding.idPasswordEditText.getText().toString().trim();
        if(TextUtils.isEmpty(username)){
            binding.idEmailEditText.setError("Please enter username");
            binding.idEmailEditText.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password)){
            binding.idPasswordEditText.setError("Please enter password");
            binding.idPasswordEditText.requestFocus();
            return;
        }
        Log.d("loggs", "userLogin: " + Constants.URL_REGISTRATION);
        UserLoginRequest userLoginRequest = new UserLoginRequest(username,password);
        loginApiService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
        loginApiService.login(userLoginRequest).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        Log.i("logg", "res: " + response.raw());
                        assert response != null;
                        UserLoginResponse user = new UserLoginResponse(response.body().string());
                        Log.i("loggi","jwt: "+user.getJWTtoken());
                        SharePrefManager.getInstance(getApplicationContext()).userLogin(user);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
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
                Log.d("logg",t.getMessage());
            }
        });
    }

}