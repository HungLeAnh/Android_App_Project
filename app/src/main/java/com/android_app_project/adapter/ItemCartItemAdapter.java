package com.android_app_project.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.slidingpanelayout.widget.SlidingPaneLayout;

import com.android_app_project.Helper.IClickItemListener;
import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemCartItemBinding;
import com.android_app_project.entities.CartItem;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemCartItemAdapter extends RecyclerView.Adapter<ItemCartItemAdapter.ItemCartItemHolder> {

    List<CartItem> cartItemList;

    Context context;

    IClickItemListener iClickItemListener;

    ProductAPI productAPI;

    public ItemCartItemAdapter(List<CartItem> cartItemList, IClickItemListener iClickItemListener, Context context) {
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
    }

    @Override
    public int getItemCount() {
        return cartItemList==null?0:cartItemList.size();
    }

    public class ItemCartItemHolder extends RecyclerView.ViewHolder {

        private ItemCartItemBinding binding;

        public ItemCartItemHolder(ItemCartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
