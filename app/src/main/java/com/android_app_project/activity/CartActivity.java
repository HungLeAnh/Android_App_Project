package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android_app_project.Helper.IClickItemListener;
import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.adapter.CheckOutBottomSheetListViewAdapter;
import com.android_app_project.adapter.ItemCartItemAdapter;
import com.android_app_project.adapter.ItemProductAdapter;
import com.android_app_project.api.CartItemAPI;
import com.android_app_project.api.OrderAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityCartBinding;
import com.android_app_project.entities.CartItem;
import com.android_app_project.entities.Order;
import com.android_app_project.entities.Product;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = CartActivity.class.getName();
    private ActivityCartBinding binding;
    Context context;
    List<CartItem> cartItemList;
    CartItemAPI cartItemAPI;
    OrderAPI orderAPI;
    ItemCartItemAdapter itemCartItemAdapter;
    String header;
    private Long totalPrice= Long.valueOf(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = getApplicationContext();

        header = "Bearer "+ SharePrefManager.getInstance(getApplicationContext()).getJWT();
        cartItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CARTITEM).create(CartItemAPI.class);
        cartItemAPI.getListCartItem(header).enqueue(new Callback<List<CartItem>>() {
            @Override
            public void onResponse(Call<List<CartItem>> call, Response<List<CartItem>> response) {
                try{
                    if(response.isSuccessful()){
                        cartItemList = new ArrayList<>(response.body());

                        binding.cartactivityTvItemCount.setText(String.valueOf(cartItemList.size())+" MẶT HÀNG");
                        CalculateTotalPrice();

                        itemCartItemAdapter = new ItemCartItemAdapter(cartItemList, new IClickItemListener() {
                            @Override
                            public void onClickItem() {

                            }
                        }, context);
                        binding.cartactivityRvCartItem.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        binding.cartactivityRvCartItem.setLayoutManager(layoutManager);
                        binding.cartactivityRvCartItem.setAdapter(itemCartItemAdapter);
                        itemCartItemAdapter.notifyDataSetChanged();
                    }
                }
                catch (Exception e){
                    Log.e(TAG,"error: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<List<CartItem>> call, Throwable t) {

            }
        });

        binding.cartactivityIvBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.cartactivityBtnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheet();
            }
        });
    }

    private void showBottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.check_out_bottomsheet);

        TextView totalPriceTV =  bottomSheetDialog.findViewById(R.id.check_out_bottom_tv_total_price);
        totalPriceTV.setText(totalPrice.toString()+" VND");
        ListView listViewitem = bottomSheetDialog.findViewById(R.id.check_out_bottom_lv_items);
        CheckOutBottomSheetListViewAdapter adapter = new CheckOutBottomSheetListViewAdapter(cartItemList);
        listViewitem.setAdapter(adapter);

        EditText addressET = bottomSheetDialog.findViewById(R.id.check_out_bottom_et_address);
        Button checkoutButton = bottomSheetDialog.findViewById(R.id.check_out_bottom_btn_checkout);
        bottomSheetDialog.show();

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = addressET.getText().toString();
                if(address.isEmpty()){
                    Toast.makeText(context,"Vui lòng nhập địa chỉ",Toast.LENGTH_SHORT).show();
                    return;
                }
                List<CartItem> cartItemLisRequest = new ArrayList<>();
                for (CartItem item : cartItemList) {
                    cartItemLisRequest.add(new CartItem(item.getCartItemId()));
                }
                Gson gson = new Gson();
                String cartitemJson = gson.toJson(cartItemLisRequest);

                orderAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ORDER).create(OrderAPI.class);
                orderAPI.createOrder(header,address,"","",cartitemJson).enqueue(new Callback<Order>() {
                    @Override
                    public void onResponse(Call<Order> call, Response<Order> response) {
                        try {
                            if(response.isSuccessful()){
                                Toast.makeText(context,"Đặt hàng thành công",Toast.LENGTH_SHORT).show();
                                bottomSheetDialog.dismiss();
                                recreate();
                            }
                            else {
                                Log.i(TAG, "onResponse error: " + response.errorBody().toString());
                            }
                        }
                        catch (Exception e){
                            Log.e(TAG, "onResponse error: " + e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<Order> call, Throwable t) {
                        Log.e(TAG, "onResponse error: " + t.getMessage());
                    }
                });
            }
        });
        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    private void CalculateTotalPrice() {
        totalPrice = Long.valueOf(0);
        for (CartItem item:cartItemList) {
            Long itemCount = Long.valueOf(item.getCount());
            BigDecimal itemPrice = item.getProduct().getPrice();
            totalPrice = totalPrice + itemCount * itemPrice.longValue();
        }
        binding.cartactivityBottomTotalPrice.setText(totalPrice.toString()+ " VND");
    }
}