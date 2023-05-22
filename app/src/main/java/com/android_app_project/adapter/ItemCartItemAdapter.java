package com.android_app_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.inteface.IClickItemCartItemListener;
import com.android_app_project.inteface.IClickItemListener;
import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.api.CartItemAPI;
import com.android_app_project.api.ItemStockAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemCartItemBinding;
import com.android_app_project.entities.CartItem;
import com.android_app_project.entities.ItemStock;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCartItemAdapter extends RecyclerView.Adapter<ItemCartItemAdapter.ItemCartItemHolder> {

    private static final String TAG = ItemCartItemAdapter.class.getName();
    List<CartItem> cartItemList;

    Context context;

    IClickItemCartItemListener iClickItemListener;

    ProductAPI productAPI;
    ItemStockAPI itemStockAPI;
    CartItemAPI cartItemAPI;

    public ItemCartItemAdapter(List<CartItem> cartItemList, IClickItemCartItemListener iClickItemListener, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;
        this.iClickItemListener = iClickItemListener;
    }


    @NonNull
    @Override
    public ItemCartItemAdapter.ItemCartItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartItemBinding itemCartItemBinding = ItemCartItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemCartItemHolder(itemCartItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartItemAdapter.ItemCartItemHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        holder.setCartItem(cartItem);
        holder.binding.itemCartItemTvProductName.setText(cartItem.getProduct().getProductName());
        holder.binding.itemCartItemTvProductPrice.setText(cartItem.getProduct().getPrice().toString()+" VND");
        if(cartItem.getProduct().getImage()!=null){
            productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
            productAPI.serverFile(cartItem.getProduct().getImage()).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        if(response.isSuccessful()){
                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                            holder.binding.itemCartItemIvProductImage.setImageBitmap(bmp);
                        }
                        else{
                            Log.d("item product adapter",response.errorBody().string());
                            holder.binding.itemCartItemIvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
                        }
                    }
                    catch (Exception e){
                        Log.d("item product adapter",e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }
        else {
            holder.binding.itemCartItemIvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
        }

        holder.binding.itemCartItemColorIvColor.setImageDrawable(new ColorDrawable(android.graphics.Color.parseColor(cartItem.getColor().getValue())));
        holder.binding.itemCartItemTvProductSize.setText("Kích cỡ: "+cartItem.getSize().getValue());
        holder.binding.itemCartItemTvProductAmount.setText(String.valueOf(cartItem.getCount()));

        itemStockAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ITEMSTOCK).create(ItemStockAPI.class);
        itemStockAPI.getProductStock(cartItem.getProduct().getProductId(),cartItem.getColor().getColorId(),cartItem.getSize().getSizeId()).enqueue(new Callback<ItemStock>() {
            @Override
            public void onResponse(Call<ItemStock> call, Response<ItemStock> response) {
                try{
                    if(response.isSuccessful()){
                        ItemStock itemStock = response.body();
                        holder.setMaxAmount(itemStock.getCount());
                    }
                    else{
                        Log.e(TAG, "onResponse: response error: "+response.errorBody().toString() );
                    }
                }
                catch (Exception e){
                    Log.e(TAG, "onResponse: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ItemStock> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage());
            }
        });
        holder.binding.itemCartItemBtnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAmount = cartItem.getCount()+1;
                if(newAmount<=holder.getMaxAmount()){
                    cartItem.setCount(newAmount);
                    holder.binding.itemCartItemTvProductAmount.setText(String.valueOf(cartItem.getCount()));
                    cartItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CARTITEM).create(CartItemAPI.class);
                    cartItemAPI.update(cartItem).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.isSuccessful()){
                                    iClickItemListener.onClickAmountChange();
                                }
                                else{
                                    Log.e(TAG, "onResponse: "+response.errorBody().toString());
                                }
                            }
                            catch (Exception e ){
                                Log.e(TAG, "onResponse: "+e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(TAG, "onFailure: "+t.getMessage());
                        }
                    });
                }
            }
        });
        holder.binding.itemCartItemBtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newAmount = cartItem.getCount()-1;
                if(newAmount>0){
                    cartItem.setCount(newAmount);
                    holder.binding.itemCartItemTvProductAmount.setText(String.valueOf(cartItem.getCount()));
                    cartItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CARTITEM).create(CartItemAPI.class);
                    cartItemAPI.update(cartItem).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            try {
                                if(response.isSuccessful()){
                                    iClickItemListener.onClickAmountChange();
                                }
                                else{
                                    Log.e(TAG, "onResponse: "+response.errorBody().toString());
                                }
                            }
                            catch (Exception e ){
                                Log.e(TAG, "onResponse: "+e.getMessage());
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e(TAG, "onFailure: "+t.getMessage());
                        }
                    });
                }
            }
        });
        holder.binding.itemCartItemIvDeleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_CARTITEM).create(CartItemAPI.class);
                cartItemAPI.removeFromCart(cartItem).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            if(response.isSuccessful()){
                                Toast.makeText(context,response.body().string(),Toast.LENGTH_SHORT).show();

                                cartItemList.remove(cartItem);
                                notifyItemRemoved(cartItemList.indexOf(cartItem));
                                notifyDataSetChanged();
                                iClickItemListener.onClickAmountChange();

                            }
                            else{
                                Log.e(TAG, "onResponse: "+response.errorBody().toString());
                            }
                        }
                        catch (Exception e ){
                            Log.e(TAG, "onResponse: "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(TAG, "onFailure: "+t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList==null?0:cartItemList.size();
    }

    public class ItemCartItemHolder extends RecyclerView.ViewHolder {

        private ItemCartItemBinding binding;
        private CartItem cartItem;

        private int maxAmount =0;

        public ItemCartItemHolder(ItemCartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public CartItem getCartItem() {
            return cartItem;
        }

        public void setCartItem(CartItem cartItem) {
            this.cartItem = cartItem;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
        }
    }
}
