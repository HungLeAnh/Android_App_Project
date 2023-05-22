package com.android_app_project.Utils;



import android.text.format.DateFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.EnumMap;
import java.util.List;

public class Constants {
    public static String localhost = "10.0.2.2:8083";
    //login và register API
    public static final String ROOT_URL = "http://" + localhost + "/";
    public static final String URL_REGISTRATION = ROOT_URL + "auth/";
    public static final String URL_CUSTOMER = ROOT_URL + "profile/";
    public static final String URL_CATEGORY = ROOT_URL + "category/";
    public static final String URL_PRODUCT = ROOT_URL + "product/";
    public static final String URL_CARTITEM = ROOT_URL + "cart-item/";
    public static final String URL_ITEMSTOCK = ROOT_URL + "item-stock/";
    public static final String URL_ORDER = ROOT_URL + "order/";
    public static final String URL_ORDER_ITEM = ROOT_URL + "order-item/";
    public static final String URL_ADMIN_STAT = ROOT_URL + "admin/stat/";
    public static final String URL_ADMIN_PRODUCT = ROOT_URL + "admin/product/";
    public static final String URL_ADMIN_ORDER = ROOT_URL + "admin/order/";
    public static final String URL_ADMIN_STOCK = ROOT_URL + "admin/item-stock/";

    public static List<String> ORDER_STATUS = Arrays.asList(
            "Chờ xác nhận","Chờ đóng gói",
            "Đã đóng gói","Đang vận chuyển","Đã giao hàng"
            ,"Hàng gặp lỗi","Đơn đã hủy","Yêu cầu hủy đơn");
    public static List<String> ADMIN_ORDER_STATUS = Arrays.asList(
            "Xác nhận","Đóng gói","Vận chuyển","Giao hàng");
}