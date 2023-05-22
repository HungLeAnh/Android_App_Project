package com.android_app_project.api.admin;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface AdminProductAPI {
    @Multipart
    @POST("create")
    Call<ResponseBody> createProduct(@Part("product") String productJson,
                                     @Part("cateId") Long cateId, @Part("size") String sizeJson,
                                     @Part("color") String colorJson, @Part("image") RequestBody image);
}
