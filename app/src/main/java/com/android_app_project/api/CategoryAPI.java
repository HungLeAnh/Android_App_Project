package com.android_app_project.api;

import com.android_app_project.entities.Category;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CategoryAPI {
    @GET("get")
    Call<List<Category>> getAll();
    @GET("get-root")
    Call<List<Category>> getParent();
    @GET("get-child")
    Call<List<Category>> getSubCategory(@Query("parentId") Long parentId);
}
