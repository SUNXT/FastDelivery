package com.sun.fastdelivery.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.adapter.ArrivePlaceListAdapter;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.utils.JsonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
    @BindView(R.id.tvStatus)
    TextView mTvStatus;
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
    @BindView(R.id.ivType)
    ImageView mIvType;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvGoodWeight)
    TextView mTvGoodWeight;
    @BindView(R.id.tvRemark)
    TextView mTvRemark;
    @BindView(R.id.tvPayWay)
    TextView mTvPayWay;
    @BindView(R.id.tvTools)
    TextView mTvTools;
    @BindView(R.id.rvArrivePlaces)
    RecyclerView mRvArrivePlaces;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        mTvTitle.setText("订单详细");
        Intent data = getIntent();
        if (data != null){
            mOrder = (Order) data.getSerializableExtra(Order.TAG);
            Log.d(TAG, JsonUtils.toJson(mOrder));
        }
        if (mOrder != null){
            String status = "已支付";
            int textColor = getResources().getColor(R.color.black);
            String payWay = "";
            switch (mOrder.getStatus()){
                case Order.STATUS_CANCEL:
                    textColor = getResources().getColor(R.color.red);
                    mTvPayWay.setText("支付方式：");
                    status = "已取消";
                    break;
                case Order.STATUS_COMPLETE:
                    status = "已完成";
                    payWay = mOrder.getOrderPayment().getPayWay();
                    break;
                case Order.STATUS_PAY:
                    textColor = getResources().getColor(R.color.colorPrimary);
                    status = "已支付";
                    payWay = mOrder.getOrderPayment().getPayWay();
                    break;
                case Order.STATUS_SENDING:
                    textColor = getResources().getColor(R.color.colorPrimary);
                    status = "配送中";
                    payWay = mOrder.getOrderPayment().getPayWay();
                    break;
            }
            mTvStatus.setText(status);
            mTvStatus.setTextColor(textColor);
            mTvPayWay.setText("支付方式：" + payWay);

            mTvSendLocationInfo.setText(mOrder.getOrderShippingInfo().getDeparture());
            mTvReceiveLocationInfo.setText(mOrder.getOrderShippingInfo().getDestination());
            mTvSendContactInfo.setText("联系人：" + mOrder.getPosterName() + " " + mOrder.getPosterPhone());
            mTvReceiveContactInfo.setText("联系人：" + mOrder.getReceiverName() + " " + mOrder.getReceiverPhone());
            mTvOrderId.setText("订单号：" + mOrder.getOrderId() + "  时间：" + mOrder.getCreateTime());
            mTvTools.setText("配送方式：" + mOrder.getOrderShippingInfo().getDistributionUtil());
            int resId = R.drawable.cake;
            switch (mOrder.getGoodType()){
                case 1:
                    resId = R.drawable.flower;
                    break;
                case 2:
                    resId = R.drawable.meals;
                    break;
                case 3:
                    resId = R.drawable.fruit;
                    break;
                case 4:
                    resId = R.drawable.file;
                    break;
                case 5:
                    resId = R.drawable.computer;
                    break;
                case 6:
                    resId = R.drawable.clothes;
                    break;
                case 7:
                    resId = R.drawable.other;
                    break;
            }

            mIvType.setImageResource(resId);
            mTvType.setText("类型：" + GoodType.getName(mOrder.getGoodType()));
            mTvGoodWeight.setText("重量：" + mOrder.getGoodWeight() + "kg");
            mTvRemark.setText(mOrder.getRemark());
            if (mOrder.getOrderShippingInfo() != null){
                final int count = mOrder.getOrderShippingInfo().getArrivePlace();
                ArrivePlaceListAdapter adapter= new ArrivePlaceListAdapter(this, count);
                mRvArrivePlaces.setAdapter(adapter);
                mRvArrivePlaces.setFocusable(false);
                LinearLayoutManager manager = new LinearLayoutManager(this){
                    @Override
                    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                        if (getChildCount() > 0) {
                            View firstChildView = recycler.getViewForPosition(0);
                            measureChild(firstChildView, widthSpec, heightSpec);
                            setMeasuredDimension(View.MeasureSpec.getSize(widthSpec), firstChildView.getMeasuredHeight()*count);
                        } else {
                            super.onMeasure(recycler, state, widthSpec, heightSpec);
                        }
                    }

                };
                mRvArrivePlaces.setLayoutManager(manager);
            }

        }

    }
}
