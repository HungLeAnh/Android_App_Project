package com.android_app_project.api;

import com.android_app_project.entities.CartItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface CartItemAPI {
    @GET("get")
    Call<List<CartItem>> getListCartItem(@Header("authorization") String jwt);
    @FormUrlEncoded
    @POST("add-to-cart")
    Call<ResponseBody> addToCart(@Header("authorization") String jwt,@Field("productId")Long productId,@Field("colorId")Long colorId,
                                 @Field("sizeId") Long sizeId,@Field("amount")Long amount);

    @PUT("update")
    Call<ResponseBody> update(@Body CartItem cartItem);
}
