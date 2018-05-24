package com.sun.fastdelivery.view.rider;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.UserModel;
import com.sun.fastdelivery.presenter.LoginPresenter;
import com.sun.fastdelivery.view.base.BaseActivity;
import com.sun.fastdelivery.view.base.ILoginView;

public class RiderUserMainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_user_main);
    }

}
