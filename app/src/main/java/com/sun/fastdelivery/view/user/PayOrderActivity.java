package com.sun.fastdelivery.view.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.mingle.widget.ShapeLoadingDialog;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.presenter.PayOrderPresenter;
import com.sun.fastdelivery.utils.JsonUtils;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.IPayOrderView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayOrderActivity extends BaseActivity<PayOrderPresenter, IPayOrderView> implements IPayOrderView {

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
    @BindView(R.id.ivType)
    ImageView mIvType;
    @BindView(R.id.tvType)
    TextView mTvType;
    @BindView(R.id.tvGoodWeight)
    TextView mTvGoodWeight;
    @BindView(R.id.tvTools)
    TextView mTvTools;
    @BindView(R.id.rgPayWay)
    RadioGroup mRgPayWay;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;

    //退出支付界面
    @OnClick(R.id.ivBack)
    public void goBack(){
        if (mExitDialog == null){
            mExitDialog = new AlertDialog.Builder(this)
                    .setMessage("订单支付未完成，您确定要离开支付页面吗？")
                    .setPositiveButton("继续支付", null)
                    .setNegativeButton("确认离开", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
        mExitDialog.show();
    }
    AlertDialog.Builder mExitDialog;

    //取消订单
    @OnClick(R.id.tvCancelOrder)
    public void onCancelOrder(){
        if (mCancelOrderDialog == null){
            mCancelOrderDialog = new AlertDialog.Builder(this)
                    .setMessage("客官，您确定要取消该订单吗？")
                    .setPositiveButton("继续支付", null)
                    .setNegativeButton("取消订单", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mPresenter.cancelOrder();
                        }
                    });
        }
        mCancelOrderDialog.show();
    }
    AlertDialog.Builder mCancelOrderDialog;

    //支付订单
    @OnClick(R.id.tvPayOrder)
    public void payOrder(){
        mPresenter.payOrder();
    }

    private Order mOrder;//订单
    private ShapeLoadingDialog mLoadingView;//进度对话框
    private String mPayWay = "支付宝";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected PayOrderPresenter createPresenter() {
        return new PayOrderPresenter();
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

        mRgPayWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rbAliPay){
                    mPayWay = "支付宝";
                }else {
                    mPayWay = "微信支付";
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        goBack();
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
    public User getUser() {
        return UserSpUtils.getUser(this);
    }

    @Override
    public Order getOrder() {
        return mOrder;
    }

    @Override
    public String getPayWay() {
        return mPayWay;
    }

    @Override
    public void onPayOrderSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("支付订单成功！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setCancelable(false);
        builder.show();
    }

    @Override
    public void onCancelOrderSuccess() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage("取消订单成功！")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        setResult(RESULT_OK);
                        finish();
                    }
                })
                .setCancelable(false);
        builder.show();
    }

    @Override
    public void showError(String msg) {
        ToastUtils.showToast(msg);
    }
}
