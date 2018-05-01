package com.sun.fastdelivery.view.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sun.fastdelivery.presenter.BasePresenter;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public abstract class BaseActivity<P extends BasePresenter<V>, V> extends AppCompatActivity {

    protected P mPresenter;//P层对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    protected abstract P createPresenter();//实例化P层对象

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
        mPresenter = null;
    }
}
