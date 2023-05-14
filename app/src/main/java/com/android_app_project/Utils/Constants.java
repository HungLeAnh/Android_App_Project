package com.android_app_project.Utils;



import android.text.format.DateFormat;

import java.util.Date;

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


}