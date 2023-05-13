package com.android_app_project.api;

import com.android_app_project.entities.Order;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface OrderAPI {
    @POST("create")
    Call<Order> createOrder(@Header("authorization") String jwt,
                            @Query("address") String address,
                            @Query("description") String description,
                            @Query("notification") String notification,
                            @Query("cartitem") String cartItemJson);
}
