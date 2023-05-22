package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.adapter.ItemOrderAdapter;
import com.android_app_project.adapter.ItemOrderItemAdapter;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityOrderDetailBinding;
import com.android_app_project.entities.Order;
import com.android_app_project.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends AppCompatActivity {
    private ActivityOrderDetailBinding binding;
    private ItemOrderItemAdapter itemOrderItemAdapter;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = getApplicationContext();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle==null){
            finish();
            Toast.makeText(context,"Không thể xem chi tiết",Toast.LENGTH_SHORT).show();
        }

        List<OrderItem> orderItemList = new ArrayList<>((List<OrderItem>)bundle.getSerializable("orderitems"));
        itemOrderItemAdapter = new ItemOrderItemAdapter(orderItemList,context);
        binding.orderdetailactivityRvOrderItem.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        binding.orderdetailactivityRvOrderItem.setLayoutManager(layoutManager);
        binding.orderdetailactivityRvOrderItem.setAdapter(itemOrderItemAdapter);
        itemOrderItemAdapter.notifyDataSetChanged();
        binding.orderdetailactivityIvBackIcon.setOnClickListener(v -> {
            finish();
        });

    }
}