package com.sun.fastdelivery.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.DispatchInformation;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateOrderActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.tvSendLocationInfo)
    TextView mTvSendLocationInfo;
    @BindView(R.id.tvSendContactInfo)
    TextView mTvSendContactInfo;
    @BindView(R.id.tvReceiveLocationInfo)
    TextView mTvReceiveLocationInfo;
    @BindView(R.id.tvReceiveContactInfo)
    TextView mTvReceiveContactInfo;
    @BindView(R.id.tvSendType)
    TextView mTvGoodType;
    @BindView(R.id.tvWeight)
    TextView mTvWeight;
    @BindView(R.id.tvMoney)
    TextView mTvMoney;

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }
    /**
     * 跳转到完善发件人信息界面
     */
    @OnClick({R.id.tvSendLocationInfo, R.id.tvSendContactInfo, R.id.ivSendLocation})
    public void goCompleteSendInfoView(){
        Intent intent = new Intent(this, CompleteLocationInfoActivity.class);
        startActivityForResult(intent, CODE_SEND_INFO);
    }

    /**
     * 跳转到完善收件人信息界面
     */
    @OnClick({R.id.tvReceiveContactInfo, R.id.tvReceiveLocationInfo, R.id.ivReceiveLocation})
    public void goCompleteReceiveInfoView(){
        Intent intent = new Intent(this, CompleteLocationInfoActivity.class);
        startActivityForResult(intent, CODE_RECEIVE_INFO);
    }

    /**
     * 跳转到选择物品类型
     */
    @OnClick({R.id.ivChooseType, R.id.tvSendType})
    public void chooseGoodType(){
        Intent intent = new Intent(this, ChooseGoodTypeActivity.class);
        startActivityForResult(intent, CODE_CHOOSE_GOOD_TYPE);
    }

    /**
     * 改变配送重量
     * @param view
     */
    @OnClick({R.id.ivCut, R.id.ivAdd})
    public void changeWeight(View view){
        if (view.getId() == R.id.ivCut){
            if (mGoodWeight <= 1){
                ToastUtils.showToast("至少1公斤！");
                return;
            }
            mGoodWeight--;
        }else {
            mGoodWeight++;
        }
        mTvWeight.setText("预计 " + mGoodWeight + " 公斤以下");
        updateOrderMoney();
    }

    /**
     * 创建订单
     */
    @OnClick(R.id.btnCreateOrder)
    public void createOrder(){

    }

    private final int CODE_SEND_INFO = 11;
    private final int CODE_RECEIVE_INFO = 12;
    private final int CODE_CHOOSE_GOOD_TYPE = 13;

    private int mGoodWeight = 1;//重量
    private DispatchInformation mSendInfo = new DispatchInformation();
    private DispatchInformation mReceiveInfo;
    private GoodType mGoodType;
    private int mOrderMoney = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);
        init();
    }


    public void init() {
        Intent data = getIntent();
        if (data != null){
            String location = data.getStringExtra("location");
            mTvSendLocationInfo.setText(location);
            mSendInfo.setLocation(location);
            mSendInfo.setLatLng((LatLng) data.getParcelableExtra("latLng"));
        }
    }

    /**
     * 更新订单价格
     */
    private void updateOrderMoney(){
        if (mSendInfo == null || mReceiveInfo == null){
            return;
        }
        float distance = AMapUtils.calculateLineDistance(mSendInfo.getLatLng(),mReceiveInfo.getLatLng());

        mOrderMoney = (int) (mGoodWeight * (distance/1000) * 10);
        mTvMoney.setText(mOrderMoney + "元");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null){
            switch (requestCode){
                case CODE_SEND_INFO:
                    mSendInfo = data.getParcelableExtra(DispatchInformation.TAG);
                    mTvSendLocationInfo.setText(mSendInfo.getLocation());
                    mTvSendContactInfo.setText(mSendInfo.getUserName() + " " + mSendInfo.getUserPhone());
                    updateOrderMoney();
                    break;
                case CODE_RECEIVE_INFO:
                    mReceiveInfo = data.getParcelableExtra(DispatchInformation.TAG);
                    mTvReceiveLocationInfo.setText(mReceiveInfo.getLocation());
                    mTvReceiveContactInfo.setText(mReceiveInfo.getUserName() + " " + mReceiveInfo.getUserPhone());
                    updateOrderMoney();
                    break;
                case CODE_CHOOSE_GOOD_TYPE:
                    mGoodType = (GoodType) data.getSerializableExtra("type");
                    if (mGoodType != null){
                        mTvGoodType.setText(mGoodType.text);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
