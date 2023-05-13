package com.android_app_project.api;

import com.android_app_project.entities.Customer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface CustomerAPI {
    @GET("profile")
    Call<Customer> profile(@Header("authorization") String jwt);
    @PUT("profile/update")
    Call<Customer> updateProfile(@Header("authorization") String jwt);
}
