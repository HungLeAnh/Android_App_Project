package com.android_app_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.android_app_project.Helper.IClickItemListener;
import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.Utils.SharePrefManager;
import com.android_app_project.adapter.ItemProductDetailColorAdapter;
import com.android_app_project.adapter.ItemProductDetailSizeAdapter;
import com.android_app_project.api.CartItemAPI;
import com.android_app_project.api.ItemStockAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ActivityProductDetailBinding;
import com.android_app_project.databinding.ItemProductDetailSizeBinding;
import com.android_app_project.entities.Color;
import com.android_app_project.entities.ItemStock;
import com.android_app_project.entities.Product;
import com.android_app_project.entities.Size;

import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {

    private static final String TAG = ProductDetailActivity.class.getName();
    private ActivityProductDetailBinding binding;
    Context context;
    Product product;
    Bitmap bitmap;
    List<Color> colors;
    ItemProductDetailColorAdapter itemProductDetailColorAdapter;
    List<Size> sizes;
    CartItemAPI cartItemAPI;
    ItemStockAPI itemStockAPI;
    Long MaxAmount=Long.valueOf(0);

    Long amount = Long.valueOf(0);

    ItemProductDetailSizeAdapter itemProductDetailSizeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = getApplicationContext();
        Intent intent = getIntent();
        product =  (Product) intent.getSerializableExtra("product");
        byte[] byteArray = intent.getByteArrayExtra("product_image");
        bitmap= BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        binding.productDetailBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(context,R.anim.onclick));
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                },300);
            }
        });

        binding.productDetailIvProductImage.setImageBitmap(bitmap);

        binding.productDetailTvProductName.setText(product.getProductName());
        binding.productDetailTvProductPrice.setText(product.getPrice().toString() + " VND");
        binding.productDetailTvProductBrand.setText(product.getBrand());

        sizes = new ArrayList<>(product.getSizes());
        Log.d(TAG, "onCreate: sizes: "+sizes.size());

        itemProductDetailSizeAdapter = new ItemProductDetailSizeAdapter(sizes, product.getProductId(), new IClickItemListener() {
            @Override
            public void onClickItem() {
                onClickColorOrSize();
            }
        }, context);
        binding.productDetailRvProductSizes.setHasFixedSize(true);
        GridLayoutManager layoutManagerSize = new GridLayoutManager((context),4);
        binding.productDetailRvProductSizes.setLayoutManager(layoutManagerSize);
        binding.productDetailRvProductSizes.setAdapter(itemProductDetailSizeAdapter);
        itemProductDetailSizeAdapter.notifyDataSetChanged();

        colors = new ArrayList<>(product.getColors());
        Log.d(TAG, "onCreate: colors: "+colors.size());
        itemProductDetailColorAdapter = new ItemProductDetailColorAdapter(colors, product.getProductId(), new IClickItemListener() {
            @Override
            public void onClickItem() {
                onClickColorOrSize();
            }
        }, context);
        binding.productDetailRvProductColors.setHasFixedSize(true);
        GridLayoutManager layoutManagerColor = new GridLayoutManager((context),4);
        binding.productDetailRvProductColors.setLayoutManager(layoutManagerColor);
        binding.productDetailRvProductColors.setAdapter(itemProductDetailColorAdapter);
        itemProductDetailColorAdapter.notifyDataSetChanged();

        binding.productDetailTvDescription.setText(product.getDescription());
        binding.productDetailRbRatingBar.setRating(product.getRating().floatValue());
        binding.productDetailRbRatingBar.setIsIndicator(true);
        binding.productDetailTvStockCount.setText("Còn 0 sản phẩm");
        binding.productDetailBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount<MaxAmount)
                    amount++;
                binding.productDetailTvProductAmount.setText(amount.toString());
            }
        });
        binding.productDetailBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(amount>0)
                    amount--;
                binding.productDetailTvProductAmount.setText(amount.toString());
            }
        });

        binding.productDetailBtnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int colorPosition = itemProductDetailColorAdapter.getSelectedPosition();
                Log.d(TAG, "onClick: color position"+colorPosition);
                int sizePosition = itemProductDetailSizeAdapter.getSelectedPosition();
                Log.d(TAG, "onClick: size position"+sizePosition);
                if(amount<=0||colorPosition<0||sizePosition<0)
                    return;
                Long colorId = colors.get(colorPosition).getColorId();
                Long sizeId = sizes.get(sizePosition).getSizeId();
                Long amount = Long.valueOf(binding.productDetailTvProductAmount.getText().toString());
                String request = "Bearer "+ SharePrefManager.getInstance(getApplicationContext()).getJWT();

                cartItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CARTITEM).create(CartItemAPI.class);
                cartItemAPI.addToCart(request,product.getProductId(),colorId,sizeId,amount).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if(response.isSuccessful()){
                                Toast.makeText(context,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context,"Thêm vào giỏ hàng không thành công",Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e ){
                            Log.e(TAG, "onResponse: "+e.getMessage());
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });


    }

    private void onClickColorOrSize() {
        binding.productDetailTvStockCount.setText("Còn 0 sản phẩm");
        MaxAmount = Long.valueOf(0);
        amount = Long.valueOf(0);
        binding.productDetailTvProductAmount.setText(amount.toString());
        if(itemProductDetailColorAdapter.getSelectedPosition()<0||
                itemProductDetailSizeAdapter.getSelectedPosition()<0)
            return;
        itemStockAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ITEMSTOCK).create(ItemStockAPI.class);
        Long colorId = colors.get(itemProductDetailColorAdapter.getSelectedPosition()).getColorId();
        Long sizeId = sizes.get(itemProductDetailSizeAdapter.getSelectedPosition()).getSizeId();
        itemStockAPI.getProductStock(product.getProductId(),colorId, sizeId )
                .enqueue(new Callback<ItemStock>() {
                    @Override
                    public void onResponse(Call<ItemStock> call, Response<ItemStock> response) {
                        try {
                            Log.i(TAG,"onResponse: "+ response.raw());
                            if(response.isSuccessful()){
                                ItemStock itemStock = response.body();
                                MaxAmount = Long.valueOf(itemStock.getCount());
                                binding.productDetailTvStockCount.setText("Còn "+MaxAmount.toString()+" sản phẩm");
                                Log.i(TAG,"Max amount :"+MaxAmount);
                            }
                            else{
                                Log.i(TAG, "onResponse: "+response.errorBody().string());
                            }
                        }
                        catch (Exception e){
                            Log.e(TAG,"error: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ItemStock> call, Throwable t) {

                    }
                });
    }
}