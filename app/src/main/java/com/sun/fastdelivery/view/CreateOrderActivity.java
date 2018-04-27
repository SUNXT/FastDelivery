package com.sun.fastdelivery.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateOrderActivity extends AppCompatActivity {

    @BindView(R.id.tvSendLocationInfo)
    TextView mTvSendLocationInfo;
    @BindView(R.id.tvSendContactInfo)
    TextView mTvSendContactInfo;
    @BindView(R.id.tvReceiveLocationInfo)
    TextView mTvReceiveLocationInfo;
    @BindView(R.id.tvReceiveContactInfo)
    TextView mTvReceiveContactInfo;
    @BindView(R.id.tvWeight)
    TextView mTvWeight;

    private int CODE_SEND_INFO = 11;
    private int CODE_RECEIVE_INFO = 12;

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
     * 改变配送重量
     * @param view
     */
    @OnClick({R.id.ivCut, R.id.ivAdd})
    public void changeWeight(View view){
        if (view.getId() == R.id.ivCut){
            if (mWeight <= 1){
                ToastUtils.showToast("至少1公斤！");
                return;
            }
            mWeight --;
        }else {
            mWeight ++;
        }
        mTvWeight.setText("预计 " + mWeight + " 公斤以下");
    }


    private int mWeight = 1;//重量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        ButterKnife.bind(this);
        initView();
    }


    public void initView() {
        Intent data = getIntent();
        if (data != null){
            mTvSendLocationInfo.setText(data.getStringExtra("location"));
        }
    }
}
