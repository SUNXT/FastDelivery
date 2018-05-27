package com.sun.fastdelivery.view.rider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mingle.widget.LoadingView;
import com.mingle.widget.ShapeLoadingDialog;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.presenter.TakeOrderPresenter;
import com.sun.fastdelivery.utils.JsonUtils;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.ITakeOrderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakeOrderActivity extends BaseActivity<TakeOrderPresenter, ITakeOrderView> implements ITakeOrderView {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;
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
    @BindView(R.id.tvTools)
    TextView mTvTools;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.tvTakeOrder)
    public void takeOrder(){
        mPresenter.takeOrder();
    }

    private Order mOrder;
    private ShapeLoadingDialog mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_order);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected TakeOrderPresenter createPresenter() {
        return new TakeOrderPresenter();
    }

    private void initView() {
        mTvTitle.setText("接单");
        Intent data = getIntent();
        if (data != null){
            mOrder = (Order) data.getSerializableExtra(Order.TAG);
        }
        if (mOrder != null){
            mTvSendLocationInfo.setText(mOrder.getOrderShippingInfo().getDeparture());
            mTvReceiveLocationInfo.setText(mOrder.getOrderShippingInfo().getDestination());
            mTvSendContactInfo.setText("联系人：" + mOrder.getPosterName() + " " + mOrder.getPosterPhone());
            mTvReceiveContactInfo.setText("联系人：" + mOrder.getReceiverName() + " " + mOrder.getReceiverPhone());
            mTvTools.setText("配送方式：" + mOrder.getOrderShippingInfo().getDistributionUtil());
            mTvMoney.setText(mOrder.getOrderPrice() + "元");
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
        }

        mLoadingView = new ShapeLoadingDialog(this);
        mLoadingView.setLoadingText("加载中...");
        mLoadingView.setCanceledOnTouchOutside(false);

    }

    @Override
    public void showLoading() {
        mLoadingView.show();
    }

    @Override
    public void stopLoading() {
        mLoadingView.dismiss();
    }

    @Override
    public RiderUser getRiderUser() {
        return UserSpUtils.getRiderUser(this);
    }

    @Override
    public Order getOrder() {
        return mOrder;
    }

    @Override
    public void onTakeOrderSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("接单成功！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
         builder.show();
    }

    @Override
    public void onFailure(String msg) {
        ToastUtils.showToast(msg);
    }
}
