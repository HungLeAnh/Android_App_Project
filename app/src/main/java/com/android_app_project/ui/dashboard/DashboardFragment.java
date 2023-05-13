package com.android_app_project.ui.dashboard;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android_app_project.Utils.Constants;
import com.android_app_project.adapter.ViewPagerAdapter;
import com.android_app_project.api.CategoryAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.FragmentDashboardBinding;
import com.android_app_project.entities.Category;
import com.android_app_project.entities.Product;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardFragment extends Fragment {

    private static final String TAG = DashboardFragment.class.getName();
    private FragmentDashboardBinding binding;
    private ViewPagerAdapter viewPagerAdapter;
    Context context;
    Fragment fragment;
    ProductAPI productAPI;
    CategoryAPI categoryAPI;

    List<Category> parentCategoryList = new ArrayList<>();
    List<Category> childCategoryList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fragment = this;
        context = getContext();

        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CATEGORY).create(CategoryAPI.class);
        categoryAPI.getParent().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                try {
                    Log.d("TAG", "onResponse: "+response.raw());
                    if(response.isSuccessful()){
                        parentCategoryList = new ArrayList<>(response.body());
                        viewPagerAdapter = new ViewPagerAdapter(fragment);
                        for (Category cate : parentCategoryList) {
                            categoryAPI.getSubCategory(cate.getCategoryId()).enqueue(new Callback<List<Category>>() {
                                @Override
                                public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                                    try {
                                        if(response.isSuccessful()){
                                            childCategoryList = new ArrayList<>(response.body());
                                            viewPagerAdapter.addFragment( CategoryFragment.newInstance(childCategoryList),cate.getCategoryName());

                                            binding.DashboardFragmentViewPager.setAdapter(viewPagerAdapter);

                                            new TabLayoutMediator(binding.DashboardFragmentTabLayout, binding.DashboardFragmentViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                                                @Override
                                                public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                                                    tab.setText(viewPagerAdapter.getTitle(position));
                                                }
                                            }).attach();
                                        }
                                        else {
                                            Toast.makeText(context,"Child category: "+response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    catch (Exception e){
                                        Toast.makeText(context,"child category: "+e.getMessage(),Toast.LENGTH_SHORT).show();

                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Category>> call, Throwable t) {

                                }
                            });
                        }
                    }
                    else {
                        Toast.makeText(context,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {

            }
        });
        binding.DashboardFragmentSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
                productAPI.search(query).enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        try {
                            Log.i(TAG,"onResponse: "+response.raw());
                            if(response.isSuccessful()){
                                List<Product> products = new ArrayList<>(response.body());
                            }
                        }
                        catch (Exception e ){
                            Log.e(TAG,"error: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}