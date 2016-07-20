package com.example.ye1chen.scudsbook_deliver_client.orderpage;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.database.ScudsbookDba;

/**
 * Created by ye1.chen on 7/12/16.
 */
public class ManagerAddNewOrder extends Activity implements View.OnClickListener{

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
    private EditText mSummary;

    private Button mSubmit;

    private String profileEditId;

    public static final String INTENT_TYPE_EDIT = "add_new_order_intent_type_edit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_page);
        setEditTextView();
        mSubmit = (Button) findViewById(R.id.bt_submit);
        mSubmit.setOnClickListener(this);
        profileEditId = getIntent().getStringExtra(INTENT_TYPE_EDIT);
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
        mSummary = (EditText) findViewById(R.id.ed_order_summary);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_submit) {
            submitItem();
        }
    }

    private void submitItem() {
        String customerName = mCustomerName.getText().toString();
        String customerPhone = mCustomerPhone.getText().toString();
        String distance = mDistance.getText().toString();
        String address = mAddress.getText().toString();
        String city = mCity.getText().toString();
        String state = mState.getText().toString();
        String zip = mZip.getText().toString();
        String tip = mTip.getText().toString();
        String total = mTotal.getText().toString();
        String summary = mSummary.getText().toString();

        if (TextUtils.isEmpty(customerName) || TextUtils.isEmpty(customerPhone)
                || TextUtils.isEmpty(distance) || TextUtils.isEmpty(address) || TextUtils.isEmpty(city)
                || TextUtils.isEmpty(state) || TextUtils.isEmpty(zip) || TextUtils.isEmpty(tip)
                || TextUtils.isEmpty(total) || TextUtils.isEmpty(summary)) {
            Toast.makeText(this,
                    getResources().getString(R.string.no_item_info),
                    Toast.LENGTH_LONG).show();
        } else{
            OrderInfo info;
            if (TextUtils.isEmpty(profileEditId)) {
                info = new OrderInfo();
                info.setOrderTime(String.valueOf(System.currentTimeMillis()));
            } else {
                info = ScudsbookDba.getDB().getOrderInfoById(getContentResolver(), profileEditId);
            }
            info.setCustomerName(customerName);
            info.setCustomerPhone(customerPhone);
            info.setDistance(distance);
            info.setAddress(address);
            info.setCity(city);
            info.setState(state);
            info.setZip(zip);
            info.setTip(tip);
            info.setTotal(total);
            info.setOrderSum(summary);

            if (TextUtils.isEmpty(profileEditId)) {
                ScudsbookDba.getDB().saveOrderInfo(getContentResolver(), info);
            } else {
                ScudsbookDba.getDB().updateAAProfile(getContentResolver(), info);
            }
            this.finish();
        }
    }
}
