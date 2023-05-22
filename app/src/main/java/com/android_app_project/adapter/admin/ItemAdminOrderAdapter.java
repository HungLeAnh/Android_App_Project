package com.android_app_project.adapter.admin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.R;
import com.android_app_project.Utils.Constants;
import com.android_app_project.activity.OrderDetailActivity;
import com.android_app_project.adapter.ItemOrderAdapter;
import com.android_app_project.api.OrderItemAPI;
import com.android_app_project.api.ProductAPI;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.api.admin.AdminOrderAPI;
import com.android_app_project.databinding.ItemAdminOrderBinding;
import com.android_app_project.databinding.ItemOrderBinding;
import com.android_app_project.entities.Order;
import com.android_app_project.entities.OrderItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdminOrderAdapter extends RecyclerView.Adapter<ItemAdminOrderAdapter.ItemOrderHolder> {

    private static final String TAG = ItemAdminOrderAdapter.class.getName();
    List<Order> orderList;
    OrderItemAPI orderItemAPI;
    ProductAPI productAPI;
    AdminOrderAPI adminOrderAPI;
    Context context;

    public ItemAdminOrderAdapter(List<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemAdminOrderAdapter.ItemOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdminOrderBinding binding = ItemAdminOrderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ItemOrderHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdminOrderAdapter.ItemOrderHolder holder, int position) {
        Order order = orderList.get(position);

        holder.binding.itemadminorderTvTotalPrice.setText(order.getTotalPrice().toString()+" VND");
        holder.setStatusPos(Constants.ORDER_STATUS.indexOf(order.getStatus()));
        if (holder.getStatusPos()<Constants.ADMIN_ORDER_STATUS.size()){
            holder.binding.itemadminorderBtnStatusChange.setText(Constants.ADMIN_ORDER_STATUS.get(holder.getStatusPos()));
        }
        else {
            holder.binding.itemadminorderBtnStatusChange.setText(Constants.ORDER_STATUS.get(holder.getStatusPos()));
        }
        orderItemAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ORDER_ITEM).create(OrderItemAPI.class);
        orderItemAPI.getbyOrder(order.getOrderId()).enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                try {
                    Log.i("logg", "raw: " + response.raw());
                    if(response.isSuccessful()){
                        List<OrderItem> orderItemList = new ArrayList<>(response.body());
                        Log.i("logg", "raw: " + response.raw());
                        holder.setOrderItemList(orderItemList);
                        holder.binding.itemadminorderTvProductAmount.setText("x"+orderItemList.get(0).getCount());
                        holder.binding.itemadminorderTvProductName.setText(orderItemList.get(0).getProduct().getProductName());
                        holder.binding.itemadminorderTvProductSize.setText("Kích cỡ: "+orderItemList.get(0).getSize().getValue());
                        holder.binding.itemadminorderColorIvColor.setImageDrawable(new ColorDrawable(Color.parseColor(orderItemList.get(0).getColor().getValue())));
                        holder.binding.itemadminorderTvProductPrice.setText(orderItemList.get(0).getProduct().getPrice()+" VND");
                        if(orderItemList.get(0).getProduct().getImage()!=null){
                            productAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_PRODUCT).create(ProductAPI.class);
                            productAPI.serverFile(orderItemList.get(0).getProduct().getImage()).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    try{
                                        if(response.isSuccessful()){
                                            Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                                            holder.binding.itemadminorderTvProductImage.setImageBitmap(bmp);
                                        }
                                        else{
                                            Log.d("item product adapter",response.errorBody().string());
                                            holder.binding.itemadminorderTvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
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
                            holder.binding.itemadminorderTvProductImage.setImageResource(R.drawable.ic_image_not_supported_72);
                        }

                        int itemcount = 0;
                        for (int i = 0; i < orderItemList.size(); i++) {
                            itemcount+=orderItemList.get(i).getCount();
                        }
                        holder.binding.itemadminorderTvItemCount.setText(itemcount+" sản phẩm");

                    }
                    else {
                        Log.i("logg", "not success: " + response.errorBody());
                    }
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {

            }
        });

        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("orderitems", (Serializable) holder.getOrderItemList());
                intent.putExtras(bundle);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        holder.binding.itemadminorderBtnStatusChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.getStatusPos()>=Constants.ADMIN_ORDER_STATUS.size())
                    return;
                adminOrderAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ADMIN_ORDER).create(AdminOrderAPI.class);
                int status = (holder.statusPos+1);
                holder.setStatusPos(status+1);
                adminOrderAPI.updateStatus(order.getOrderId(),Constants.ORDER_STATUS.get(status)).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            Log.i("logg", "raw: " + response.raw());
                            if(response.isSuccessful()){
                                Toast.makeText(context,response.body().string() , Toast.LENGTH_SHORT).show();
                                if (holder.getStatusPos()<Constants.ADMIN_ORDER_STATUS.size()){
                                    holder.binding.itemadminorderBtnStatusChange.setText(Constants.ADMIN_ORDER_STATUS.get(holder.getStatusPos()));
                                }
                                else {
                                    holder.binding.itemadminorderBtnStatusChange.setText(Constants.ORDER_STATUS.get(holder.getStatusPos()));
                                }
                            }
                            else {
                                Toast.makeText(context,"lỗi API" , Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e ){
                            Log.i(TAG, "onResponse: exception "+e.getMessage());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList==null?0:orderList.size();
    }

    public class ItemOrderHolder extends RecyclerView.ViewHolder {
        private ItemAdminOrderBinding binding;
        private List<OrderItem> OrderItemList;

        private int statusPos=0;
        public ItemOrderHolder(ItemAdminOrderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }

        public int getStatusPos() {
            return statusPos;
        }

        public void setStatusPos(int statusPos) {
            this.statusPos = statusPos;
        }

        public List<OrderItem> getOrderItemList() {
            return OrderItemList;
        }

        public void setOrderItemList(List<OrderItem> orderItemList) {
            OrderItemList = orderItemList;
        }
    }
}
