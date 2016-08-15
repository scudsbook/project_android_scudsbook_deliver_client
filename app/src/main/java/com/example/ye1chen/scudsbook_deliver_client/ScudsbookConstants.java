package com.example.ye1chen.scudsbook_deliver_client;

/**
 * Created by ye1.chen on 5/10/16.
 */
public class ScudsbookConstants {

    // Server connection error code - Log In
    public static final String error_security_fail = "error_security_fail";
    public static final String error_mobile_data = "error_mobile_data";
    // Server connection success code - Log In
    public static final String result_account_exist = "result_account_exist";
    public static final String result_account_login = "result_account_login";

    // Server database key - User
    public static final String key_scudsbook = "_key_scudsbook";
    public static final String key_type = "_key_type";
    public static final String user_name = "_user_name";
    public static final String deliver_by = "_deliver_by";
    public static final String password = "_password";
    public static final String location_lat = "_location_lat";
    public static final String location_lan = "_location_lan";

    // Server database key type
    public static final String type_user_request = "user_request";
    public static final String type_location_update = "location_update";
    public static final String type_admin_key = "user_admin_key";
    public static final String type_location_query = "location_query";
    public static final String type_order_info_update = "order_info_update";
    public static final String type_manager_order_info_query = "manager_order_info_query";
    public static final String type_manager_order_info_list_query = "manager_order_info_list_query";;
    public static final String type_deliver_order_info_query = "deliver_order_info_query";
    public static final String type_deliver_order_info_list_query = "deliver_order_info_list_query";
    public static final String type_set_deliver_by_deliver = "set_deliver_by_deliver";
    public static final String type_user_list_query = "user_list_query";

    public static final String admin_key = "admin_key";
    public static final String non_admin_key = "non_admin_key";

    // Server database key - order info
    public static final String key_order_info_id = "key_order_info_id";
    public static final String key_order_info_customer_name = "key_order_info_customer_name";
    public static final String key_order_info_customer_phone = "key_order_info_customer_phone";
    public static final String key_order_info_distance = "key_order_info_distance";
    public static final String key_order_info_address = "key_order_info_address";
    public static final String key_order_info_city = "key_order_info_city";
    public static final String key_order_info_state = "key_order_info_state";
    public static final String key_order_info_zip = "key_order_info_zip";
    public static final String key_order_info_product_cost = "key_order_info_product_cost";
    public static final String key_order_info_deliver_fee = "key_order_info_deliver_fee";
    public static final String key_order_info_tip = "key_order_info_tip";
    public static final String key_order_info_total = "key_order_info_total";
    public static final String key_order_info_deliver_by = "key_order_info_deliver_by";
    public static final String key_order_info_order_summary = "key_order_info_summary";
    public static final String key_order_info_order_time = "key_order_info_time";


    public static final String ORDER_INFO_DELIVERBY_NOT_SET = "not_set";
}
