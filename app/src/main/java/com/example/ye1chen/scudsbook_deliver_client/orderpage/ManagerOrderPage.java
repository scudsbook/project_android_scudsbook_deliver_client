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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.ye1chen.scudsbook_deliver_client.Object.OrderInfo;
import com.example.ye1chen.scudsbook_deliver_client.R;
import com.example.ye1chen.scudsbook_deliver_client.ScudsbookUtil;
import com.example.ye1chen.scudsbook_deliver_client.UserInfo;

/**
 * Created by ye1.chen on 4/28/16.
 */
public class ManagerOrderPage extends Activity implements AdapterView.OnItemSelectedListener{

    public static final String INTENT_EXTRA_KEY_ORDER_ID = "intent_extra_key_order_id";

    private static String orderId;
    private LinearLayout mOrderLout;
    private OrderInfo info;
    private ListView mListView;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_page);
        orderId = getIntent().getStringExtra(INTENT_EXTRA_KEY_ORDER_ID);
        mOrderLout = (LinearLayout) findViewById(R.id.ly_list_item_order);
        mListView = (ListView) findViewById(R.id.item_list);
        mSpinner = (Spinner) findViewById(R.id.set_deliver_by_manager);
        mSpinner.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //info = ScudsbookDba.getDB().getOrderInfoById(getContentResolver(), orderId);
        new Thread(new OrderInfoQuery()).start();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        showDialog(mSpinner.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class OrderInfoQuery implements Runnable {

        @Override
        public void run() {
            info = ScudsbookUtil.getOrderInfoFromServerManager(ManagerOrderPage.this, String.valueOf(orderId));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(ScudsbookUtil.isDeliverSet(info))
                        mSpinner.setVisibility(View.GONE);
                    else {
                        mSpinner.setVisibility(View.VISIBLE);
                        new Thread(new QueryUserListTask()).start();
                    }
                    ScudsbookUtil.setUpListItemCurrentOrderView(ManagerOrderPage.this, mOrderLout, info);
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


    private class QueryUserListTask implements Runnable {
        @Override
        public void run() {
            String temp = ScudsbookUtil.queryUserList(getApplicationContext());
            if (TextUtils.isEmpty(temp))
                return;
            final String[] userArray = temp.split(";");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSpinner.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, userArray));
                }
            });
        }
    }

    void showDialog(String deliver_by) {
        DialogFragment newFragment = MyAlertDialogFragment.newInstance(
                R.string.set_deliver_by_manager, deliver_by);
        newFragment.show(getFragmentManager(), "dialog");
    }

    public static class MyAlertDialogFragment extends DialogFragment {

        public static MyAlertDialogFragment newInstance(int title, String deliver_by) {
            MyAlertDialogFragment frag = new MyAlertDialogFragment();
            Bundle args = new Bundle();
            args.putInt("title", title);
            args.putString("user", deliver_by);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            int title = getArguments().getInt("title");
            final String deliver_by = getArguments().getString("user");

            return new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(getActivity().getResources().getString(R.string.set_deliver_dialog_message_by_manager_1)+" "+deliver_by
                            +" "+getActivity().getResources().getString(R.string.set_deliver_dialog_message_by_manager_2))
                    .setPositiveButton(android.R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    new Thread(new OderInfoUpdateTask(getActivity(),deliver_by)).start();
                                    getActivity().finish();
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
        String deliver_by;

        public OderInfoUpdateTask(Context context, String deliver_by) {
            this.mContext = context;
            this.deliver_by = deliver_by;
        }
        @Override
        public void run() {
            ScudsbookUtil.setDeliverByDeliver(mContext,orderId, UserInfo.getInstance(mContext).getUserName(),deliver_by);
        }
    }
}
