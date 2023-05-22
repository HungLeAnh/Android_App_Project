package com.android_app_project.api;

import com.android_app_project.entities.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
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
    @GET("get")
    Call<List<Order>> getByCustomerId(@Header("authorization") String jwt);
}
