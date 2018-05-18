package com.sun.fastdelivery.view;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.utils.UserSpUtils;
import com.sun.fastdelivery.view.user.LoginActivity;
import com.sun.fastdelivery.view.user.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int splashTime = 1000;//闪屏显示的时间，单位毫秒
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (UserSpUtils.isUserLogin(SplashActivity.this)){
                    intent.setClass(SplashActivity.this, MainActivity.class);
                }else {
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, splashTime);

    }
}
