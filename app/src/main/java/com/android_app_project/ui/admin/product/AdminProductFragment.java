package com.android_app_project.ui.admin.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.activity.CartActivity;
import com.android_app_project.activity.LoginActivity;
import com.android_app_project.activity.ProfileActivity;
import com.android_app_project.adapter.ItemProductAdapter;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.api.admin.AdminProductAPI;
import com.android_app_project.databinding.AdminFragmentProductBinding;
import com.android_app_project.databinding.FragmentHomeBinding;
import com.android_app_project.entities.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductFragment extends Fragment {

    private AdminFragmentProductBinding binding;
    Context context;
    AdminProductAPI adminProductAPI;
    ItemProductAdapter itemProductAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = AdminFragmentProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}