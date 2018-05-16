package com.sun.fastdelivery.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayOrderActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvOrderId)
    TextView mTvOrderId;
    @BindView(R.id.tvSendLocationInfo)
    TextView mTvSendLocationInfo;
    @BindView(R.id.tvSendContactInfo)
    TextView mTvSendContactInfo;
    @BindView(R.id.tvReceiveLocationInfo)
    TextView mTvReceiveLocationInfo;
    @BindView(R.id.tvReceiveContactInfo)
    TextView mTvReceiveContactInfo;

    AlertDialog.Builder mExitDialog;
    @OnClick(R.id.ivBack)
    public void goBack(){
        if (mExitDialog == null){
            mExitDialog = new AlertDialog.Builder(this)
                    .setTitle("取消支付")
                    .setMessage("您确定退出支付界面吗？")
                    .setPositiveButton("取消", null)
                    .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
        mExitDialog.show();
    }

    private Order mOrder;//订单

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvTitle.setText("支付订单");
        Intent data = getIntent();
        if (data != null){
            mOrder = (Order) data.getSerializableExtra(Order.TAG);
            Log.d(TAG, JsonUtils.toJson(mOrder));
        }
        if (mOrder != null){
            mTvSendLocationInfo.setText(mOrder.getOrderShippingInfo().getDeparture());
            mTvReceiveLocationInfo.setText(mOrder.getOrderShippingInfo().getDestination());
            mTvSendContactInfo.setText("联系人：" + mOrder.getPosterName() + " " + mOrder.getPosterPhone());
            mTvReceiveContactInfo.setText("联系人：" + mOrder.getReceiverName() + " " + mOrder.getReceiverPhone());
            mTvOrderId.setText("订单号：" + mOrder.getOrderId() + "  时间：" + mOrder.getCreateTime());
        }

    }


    @Override
    public void onBackPressed() {
        goBack();
    }
}
