package com.sun.fastdelivery.view.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.adapter.GoodTypeListAdapter;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.GoodTypeListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseGoodTypeActivity extends AppCompatActivity {

    @BindView(R.id.gvType)
    GridView mGvGoodType;
    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.btnSure)
    public void onSure(){
        Intent data = new Intent();
        data.putExtra("type", mGoodType);
        setResult(RESULT_OK, data);
        finish();
    }

    private GoodType mGoodType = GoodType.CAKE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_type);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<GoodTypeListBean> list = new ArrayList<>();
        int[] resIds = {R.drawable.cake, R.drawable.flower, R.drawable.meals, R.drawable.fruit,
                        R.drawable.file, R.drawable.computer, R.drawable.clothes, R.drawable.other};
        GoodType[] goodTypes = GoodType.values();
        for (int i = 0; i < resIds.length; ++ i){
            GoodTypeListBean bean = new GoodTypeListBean();
            bean.setPicRes(resIds[i]);
            bean.setType(goodTypes[i]);
            list.add(bean);
        }
        list.get(0).setSelected(true);
        final GoodTypeListAdapter adapter = new GoodTypeListAdapter(this, list);
        mGvGoodType.setAdapter(adapter);
        mGvGoodType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mGoodType = adapter.getItem(position).getType();
                adapter.updateItemSelectStatus(position);
            }
        });
    }
}
