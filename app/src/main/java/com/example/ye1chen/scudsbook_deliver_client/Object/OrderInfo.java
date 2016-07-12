package com.example.ye1chen.scudsbook_deliver_client.Object;

/**
 * Created by ye1.chen on 7/12/16.
 */
public class OrderInfo {

    private String mId;
    private String mCustomerName;
    private String mCustomerPhone;
    private String mDistance;
    private String mAddress;
    private String mCity;
    private String mState;
    private String mZip;
    private String mProductCost;
    private String mDeliverFee;
    private String mTip;
    private String mTotal;

    public void setId(String id) {
        mId = id;
    }

    public void setCustomerName(String name) {
        mCustomerName = name;
    }

    public void setCustomerPhone(String phone) {
        mCustomerPhone = phone;
    }

    public void setDistance(String d) {
        mDistance = d;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public void setState(String state) {mState = state;}

    public void setZip(String zip) {
        mZip = zip;
    }

    public void setProductCost(String cost) {
        mProductCost = cost;
    }

    public void setDeliverFee(String fee) {
        mDeliverFee = fee;
    }

    public void setTip(String tip) {
        mTip = tip;
    }

    public void setTotal(String total) {
        mTotal = total;
    }

    public String getId() {
        return mId;
    }

    public String getCustomerName() {
        return mCustomerName;
    }

    public String getCustomerPhone() {
        return mCustomerPhone;
    }

    public String getDistance() {
        return mDistance;
    }

    public String getAddress() {
        return mAddress;
    }

    public String getCity() {
        return mCity;
    }

    public String getState() {
        return mState;
    }

    public String getZip() {
        return mZip;
    }

    public String getProductCost() {
        return mProductCost;
    }

    public String getDeliverFee() {
        return mDeliverFee;
    }

    public String getTip() {
        return mTip;
    }

    public String getTotal() {
        return mTotal;
    }
}
