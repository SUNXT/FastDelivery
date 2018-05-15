package com.sun.fastdelivery.view;

import android.content.DialogInterface;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sun.fastdelivery.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayOrderActivity extends AppCompatActivity {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    AlertDialog.Builder mExitDialog;
    @OnClick(R.id.ivBack)
    public void goBack(){
        if (mExitDialog == null){
            mExitDialog = new AlertDialog.Builder(this)
                    .setTitle("取消支付")
                    .setMessage("您确定取消支付吗？")
                    .setPositiveButton("返回", null)
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
        }
        mExitDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTvTitle.setText("支付订单");

    }

    @Override
    public void onBackPressed() {
        goBack();
    }
}
