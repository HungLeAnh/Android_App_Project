package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.adapter.ItemOrderAdapter;
import com.android_app_project.api.OrderAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityOrderBinding;
import com.android_app_project.entities.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private static final String TAG = OrderActivity.class.getName();
    private ActivityOrderBinding binding;
    OrderAPI orderAPI;
    List<Order> orderList;
    Context context;

    ItemOrderAdapter itemOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = getApplicationContext();

        String request = "Bearer "+ SharePrefManager.getInstance(getApplicationContext()).getJWT();

        orderAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ORDER).create(OrderAPI.class);
        orderAPI.getByCustomerId(request).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                try{
                    Log.i("logg", "raw: " + response.raw());
                    if(response.isSuccessful()){
                        Log.i("loggi", "onResponse: " );
                        orderList = new ArrayList<>(response.body());
                        Log.i(TAG, "onResponse: "+orderList.get(0).getTotalPrice());
                        itemOrderAdapter = new ItemOrderAdapter(orderList,context);
                        binding.orderactivityRvOrderItem.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        binding.orderactivityRvOrderItem.setLayoutManager(layoutManager);
                        binding.orderactivityRvOrderItem.setAdapter(itemOrderAdapter);
                        itemOrderAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),response.message() , Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

        binding.orderactivityIvBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}