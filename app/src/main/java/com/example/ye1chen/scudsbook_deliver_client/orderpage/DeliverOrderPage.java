package com.example.ye1chen.scudsbook_deliver_client.orderpage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.ye1chen.scudsbook_deliver_client.HttpConnection;
import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookConstants;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookUtil;
import com.example.ye1chen.scudsbook_deliver_client.UserInfo;

import java.util.HashMap;

/**
 * Created by ye1.chen on 4/28/16.
 */
public class DeliverOrderPage extends Activity implements View.OnClickListener{

    public static final String INTENT_EXTRA_KEY_ORDER_ID = "intent_extra_key_order_id";

    private String orderId;
    private LinearLayout mOrderLout;
    private static OrderInfo info;
    private ListView mListView;
    private Button mSetUpDeliver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        orderId = getIntent().getStringExtra(INTENT_EXTRA_KEY_ORDER_ID);
        mOrderLout = (LinearLayout) findViewById(R.id.ly_list_item_order);
        mListView = (ListView) findViewById(R.id.item_list);
        mSetUpDeliver = (Button) findViewById(R.id.set_deliver);
        mSetUpDeliver.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //info = ScudsbookDba.getDB().getOrderInfoById(getContentResolver(), orderId);
        new Thread(new OrderInfoQuery()).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_deliver:
                showDialog();
                break;
        }
    }

    private class OrderInfoQuery implements Runnable {

        @Override
        public void run() {
            info = ScudsbookUtil.getOrderInfoFromServerDeliver(DeliverOrderPage.this, String.valueOf(orderId));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(ScudsbookUtil.isDeliverSet(info))
                        mSetUpDeliver.setVisibility(View.GONE);
                    else
                        mSetUpDeliver.setVisibility(View.VISIBLE);
                    ScudsbookUtil.setUpListItemCurrentOrderView(DeliverOrderPage.this, mOrderLout, info);
                    setUpList();
                }
            });
        }
    }

    private void setUpList() {
        if(info == null)
            return;
        String[] orderDetial = info.getOrderSum().split(",");
        mListView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,orderDetial));
    }

    void showDialog() {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.set_deliver_dialog_title);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(R.string.set_deliver_dialog_message)
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    new Thread(new OderInfoUpdateTask(getActivity())).start();
                                }
                            }
                    )
                    .setNegativeButton(android.R.string.cancel,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dismiss();
                                }
                            }
                    )
                    .create();
        }
    }

    private static class OderInfoUpdateTask implements Runnable {

        Context mContext;

        public OderInfoUpdateTask(Context context) {
            this.mContext = context;
        }
        @Override
        public void run() {
            ScudsbookUtil.setDeliverByDeliver(mContext,info.getId(), info.getSubmitBy(), UserInfo.getInstance(mContext).getUserName());
            ((Activity)mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ((Activity)mContext).finish();
                }
            });
        }
    }
}
