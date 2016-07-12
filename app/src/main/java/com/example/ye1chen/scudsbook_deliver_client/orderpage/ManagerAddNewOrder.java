package com.example.ye1chen.scudsbook_deliver_client.orderpage;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.ye1chen.scudsbook_deliver_client.R;

/**
 * Created by ye1.chen on 7/12/16.
 */
public class ManagerAddNewOrder extends Activity{

    private EditText mCustomerName;
    private EditText mCustomerPhone;
    private EditText mDistance;
    private EditText mAddress;
    private EditText mCity;
    private EditText mState;
    private EditText mZip;
    private EditText mProductCost;
    private EditText mDeliverFee;
    private EditText mTip;
    private EditText mTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_page);
        setEditTextView();
    }

    private void setEditTextView() {
        mCustomerName = (EditText) findViewById(R.id.ed_order_name);
        mCustomerPhone = (EditText) findViewById(R.id.ed_order_phone);
        mDistance = (EditText) findViewById(R.id.ed_order_distance);
        mAddress = (EditText) findViewById(R.id.ed_order_address);
        mCity = (EditText) findViewById(R.id.ed_order_city);
        mState = (EditText) findViewById(R.id.ed_order_state);
        mZip = (EditText) findViewById(R.id.ed_order_product_cost);
        mProductCost = (EditText) findViewById(R.id.ed_order_product_cost);
        mProductCost.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTotal.setText(String.valueOf(calTotal(mProductCost.getText().toString(), mDeliverFee.getText().toString(), mTip.getText().toString())));
            }
        });
        mDeliverFee = (EditText) findViewById(R.id.ed_order_deliver_fee);
        mDeliverFee.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTotal.setText(String.valueOf(calTotal(mProductCost.getText().toString(), mDeliverFee.getText().toString(), mTip.getText().toString())));
            }
        });
        mTip = (EditText) findViewById(R.id.ed_order_tip);
        mTip.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTotal.setText(String.valueOf(calTotal(mProductCost.getText().toString(), mDeliverFee.getText().toString(), mTip.getText().toString())));
            }
        });
        mTotal = (EditText) findViewById(R.id.ed_order_total);
        mTotal.setText("0");
    }

    private int calTotal(String a, String b, String c) {
        int total = 0;
        int v1,v2,v3;
        try {
            if(TextUtils.isEmpty(a))
                v1 = 0;
            else
                v1 = Integer.parseInt(a);
            if(TextUtils.isEmpty(b))
                v2 = 0;
            else
                v2 = Integer.parseInt(b);
            if(TextUtils.isEmpty(c))
                v3 = 0;
            else
                v3 = Integer.parseInt(c);
            total = v1 + v2 + v3;
        } catch (Exception e) {
            total = 0;
        }
        return total;
    }
}
