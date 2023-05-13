package com.android_app_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.activity.ProductDetailActivity;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemProductBinding;
import com.android_app_project.entities.Product;
import com.google.gson.annotations.SerializedName;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.ItemProductHolder> {

    List<Product> array;

    Context context;

    ProductAPI productAPI;

    public ItemProductAdapter(List<Product> array, Context context) {
        this.array = array;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemProductAdapter.ItemProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding itemProductBinding = ItemProductBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemProductHolder(itemProductBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductAdapter.ItemProductHolder holder, int position) {
        Product product = array.get(position);
        holder.binding.itemProductTvProductName.setText(product.getProductName());
        holder.binding.itemProductTvProductBrand.setText(product.getBrand());
        holder.binding.itemProductTvProductPrice.setText(product.getPrice().toString()+" VND");
        if(product.getImage()!=null){
            productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
            productAPI.serverFile(product.getImage()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        if(response.isSuccessful()){
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            holder.binding.itemProductIvProductImage.setImageBitmap(bmp);
                        }
                        else{
                            Log.d("item product adapter",response.errorBody().string());
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
            holder.binding.itemProductIvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
        }
    }

    @Override
    public int getItemCount() {
        return array==null?0:array.size();
    }

    public class ItemProductHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;
        public ItemProductHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("product",(Serializable) array.get(getAdapterPosition()));
                    binding.itemProductIvProductImage.buildDrawingCache();
                    Bitmap bitmap = binding.itemProductIvProductImage.getDrawingCache();

                    ByteArrayOutputStream bStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, bStream);
                    byte[] byteArray = bStream.toByteArray();
                    intent.putExtra ("product_image", byteArray);
                    context.startActivity(intent);

                }
            });
        }
    }
}
