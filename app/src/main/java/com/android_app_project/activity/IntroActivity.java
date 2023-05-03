package com.android_app_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.android_app_project.R;


public class IntroActivity extends AppCompatActivity {

    private Handler handler = new Handler();
    ImageView iv_logo;

    Animation fadeIn,fadeOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*iv_logo = findViewById(R.id.iv_intro_logo);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.mainfadein);
        fadeOut = AnimationUtils.loadAnimation(this,R.anim.splashfadeout);
        iv_logo.setAnimation(fadeIn);*/


    }
    @Override
    protected void onStart(){
        super.onStart();
        startLoading();
    }
    @Override
    protected void onResume() {
        super.onResume();
        startLoading();

    }
    private void startLoading() {
        new Thread(new Runnable() {
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(IntroActivity.this,MainActivity.class));
                        overridePendingTransition(R.anim.mainfadein,R.anim.splashfadeout);
                        finish();
                    }
                },2000);
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}