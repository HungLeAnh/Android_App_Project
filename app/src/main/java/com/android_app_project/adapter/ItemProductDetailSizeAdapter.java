package com.android_app_project.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.Helper.IClickItemListener;
import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.api.ItemStockAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.databinding.ItemProductDetailSizeBinding;
import com.android_app_project.entities.ItemStock;
import com.android_app_project.entities.Size;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemProductDetailSizeAdapter extends RecyclerView.Adapter<ItemProductDetailSizeAdapter.ItemProductDetailSizeHolder> {

    private static final String TAG = ItemProductDetailSizeAdapter.class.getName();
    Context context;
    List<Size> sizeList;
    ItemStockAPI itemStockAPI;
    Long productId;
    private int selectedPosition = RecyclerView.NO_POSITION;
    IClickItemListener iClickItemListener;

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public ItemProductDetailSizeAdapter(List<Size> sizeList,Long productId,IClickItemListener iClickItemListener, Context context) {
        this.context = context;
        this.sizeList = sizeList;
        this.productId = productId;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public ItemProductDetailSizeAdapter.ItemProductDetailSizeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductDetailSizeBinding itemProductDetailSizeBinding = ItemProductDetailSizeBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemProductDetailSizeHolder(itemProductDetailSizeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductDetailSizeAdapter.ItemProductDetailSizeHolder holder, int position) {
        holder.itemView.setSelected(selectedPosition ==position);
        Size size = sizeList.get(position);
        holder.binding.itemProductDetailSizeTvSize.setText(size.getValue());
    }

    @Override
    public int getItemCount() {
        return sizeList==null?0:sizeList.size();
    }

    public class ItemProductDetailSizeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemProductDetailSizeBinding binding;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();
        public ItemProductDetailSizeHolder(ItemProductDetailSizeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(this::onClick);
        }

        @Override
        public void onClick(View v) {
            if (selectedItems.get(getAdapterPosition(), false)) {
                selectedItems.delete(getAdapterPosition());
                v.setSelected(false);
            }
            else {
                selectedItems.put(getAdapterPosition(), true);
                v.setSelected(true);
            }
            notifyItemChanged(selectedPosition);
            selectedPosition = getLayoutPosition();
            notifyItemChanged(selectedPosition);
            iClickItemListener.onClickItem();
        }
    }
}
