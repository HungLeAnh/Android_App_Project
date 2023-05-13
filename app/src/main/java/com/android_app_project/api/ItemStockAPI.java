package com.android_app_project.api;

import com.android_app_project.entities.ItemStock;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ItemStockAPI {
    
    @GET("get")
    Call<ItemStock> getProductStock(@Query("productId") Long productId,@Query("colorId") Long colorId,@Query("sizeId") Long sizeId);
}
