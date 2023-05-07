package com.android_app_project.ui.favorite;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android_app_project.Helper.CustomButtomonClickListener;
import com.android_app_project.Helper.SimpleSwipeHelper;
import com.android_app_project.R;
import com.android_app_project.activity.MainActivity;
import com.android_app_project.adapter.ItemFavoriteAdapter;
import com.android_app_project.databinding.FragmentDashboardBinding;
import com.android_app_project.databinding.FragmentFavoriteBinding;
import com.android_app_project.entities.Product;
import com.android_app_project.ui.dashboard.DashboardViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment {

    List<Product> productList;

    ItemFavoriteAdapter itemFavoriteAdapter;
    Product deleteProduct;
    Context context;
    private FragmentFavoriteBinding binding;

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();

        GetCategory();
        //new ItemTouchHelper(simpleCallback).attachToRecyclerView(binding.favoriteFragmentRvProductList);
        SimpleSwipeHelper simpleSwipeHelper = new SimpleSwipeHelper(context,binding.favoriteFragmentRvProductList,200) {
            @Override
            public void instantiateCustomButton(RecyclerView.ViewHolder viewHolder, List<CustomButtom> buffer) {
                buffer.add(new CustomButtom(context,"Delete",
                        R.drawable.ic_delete_24dp,
                        30,
                        Color.parseColor("#FF3C30"),
                        new CustomButtomonClickListener(){

                            @Override
                            public void onClick(int pos) {
                                Toast.makeText(context,"Detele click",Toast.LENGTH_SHORT).show();
                            }
                        })
                );
            };
        };

        return root;
    }
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView,
                              @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            deleteProduct = productList.get(position);
            productList.remove(position);
            itemFavoriteAdapter.notifyItemRemoved(position);
            Log.i("logi", "onSwiped: item còn lại : "+itemFavoriteAdapter.getItemCount());
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            final int WIPE_DIRECTION_LEFT = 0;
            final int WIPE_DIRECTION_RIGHT = 1;
            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive){
                int direction = dX >0 ? WIPE_DIRECTION_RIGHT : WIPE_DIRECTION_RIGHT;
                int absoluteDisplacement = Math.abs((int)dX);

                switch (direction){
                    case WIPE_DIRECTION_LEFT:
                        //Draw backgound
                        View itemView = viewHolder.itemView;
                        ColorDrawable bg = new ColorDrawable();
                        bg.setColor(Color.RED);
                        bg.setBounds(itemView.getLeft(),itemView.getTop(),itemView.getRight(),itemView.getBottom());
                        bg.draw(c);
                        //Draw icon
                        Drawable icon = ActivityCompat.getDrawable(context,R.drawable.ic_delete_24dp);
                        int top = ((itemView.getHeight()/2) - (icon.getIntrinsicHeight()/2)) + itemView.getTop();
                        icon.setBounds(0,top,0+icon.getIntrinsicWidth(),top+icon.getIntrinsicHeight());
                        icon.draw(c);

                        break;
                    case WIPE_DIRECTION_RIGHT:


                        break;
                }

            }
        }
    };

    @SuppressLint("NotifyDataSetChanged")
    private void GetCategory() {
        productList = new ArrayList<>();
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(10034400.0323)),"asdtythf"));
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(13430000.0323)),"asdhbvxzf"));
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(10034200.0323)),"asdeghjgjf"));
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(10430100.0323)),"asdbghehf"));
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(10304300.0323)),"asdntimvcf"));
        productList.add(new Product("android.resource://"+R.class.getPackage().getName()+"/" +R.drawable.male,new BigDecimal(Double.toString(10061300.0323)),"asdtwywsgf"));
        itemFavoriteAdapter = new ItemFavoriteAdapter(productList,context);
        binding.favoriteFragmentRvProductList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager((context),LinearLayoutManager.VERTICAL,false);
        binding.favoriteFragmentRvProductList.setLayoutManager(layoutManager);
        binding.favoriteFragmentRvProductList.setAdapter(itemFavoriteAdapter);
        itemFavoriteAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}