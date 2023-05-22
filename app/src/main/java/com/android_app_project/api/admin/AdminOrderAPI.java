package com.android_app_project.api.admin;

import com.android_app_project.entities.Order;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AdminOrderAPI {
    @GET("get-all")
    Call<List<Order>> getall();
    @PUT("status")
    Call<ResponseBody> updateStatus(@Query("orderId") Long orderId,
                                   @Query("update") String status);
}
