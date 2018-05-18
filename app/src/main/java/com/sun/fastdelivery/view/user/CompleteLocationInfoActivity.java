package com.sun.fastdelivery.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.adapter.LocationListAdapter;
import com.sun.fastdelivery.bean.City;
import com.sun.fastdelivery.bean.DispatchInformation;
import com.sun.fastdelivery.utils.MyTextUtils;
import com.sun.fastdelivery.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompleteLocationInfoActivity extends AppCompatActivity implements LocationSource, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, AMapLocationListener {

    private int CODE_CHOOSE_CITY = 11;

    @BindView(R.id.tvCityTitle)
    TextView mTvCityTitle;
    @BindView(R.id.tvCurrentLocation)
    TextView mTvCurrentLocation;
    @BindView(R.id.etAddressInfo)
    EditText mEtAddressInfo;
    @BindView(R.id.etName)
    EditText mEtName;
    @BindView(R.id.etPhone)
    EditText mEtPhone;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.lvNearbyLocation)
    ListView mLvNearbyLocation;//附件地址列表

    @OnClick(R.id.ivBack)
    public void goBack(){
        finish();
    }

    @OnClick(R.id.tvCityTitle)
    public void chooseCity(){
        Intent intent = new Intent(this, ChooseCityActivity.class);
        startActivityForResult(intent, CODE_CHOOSE_CITY);
    }

    @OnClick(R.id.tvSure)
    public void onSure(){
        if (TextUtils.isEmpty(mCurrentLocationText)){
            ToastUtils.showToast("请选中位置！");
            return;
        }
        String addressInfo = mEtAddressInfo.getText().toString();
        if (TextUtils.isEmpty(addressInfo)){
            ToastUtils.showToast("请先补充地址信息！");
            return;
        }
        String name = mEtName.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtils.showToast("姓名不能为空！");
            return;
        }
        String phone = mEtPhone.getText().toString();
        if (TextUtils.isEmpty(phone)){
            ToastUtils.showToast("联系方式不能为空！");
            return;
        }

        if (!MyTextUtils.isPhoneNumLegitimate(phone)){
            ToastUtils.showToast("手机号码不合理！");
            return;
        }

        DispatchInformation information = new DispatchInformation();
        information.setAddress(addressInfo);
        information.setLocation(mCurrentLocationText);
        information.setUserName(name);
        information.setUserPhone(phone);
        information.setLatLng(mCurrentLatLng);
        Intent data = new Intent();
        data.putExtra(DispatchInformation.TAG, information);
        setResult(RESULT_OK, data);
        finish();
    }

    /**
     * 点击定位按钮，回到当前定位
     */
    @OnClick(R.id.ivLocation)
    public void location(){
        if (mLocationClient != null){
            mLocationClient.startLocation();
        }else {
            Log.e(TAG, "mLocationClient is null!");
        }
    }

    private City mCity;//当前城市
    private AMap mMap;//地图控制器
    private UiSettings mUiSettings;//地图UI设置器
    private String mCurrentLocationText;//当前位置的文本表示
    private LatLng mCurrentLatLng;//当前位置的经纬度
    private GeocodeSearch mGeocodeSearch;//高德滴入的地址转换
    private LocationListAdapter mAdapter;
    private List<PoiItem> mNearbyLocationData = new ArrayList<>();

    /**
     * 定位
     */
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationSource.OnLocationChangedListener mListener;
    private String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_location_info);
        ButterKnife.bind(this);
        initView();
        initMap(savedInstanceState);
    }

    private void initView() {
        mAdapter = new LocationListAdapter(this, mNearbyLocationData);
        mLvNearbyLocation.setAdapter(mAdapter);
        mLvNearbyLocation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiItem item = mAdapter.getItem(position);
                mCurrentLocationText = item.toString();
                mTvCurrentLocation.setText(mCurrentLocationText);
                mCurrentLatLng = new LatLng(item.getLatLonPoint().getLatitude(), item.getLatLonPoint().getLongitude());
            }
        });
    }

    private void initMap(Bundle savedInstanceState) {
        mMapView.onCreate(savedInstanceState);//地图初始化
        mMap = mMapView.getMap();//获取地图控制器
        mUiSettings = mMap.getUiSettings();
        mMap.setLocationSource(this);//通过aMap对象设置定位数据源的监听

//        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮

        mMap.moveCamera(CameraUpdateFactory.zoomTo(17));

        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.showMyLocation(false);
        mMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setAllGesturesEnabled(true);
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);//设置logo位置
        mUiSettings.setZoomControlsEnabled(false);
        mMap.setOnCameraChangeListener(this);

        //进行坐标转换
        mGeocodeSearch = new GeocodeSearch(this);
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        if (mLocationClient != null){
            mLocationClient.onDestroy();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_CHOOSE_CITY && resultCode == RESULT_OK && data != null){
            mCity = (City) data.getSerializableExtra(City.TAG);
            if (mCity != null && !TextUtils.isEmpty(mCity.getName())){
                mTvCityTitle.setText(mCity.getName());
                //将地图中心移到选中的城市中
                LatLng latLng = new LatLng(mCity.getLat(), mCity.getLng());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(this);
        //设置定位回调监听，这里要实现AMapLocationListener接口，AMapLocationListener接口只有onLocationChanged方法可以实现，用于接收异步返回的定位结果，参数是AMapLocation类型。
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(true);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
//        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            initLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        LatLonPoint latLonPoint = new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude);
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,GeocodeSearch.AMAP);
        mCurrentLatLng = cameraPosition.target;//对当前中心经纬度进行记录
        mGeocodeSearch.getFromLocationAsyn(query);//查询当前地址的地理位置信息
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
        Log.d(TAG, address.getFormatAddress());
        List<PoiItem> list = address.getPois();
        mNearbyLocationData.clear();
        for (PoiItem item: list){
            Log.d(TAG, item.toString());
            mNearbyLocationData.add(item);
        }
        mAdapter.notifyDataSetChanged();
        mCurrentLocationText = address.getFormatAddress();
        mTvCurrentLocation.setText(address.getFormatAddress());
        mTvCityTitle.setText(address.getCity());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            Log.i("AMapLocation: ", aMapLocation.toString());
            if (aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);
                //获取定位信息
                StringBuffer buffer = new StringBuffer();
                buffer.append(aMapLocation.getCountry() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getCity() + ""
                        + aMapLocation.getProvince() + ""
                        + aMapLocation.getDistrict() + ""
                        + aMapLocation.getStreet() + ""
                        + aMapLocation.getStreetNum());
                Log.d(TAG, buffer.toString());
                //然后可以移动到定位点,使用animateCamera就有动画效果
                //取出经纬度
                LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Log.d(TAG, "定位失败");
            }
        }
    }
}
