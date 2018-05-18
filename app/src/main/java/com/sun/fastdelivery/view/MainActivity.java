package com.sun.fastdelivery.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
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
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.sun.fastdelivery.R;
import com.sun.fastdelivery.utils.ToastUtils;
import com.sun.fastdelivery.utils.UserSpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AMapLocationListener, LocationSource, AMap.OnMapClickListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener {

    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.tvCurrentLocation)
    TextView mTvCurrentLocation;

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

    /**
     * 点击我要下单按钮，跳转到创建订单界面
     */
    @OnClick(R.id.llOrder)
    public void goOrderView(){
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("location", mCurrentLocationText);
        intent.putExtra("latLng", mCurrentLatLng);
        startActivity(intent);
    }

    private long keyDownTime = 0;//返回时记录的系统时间
    private long timeInterval = 2000;//双击退出的时间间隔

    private AMap mMap;//地图控制器
    private UiSettings mUiSettings;//地图UI设置器
    private String mCurrentLocationText;//当前位置的文本表示
    private LatLng mCurrentLatLng;//当前位置的经纬度
    private GeocodeSearch mGeocodeSearch;//高德滴入的地址转换

    /**
     * 定位
     */
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationOption;
    private LocationSource.OnLocationChangedListener mListener;
    private String TAG = getClass().getSimpleName();

    //注销对话框
    private AlertDialog.Builder mLogoutDialog;
    private AlertDialog.Builder mAboutDialog;//关于对话框
    private AlertDialog.Builder mRuleDialog;//配送规则对话框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initMap(savedInstanceState);

        String phone = UserSpUtils.getUser(this).getUserPhone();
        if (!TextUtils.isEmpty(phone)){
            TextView tvPhone = navigationView.getHeaderView(0).findViewById(R.id.tvPhone);
            tvPhone.setText(phone.substring(0, 3) + "****" + phone.substring(phone.length()-4, phone.length()));
        }

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


    /**
     * 开始定位
     */
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            long currentTime = System.currentTimeMillis();
            if (currentTime - keyDownTime < timeInterval){
                finish();
            }else {
                ToastUtils.showToast("再按一次，退出程序");
                keyDownTime = currentTime;
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // TODO: 2018/5/2
            Intent intent = new Intent(this, PayOrderActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            Intent intent = new Intent(this, OrderManagerActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_bike) {

        } else if (id == R.id.nav_rule) {
            if (mRuleDialog == null){
                mRuleDialog = new AlertDialog.Builder(this)
                        .setMessage("配送规则为 每公斤每公里1元，配送费与重量和距离成正比。")
                        .setPositiveButton("明白",null);
            }
            mRuleDialog.show();
        } else if (id == R.id.nav_about) {
            if (mAboutDialog == null){
                mAboutDialog = new AlertDialog.Builder(this)
                        .setMessage("快派 APP 1.0")
                        .setPositiveButton("确定", null);
            }
            mAboutDialog.show();
        } else if (id == R.id.nav_logout) {
            if (mLogoutDialog == null){
                mLogoutDialog = new AlertDialog.Builder(this)
                        .setCancelable(true)
                        .setMessage("确定要退出该账号吗？")
                        .setPositiveButton("注销", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UserSpUtils.logout(MainActivity.this);
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("取消", null);
            }
            mLogoutDialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            Log.i("AMapLocation: ", aMapLocation.toString());
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
//                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见官方定位类型表
//                aMapLocation.getLatitude();//获取纬度
//                aMapLocation.getLongitude();//获取经度
//                aMapLocation.getAccuracy();//获取精度信息
//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(aMapLocation.getTime());
//                df.format(date);//定位时间
//                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                aMapLocation.getCountry();//国家信息
//                aMapLocation.getProvince();//省信息
//                aMapLocation.getCity();//城市信息
//                aMapLocation.getDistrict();//城区信息
//                aMapLocation.getStreet();//街道信息
//                aMapLocation.getStreetNum();//街道门牌号信息
//                aMapLocation.getCityCode();//城市编码
//                aMapLocation.getAdCode();//地区编码

                //设置缩放级别
//                    aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
//                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                //点击定位按钮 能够将地图的中心移动到定位点
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
            }else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Log.d(TAG, "定位失败");
            }
        }
    }

    /**
     * 激化定位
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mLocationClient == null) {
            initLocation();
        }
    }

    /**
     * 结束定位
     */
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
    public void onMapClick(LatLng latLng) {
        Log.d(TAG, latLng.toString());
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        Log.d(TAG, "onCameraChange: " + cameraPosition.toString());
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition.target, 17));
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
        mCurrentLocationText = address.getFormatAddress();
        mTvCurrentLocation.setText(address.getFormatAddress());
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
