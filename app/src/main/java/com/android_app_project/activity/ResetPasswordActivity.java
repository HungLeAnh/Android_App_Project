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
import com.android_app_project.databinding.ActivityResetPasswordBinding;
import com.android_app_project.entities.Account;
import com.android_app_project.model.ResetPasswordRequest;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity extends AppCompatActivity {

    private ActivityResetPasswordBinding binding;
    LoginAPIService loginAPIService;
    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        if(intent!= null){
            account = (Account) intent.getSerializableExtra("account");
        }
        binding.idResetpasswordResetButton.setOnClickListener(v -> {
            String code = binding.idResetpasswordOTPEditText.getText().toString().trim();
            String password = binding.idResetpasswordNewPasswordEditText.getText().toString().trim();
            String repeatPassword = binding.idResetpasswordRepeatPasswordEditText.getText().toString().trim();
            ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(code,password,repeatPassword);

            Gson gson = new Gson();
            String jsonAccount = gson.toJson(account);
            String jsonResetPasswordRequest = gson.toJson(resetPasswordRequest);

            loginAPIService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
            loginAPIService.resetPassword(jsonResetPasswordRequest,jsonAccount).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("logg", "raw: " + response.raw());
                    try {
                        if(response.isSuccessful()){
                            assert response.body() != null;
                            String message = response.body().string();
                            Toast.makeText(getApplicationContext(),message, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            if(message.contains("success"))
                                intent.putExtra("success", true);
                            else
                                intent.putExtra("success", false);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Gọi API thất bại: "+response.message() , Toast.LENGTH_SHORT).show();
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
        });
    }
}