package com.android_app_project.api.admin;

import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminStatAPI {
    @GET("total-revenue")
    Call<BigDecimal> calTotalRevenue();
    @GET("count-order")
    Call<Long> countOrder();
    @GET("count-customer")
    Call<Long> countCustomer();
}
