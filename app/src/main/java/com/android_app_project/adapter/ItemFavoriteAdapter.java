package com.android_app_project.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.databinding.ItemFavoriteBinding;
import com.android_app_project.entities.Product;
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemFavoriteAdapter extends RecyclerView.Adapter<ItemFavoriteAdapter.ItemFavoriteHolder>{

    List<Product> array;

    Context context;
    public ItemFavoriteAdapter(List<Product> array,Context context) {
        this.context = context;
        this.array = array;
    }

    @NonNull
    @Override
    public ItemFavoriteAdapter.ItemFavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFavoriteBinding itemFavoriteBinding = ItemFavoriteBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemFavoriteHolder(itemFavoriteBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemFavoriteAdapter.ItemFavoriteHolder holder, int position) {
        Product product = array.get(position);
        holder.binding.itemFavoriteTvProductName.setText(product.getProductName());
        holder.binding.itemFavoriteTvProductPrice.setText(String.valueOf(product.getPrice()));
        Glide.with(context).load(product.getImage()).into(holder.binding.itemFavoriteIvProductImage);
    }

    @Override
    public int getItemCount() {
        return array==null?0:array.size();
    }

    public class ItemFavoriteHolder extends RecyclerView.ViewHolder {

        private ItemFavoriteBinding binding;

        public ItemFavoriteHolder(ItemFavoriteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
