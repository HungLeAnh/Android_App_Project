package com.android_app_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.activity.OrderDetailActivity;
import com.android_app_project.api.OrderAPI;
import com.android_app_project.api.OrderItemAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemOrderBinding;
import com.android_app_project.entities.Order;
import com.android_app_project.entities.OrderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ItemOrderHolder> {

    List<Order> orderList;
    OrderItemAPI orderItemAPI;
    ProductAPI productAPI;
    Context context;

    public ItemOrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemOrderAdapter.ItemOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderBinding binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemOrderHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderAdapter.ItemOrderHolder holder, int position) {
        Order order = orderList.get(position);

        holder.binding.itemOrderTvTotalPrice.setText(order.getTotalPrice().toString()+" VND");

        orderItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ORDER_ITEM).create(OrderItemAPI.class);
        orderItemAPI.getbyOrder(order.getOrderId()).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                try {
                    Log.i("logg", "raw: " + response.raw());
                    if(response.isSuccessful()){
                        List<OrderItem> orderItemList = new ArrayList<>(response.body());
                        Log.i("logg", "raw: " + response.raw());
                        holder.setOrderItemList(orderItemList);
                        holder.binding.itemOrderTvProductAmount.setText("x"+orderItemList.get(0).getCount());
                        holder.binding.itemOrderTvProductName.setText(orderItemList.get(0).getProduct().getProductName());
                        holder.binding.itemOrderTvProductSize.setText("Kích cỡ: "+orderItemList.get(0).getSize().getValue());
                        holder.binding.itemOrderColorIvColor.setImageDrawable(new ColorDrawable(Color.parseColor(orderItemList.get(0).getColor().getValue())));
                        holder.binding.itemOrderTvProductPrice.setText(orderItemList.get(0).getProduct().getPrice()+" VND");
                        if(orderItemList.get(0).getProduct().getImage()!=null){
                            productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
                            productAPI.serverFile(orderItemList.get(0).getProduct().getImage()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try{
                                        if(response.isSuccessful()){
                                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                            holder.binding.itemOrderTvProductImage.setImageBitmap(bmp);
                                        }
                                        else{
                                            Log.d("item product adapter",response.errorBody().string());
                                            holder.binding.itemOrderTvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
                                        }
                                    }
                                    catch (Exception e){
                                        Log.d("item product adapter",e.getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                        else {
                            holder.binding.itemOrderTvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
                        }

                        int itemcount = 0;
                        for (int i = 0; i < orderItemList.size(); i++) {
                            itemcount+=orderItemList.get(i).getCount();
                        }
                        holder.binding.itemOrderTvItemCount.setText(itemcount+" sản phẩm");

                    }
                    else {
                        Log.i("logg", "not success: " + response.errorBody());
                    }
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {

            }
        });

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderitems", (Serializable) holder.getOrderItemList());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.binding.itemOrderBtnStatusChange.setText(order.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList==null?0:orderList.size();
    }

    public class ItemOrderHolder extends RecyclerView.ViewHolder {
        private ItemOrderBinding binding;
        private List<OrderItem> OrderItemList;
        public ItemOrderHolder(ItemOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public List<OrderItem> getOrderItemList() {
            return OrderItemList;
        }

        public void setOrderItemList(List<OrderItem> orderItemList) {
            OrderItemList = orderItemList;
        }
    }
}
