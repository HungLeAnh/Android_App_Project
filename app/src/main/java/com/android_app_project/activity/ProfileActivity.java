package com.android_app_project.activity;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.databinding.ActivityProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ProfileActivity extends AppCompatActivity {

    public static boolean isLoggedIn=false;
    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(isLoggedIn){
            Intent intent = new Intent(getApplicationContext(),LoggedInProfileActivity.class);
            startActivity(intent);
            finish();
        }

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        isLoggedIn = SharePrefManager.getInstance(this).isLoggedIn();
        if(isLoggedIn){
            Intent intent = new Intent(getApplicationContext(),LoggedInProfileActivity.class);
            startActivity(intent);
            finish();
        }
        binding.ivProfileActivityExitIcon.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.onclick));
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },300);

        });

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.profile_bg_video);
        binding.viProfileActivityBackgroundVideo.setVideoURI(uri);
        binding.viProfileActivityBackgroundVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                float videowidth = mp.getVideoWidth();
                float videoheight =  mp.getVideoHeight();
                float screenwidth = binding.profileActivityVideoContainer.getWidth();
                float screenheight = binding.profileActivityVideoContainer.getHeight();
                float scalex = videowidth /screenwidth;
                float scaley = videoheight/screenheight;
                if(scalex>=1f){
                    binding.viProfileActivityBackgroundVideo.setScaleX(scalex);
                }
                else{
                    binding.viProfileActivityBackgroundVideo.setScaleX(1f/scaley);
                }
                if(scaley>=1f){
                    binding.viProfileActivityBackgroundVideo.setScaleY(scaley);
                }
                else{
                    binding.viProfileActivityBackgroundVideo.setScaleY(2f/scaley);
                }

                binding.viProfileActivityBackgroundVideo.setTranslationY(binding.profileActivityVideoContainer.getHeight()/2 -56);
                mp.setLooping(true);
            }
        });
        binding.viProfileActivityBackgroundVideo.start();

        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(binding.profileActivitySheet);
        bottomSheetBehavior.setPeekHeight(560);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        binding.profileactivitySignupPolicy.setVisibility(View.VISIBLE);
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        binding.profileactivitySignupPolicy.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        if(bottomSheet.getHeight()<binding.profileactivitySignupPolicy.getHeight())
                            binding.profileactivitySignupPolicy.setVisibility(View.INVISIBLE);
                        else
                            binding.profileactivitySignupPolicy.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        binding.profileActivityBtnnSignin.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.onclick));
            v.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            },300);

            if(isLoggedIn){
                finish();
            }
        });
        binding.profileActivityBtnnSignup.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.onclick));
            v.postDelayed(() -> {
                Intent intent = new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(intent);
            },300);

        });
        binding.profileActivitySetting.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.onclick));
            v.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"open setting",Toast.LENGTH_SHORT).show();
                }
            },300);
        });
    }

    @Override
    protected void onResume() {
        binding.viProfileActivityBackgroundVideo.resume();
        super.onResume();
    }

    @Override
    protected void onPostResume() {
        binding.viProfileActivityBackgroundVideo.resume();
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        binding.viProfileActivityBackgroundVideo.pause();
        super.onStop();
    }

    @Override
    protected void onPause() {
        binding.viProfileActivityBackgroundVideo.pause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        binding.viProfileActivityBackgroundVideo.stopPlayback();
        super.onDestroy();
    }
}