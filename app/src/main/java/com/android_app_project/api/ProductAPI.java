package com.android_app_project.api;

import com.android_app_project.entities.Product;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProductAPI {
    @GET("get")
    Call<List<Product>> get();
    @GET("images/{filename}")
    Call<ResponseBody> serverFile(@Path(value = "filename") String filename);

    @GET("search")
    Call<List<Product>> search(@Part("name") String name);
}
