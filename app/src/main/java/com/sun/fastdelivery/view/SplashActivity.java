package com.sun.fastdelivery.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.rider.RiderLoginActivity;
import com.sun.fastdelivery.view.rider.RiderUserMainActivity;
import com.sun.fastdelivery.view.user.UserLoginActivity;
import com.sun.fastdelivery.view.user.UserMainActivity;

public class SplashActivity extends AppCompatActivity {

    // TODO: 2018/5/26 不同端APP的切换入口
    private boolean isRider = false;//开关，如果是骑手端，需要将其设置为true，同时将applicationId改为骑手端的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int splashTime = 1000;//闪屏显示的时间，单位毫秒

        TextView textView = (TextView) findViewById(R.id.text);
        if (isRider){
            textView.setText("快派-骑手 APP\nV1.0");
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (isRider){
                    //如果是骑手端的话，判断是否已经登录，如果没有登录，跳转到登录界面
                    if (UserSpUtils.isRiderUserLogin(SplashActivity.this)){
                        intent.setClass(SplashActivity.this, RiderUserMainActivity.class);
                    }else {
                        intent.setClass(SplashActivity.this, RiderLoginActivity.class);
                    }

                }else {
                    //用户端，判断用户是否已经登录，没有登录的话，需要调整到登录页面
                    if (UserSpUtils.isUserLogin(SplashActivity.this)){
                        intent.setClass(SplashActivity.this, UserMainActivity.class);
                    }else {
                        intent.setClass(SplashActivity.this, UserLoginActivity.class);
                    }
                }
                startActivity(intent);
                finish();
            }
        }, splashTime);

    }
}
