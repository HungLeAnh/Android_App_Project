package com.android_app_project.api;

import com.android_app_project.entities.Order;
import com.android_app_project.entities.OrderItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderItemAPI {
    @GET("get")
    Call<List<OrderItem>> getbyOrder(@Query("orderId") Long orderId);
}
