package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.adapter.ItemProductAdapter;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivitySearchBinding;
import com.android_app_project.entities.Category;
import com.android_app_project.entities.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getName();
    private ActivitySearchBinding binding;
    Category category;
    List<Product> productList;
    ProductAPI productAPI;
    Context context;
    ItemProductAdapter itemProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = getApplicationContext();

        Intent intent = getIntent();
        category = (Category) intent.getSerializableExtra("category");

        binding.searchactivityTvCategorytitle.setText(category.getCategoryName());

        binding.searchactivityIvBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
        productAPI.searchbycategory(category.getCategoryId()).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                try{
                    if(response.isSuccessful()){
                        productList = new ArrayList<>(response.body());
                        itemProductAdapter = new ItemProductAdapter(productList,context);
                        binding.searchactivityRvProductrv.setHasFixedSize(true);
                        GridLayoutManager layoutManager = new GridLayoutManager((context),2,GridLayoutManager.VERTICAL,false);
                        binding.searchactivityRvProductrv.setLayoutManager(layoutManager);
                        binding.searchactivityRvProductrv.setAdapter(itemProductAdapter);
                        itemProductAdapter.notifyDataSetChanged();
                    }
                    else {
                        Log.i(TAG, "onResponse: "+response.errorBody().string());
                    }
                }
                catch (Exception e){
                    Log.i(TAG, "onResponse: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.i(TAG, "onResponse: "+t.getMessage());
            }
        });
    }
}