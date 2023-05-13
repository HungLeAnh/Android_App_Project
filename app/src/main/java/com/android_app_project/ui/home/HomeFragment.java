package com.android_app_project.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.activity.CartActivity;
import com.android_app_project.activity.LoginActivity;
import com.android_app_project.activity.ProfileActivity;
import com.android_app_project.adapter.ItemProductAdapter;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.FragmentHomeBinding;
import com.android_app_project.entities.Category;
import com.android_app_project.entities.Product;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Context context;
    ProductAPI productAPI;
    List<Product> productList;
    ItemProductAdapter itemProductAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();
        binding.fragmenthomeIvCartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.onclick));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(SharePrefManager.getInstance(context).isLoggedIn()){
                            Intent intent = new Intent(context, CartActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Intent loginIntent = new Intent(context, LoginActivity.class);
                            mHomeFragmentResultLauncher.launch(loginIntent);
                        }
                    }
                },300);
            }
        });
        binding.fragmenthomeIvProfileIcon.setOnClickListener(new View.OnClickListener() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
        productAPI.get().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                try {
                    Log.d("TAG", "onResponse: "+response.raw());
                    if(response.isSuccessful()){
/*
                        Log.d("TAG", "onResponse: " + response.body().get(0).getProductName());
*/
                        productList = new ArrayList<>(response.body());
                        itemProductAdapter = new ItemProductAdapter(productList,context);
                        binding.fragmenthomeRvProductrv.setHasFixedSize(true);
                        GridLayoutManager layoutManager = new GridLayoutManager((context),2,GridLayoutManager.VERTICAL,false);
                        binding.fragmenthomeRvProductrv.setLayoutManager(layoutManager);
                        binding.fragmenthomeRvProductrv.setAdapter(itemProductAdapter);
                        itemProductAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(context,response.errorBody().toString(),Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });
    }

    private ActivityResultLauncher<Intent> mHomeFragmentResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    Log.d("home","onActivityResult");
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data  = result.getData();
                        if(data == null){
                            return;
                        }

                    }
                }
            }
    );

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}