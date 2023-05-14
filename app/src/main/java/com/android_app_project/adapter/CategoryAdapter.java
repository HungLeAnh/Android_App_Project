package com.android_app_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.RectF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.activity.SearchActivity;
import com.android_app_project.databinding.ItemCategoryBinding;
import com.android_app_project.databinding.ItemFavoriteBinding;
import com.android_app_project.entities.Category;

import java.io.Serializable;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<Category> categoryList;
    Context context;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding itemCategoryBinding = ItemCategoryBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new CategoryAdapter.CategoryViewHolder(itemCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, int position) {
        Category category = categoryList.get(position);
        holder.binding.itemCategoryBtn.setText(category.getCategoryName());
        holder.binding.itemCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SearchActivity.class);
                intent.putExtra("category",(Serializable) category);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList==null?0:categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;
        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
