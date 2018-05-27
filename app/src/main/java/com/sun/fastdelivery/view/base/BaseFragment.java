package com.sun.fastdelivery.view.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sun.fastdelivery.presenter.BasePresenter;

/**
 * Created by sunxuedian on 2018/5/26.
 */

public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {

    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
