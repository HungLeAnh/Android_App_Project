package com.android_app_project.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.inteface.IClickItemListener;
import com.android_app_project.databinding.ItemProductDetailColorBinding;
import com.android_app_project.entities.Color;

import java.util.List;

public class ItemProductDetailColorAdapter extends RecyclerView.Adapter<ItemProductDetailColorAdapter.ItemProductDetailColorHolder> {

    private static final String TAG = ItemProductDetailColorAdapter.class.getName();
    List<Color> colorList;
    Long productId;
    Context context;
    private int selectedPosition = RecyclerView.NO_POSITION;
    IClickItemListener iClickItemListener;

    public int getSelectedPosition() {
        return selectedPosition;
    }
    public ItemProductDetailColorAdapter(List<Color> colorList, Long productId, IClickItemListener iClickItemListener, Context context) {
        this.colorList = colorList;
        this.context = context;
        this.productId = productId;
        this.iClickItemListener = iClickItemListener;
    }

    @NonNull
    @Override
    public ItemProductDetailColorAdapter.ItemProductDetailColorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductDetailColorBinding itemProductDetailColorBinding = ItemProductDetailColorBinding
                .inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemProductDetailColorHolder(itemProductDetailColorBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductDetailColorAdapter.ItemProductDetailColorHolder holder, int position) {
        holder.itemView.setSelected(selectedPosition ==position);
        Color color = colorList.get(position);
        holder.binding.itemProductDetailColorIvColor.setImageDrawable(new ColorDrawable(android.graphics.Color.parseColor(color.getValue())));
    }

    @Override
    public int getItemCount() {
        return colorList==null?0:colorList.size();
    }

    public class ItemProductDetailColorHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ItemProductDetailColorBinding binding;
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public ItemProductDetailColorHolder(ItemProductDetailColorBinding binding) {
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
