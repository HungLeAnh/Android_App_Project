package com.android_app_project.adapter;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android_app_project.R;
import com.android_app_project.entities.Cart;
import com.android_app_project.entities.CartItem;
import com.android_app_project.entities.Product;

import java.util.List;

public class CheckOutBottomSheetListViewAdapter extends BaseAdapter {

    List<CartItem> cartItemList;

    public CheckOutBottomSheetListViewAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @Override
    public int getCount() {
        return cartItemList.size();
    }

    @Override
    public CartItem getItem(int position) {
        return cartItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartItemList.get(position).getCartItemId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.item_check_out_item, null);
        } else viewProduct = convertView;

        //Bind sữ liệu phần tử vào View
        CartItem cartItem = getItem(position);
        ((TextView) viewProduct.findViewById(R.id.item_check_out_item_tv_product_name))
                .setText(String.format(cartItem.getProduct().getProductName()));
        ((TextView) viewProduct.findViewById(R.id.item_check_out_item_tv_product_price))
                .setText(String.format(cartItem.getProduct().getPrice().toString()));
        ((TextView) viewProduct.findViewById(R.id.item_check_out_item_tv_product_size))
                .setText(String.format(cartItem.getSize().getValue()));
        ((TextView) viewProduct.findViewById(R.id.item_check_out_item_tv_productAmount))
                .setText(String.format(String.valueOf(cartItem.getCount())));
        ((ImageView) viewProduct.findViewById(R.id.item_check_out_item_color_iv_color))
                .setImageDrawable(new ColorDrawable(android.graphics.Color.parseColor(cartItem.getColor().getValue())));
        return viewProduct;
    }
}
