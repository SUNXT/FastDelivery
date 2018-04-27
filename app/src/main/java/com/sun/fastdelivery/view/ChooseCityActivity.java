package com.sun.fastdelivery.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.City;
import com.sun.fastdelivery.utils.ChooseCityToJs;
import com.sun.fastdelivery.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseCityActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView mWebView;
    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        WebSettings webSettings = mWebView.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        ChooseCityToJs chooseCityToJs = new ChooseCityToJs();
        chooseCityToJs.setOnChooseListener(new ChooseCityToJs.OnChooseListener() {
            @Override
            public void onChoose(City city) {
                ToastUtils.showToast(city.toString());
            }
        });
        mWebView.addJavascriptInterface(chooseCityToJs, "chooseCityToJs");//调用安卓的方法

        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
        mWebView.loadUrl("file:///android_asset/city_choose.html");
    }


}
