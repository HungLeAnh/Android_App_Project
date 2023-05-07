package com.android_app_project.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android_app_project.R;
import com.android_app_project.activity.ProfileActivity;
import com.android_app_project.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();
        binding.ivCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.onclick));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"cart clicked",Toast.LENGTH_SHORT).show();
                    }
                },300);
            }
        });
        binding.ivProfileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context,R.anim.onclick));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(context, ProfileActivity.class);
                        startActivity(intent);
                    }
                },300);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}