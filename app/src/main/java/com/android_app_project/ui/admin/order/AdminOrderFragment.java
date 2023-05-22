package com.android_app_project.ui.admin.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android_app_project.Utils.Constants;
import com.android_app_project.adapter.ItemOrderAdapter;
import com.android_app_project.adapter.admin.ItemAdminOrderAdapter;
import com.android_app_project.api.RetrofitClient;
import com.android_app_project.api.admin.AdminOrderAPI;
import com.android_app_project.databinding.AdminFragmentOrderBinding;
import com.android_app_project.databinding.FragmentNotificationsBinding;
import com.android_app_project.entities.Order;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderFragment extends Fragment {

    private static final String TAG = AdminOrderFragment.class.getName();
    private AdminFragmentOrderBinding binding;
    Context context;
    AdminOrderAPI adminOrderAPI;
    ItemAdminOrderAdapter itemAdminOrderAdapter;
    List<Order> orderList;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = AdminFragmentOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        context = getContext();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adminOrderAPI = RetrofitClient.getInstance().getRetrofit(Constants.URL_ADMIN_ORDER).create(AdminOrderAPI.class);
        adminOrderAPI.getall().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                try {
                    Log.i("logg", "raw: " + response.raw());
                    if(response.isSuccessful()){
                        Log.i("loggi", "onResponse: " );
                        orderList = new ArrayList<>(response.body());
                        Log.i(TAG, "onResponse: "+orderList.get(0).getTotalPrice());
                        itemAdminOrderAdapter = new ItemAdminOrderAdapter(orderList,context);
                        binding.adminorderRvOrderItem.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                        binding.adminorderRvOrderItem.setLayoutManager(layoutManager);
                        binding.adminorderRvOrderItem.setAdapter(itemAdminOrderAdapter);
                        itemAdminOrderAdapter.notifyDataSetChanged();
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
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}