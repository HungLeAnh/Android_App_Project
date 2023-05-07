package com.android_app_project.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.api.LoginAPIService;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityForgetPasswordBinding;
import com.android_app_project.model.ForgetPasswordResponse;
import com.android_app_project.model.SignupResponse;

import java.io.IOException;
import java.io.Serializable;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;
    LoginAPIService loginAPIService;
    public static final String TAG = ForgetPasswordActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.idForgotpasswordSendButton.setOnClickListener(v -> {
            String email = binding.idForgotpasswordEmailEditText.getText().toString().trim();
            loginAPIService = RetrofitClient.getInstance().getRetrofit(Constants.URL_REGISTRATION).create(LoginAPIService.class);
            loginAPIService.forgotPassword(email).enqueue(new Callback<ForgetPasswordResponse>() {
                @Override
                public void onResponse(Call<ForgetPasswordResponse> call, Response<ForgetPasswordResponse> response) {
                    try {
                        if(response.isSuccessful()){
                            Log.i("logg", "raw: " + response.raw());
                            assert response.body() != null;
                            ForgetPasswordResponse myresp = response.body();
                            Toast.makeText(getApplicationContext(),myresp.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                            intent.putExtra("account",(Serializable) myresp.getAccount());
                            mforgetpasswordverifyResultLauncher.launch(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Gọi API thất bại: "+response.message() , Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ForgetPasswordResponse> call, Throwable t) {
                    Log.d("logg",t.getMessage());
                }
            });
        });
    }
    private ActivityResultLauncher<Intent> mforgetpasswordverifyResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        new ActivityResultCallback<ActivityResult>(){

            @Override
            public void onActivityResult(ActivityResult result) {
                Log.d(TAG,"onActivityResult");
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data  = result.getData();
                    if(data == null){
                        return;
                    }
                    boolean success = data.getBooleanExtra("success",false);
                    if(success)
                        finish();
                    else
                        Toast.makeText(getApplicationContext(),"Đặt lại mật khẩu thất bại",Toast.LENGTH_SHORT).show();
                }
            }
        }
    );
}