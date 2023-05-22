package com.android_app_project.ui.admin.dashboard;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android_app_project.Utils.Constants;
import com.android_app_project.activity.admin.AdminMainActivity;
import com.android_app_project.adapter.ViewPagerAdapter;
import com.android_app_project.api.CategoryAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.api.admin.AdminStatAPI;
import com.android_app_project.databinding.AdminFragmentDashboardBinding;
import com.android_app_project.databinding.AdminFragmentOrderBinding;
import com.android_app_project.databinding.FragmentDashboardBinding;
import com.android_app_project.entities.Category;
import com.android_app_project.entities.Product;
import com.android_app_project.ui.dashboard.CategoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardFragment extends Fragment {

    private static final String TAG = AdminDashboardFragment.class.getName();
    private AdminFragmentDashboardBinding binding;
    Context context;
    AdminStatAPI adminStatAPI;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = AdminFragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adminStatAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ADMIN_STAT).create(AdminStatAPI.class);
        adminStatAPI.calTotalRevenue().enqueue(new Callback<BigDecimal>() {
            @Override
            public void onResponse(Call<BigDecimal> call, Response<BigDecimal> response) {
                try {
                    if(response.isSuccessful()){
                        binding.adminfragmentdashboardTvTotalRevenue.setText(response.body().toString());
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<BigDecimal> call, Throwable t) {

            }
        });
        adminStatAPI.countCustomer().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                try {
                    if(response.isSuccessful()){
                        binding.adminfragmentdashboardTvCustomerCount.setText(response.body().toString());
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
        adminStatAPI.countOrder().enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                try {
                    if(response.isSuccessful()){
                        binding.adminfragmentdashboardTvOrderCount.setText(response.body().toString());
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}