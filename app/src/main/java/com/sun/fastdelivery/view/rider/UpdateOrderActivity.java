package com.sun.fastdelivery.view.rider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.presenter.UpdateOrderPresenter;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.IUpdateOrderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateOrderActivity extends BaseActivity<UpdateOrderPresenter, IUpdateOrderView> implements IUpdateOrderView {

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
    @BindView(R.id.tvArrivePlace)
    TextView mTvArrivePlace;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;
    @BindView(R.id.btnUpdateOrder)
    Button mBtnUpdateOrder;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnUpdateOrder)
    public void updateOrder(){
        mPresenter.updateOrder();
    }

    private ShapeLoadingDialog mLoadingView;
    private Order mOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_order);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected UpdateOrderPresenter createPresenter() {
        return new UpdateOrderPresenter();
    }

    private void initView() {
        mTvTitle.setText("订单详细");
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
            int arrivePlace = mOrder.getOrderShippingInfo().getArrivePlace();
            String arrivePlaceText = "";
            switch (arrivePlace){
                case 0:
                    arrivePlaceText = "还没开始配送，下个配送网点为网点1";
                    break;
                case 1:
                case 2:
                case 3:
                    arrivePlaceText = "物件配送到网点" + arrivePlace + "，下个网点为" + (arrivePlace + 1);
                    break;
                case 4:
                    arrivePlaceText = "物件已送达";
                    mBtnUpdateOrder.setVisibility(View.GONE);
                    break;
            }
            mTvArrivePlace.setText(arrivePlaceText);
            mTvMoney.setText("订单总额：￥" + mOrder.getOrderPrice());
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
    public void onUpdateOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("更新订单成功！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
        builder.show();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }
}
