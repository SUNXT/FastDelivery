package com.sun.fastdelivery.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.City;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteLocationInfoActivity extends AppCompatActivity {

    private int CODE_CHOOSE_CITY = 11;

    @BindView(R.id.tvCityTitle)
    TextView mTvCityTitle;
    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.tvCityTitle)
    public void chooseCity(){
        Intent intent = new Intent(this, ChooseCityActivity.class);
        startActivityForResult(intent, CODE_CHOOSE_CITY);
    }

    private City mCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_location_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_CHOOSE_CITY && resultCode == RESULT_OK && data != null){
            mCity = (City) data.getSerializableExtra(City.TAG);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
