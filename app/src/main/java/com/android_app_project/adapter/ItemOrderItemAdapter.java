package com.android_app_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.activity.OrderDetailActivity;
import com.android_app_project.api.OrderItemAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemOrderBinding;
import com.android_app_project.databinding.ItemOrderItemBinding;
import com.android_app_project.entities.Order;
import com.android_app_project.entities.OrderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemOrderItemAdapter extends RecyclerView.Adapter<ItemOrderItemAdapter.ItemOrderItemHolder> {
    List<OrderItem> orderItemList;
    Context context;
    ProductAPI productAPI;

    public ItemOrderItemAdapter(List<OrderItem> orderItemList, Context context) {
        this.orderItemList = orderItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemOrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderItemBinding binding = ItemOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemOrderItemHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderItemHolder holder, int position) {
        OrderItem orderItem = orderItemList.get(position);

        holder.binding.itemOrderTvProductAmount.setText("x"+orderItem.getCount());
        holder.binding.itemOrderItemTvProductName.setText(orderItem.getProduct().getProductName());
        holder.binding.itemOrderItemTvProductSize.setText("Kích cỡ: "+orderItem.getSize().getValue());
        holder.binding.itemOrderItemColorIvColor.setImageDrawable(new ColorDrawable(Color.parseColor(orderItem.getColor().getValue())));
        holder.binding.itemOrderTvProductPrice.setText(orderItem.getProduct().getPrice()+" VND");
        if(orderItem.getProduct().getImage()!=null){
            productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
            productAPI.serverFile(orderItem.getProduct().getImage()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        if(response.isSuccessful()){
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            holder.binding.itemOrderItemIvProductImage.setImageBitmap(bmp);
                        }
                        else{
                            Log.d("item product adapter",response.errorBody().string());
                            holder.binding.itemOrderItemIvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
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
            holder.binding.itemOrderItemIvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
        }

    }

    @Override
    public int getItemCount() {
        return orderItemList==null?0:orderItemList.size();
    }

    public class ItemOrderItemHolder extends RecyclerView.ViewHolder {
        private ItemOrderItemBinding binding;
        public ItemOrderItemHolder(ItemOrderItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}
