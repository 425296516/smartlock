package com.anlida.smartlock.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.anlida.smartlock.R;
import com.anlida.smartlock.adapter.MonitorWarnAdapter;
import com.anlida.smartlock.base.FMFragment;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.model.req.ReqWarnRecord;
import com.anlida.smartlock.model.resp.RespWarnLocation;
import com.anlida.smartlock.model.resp.RespWarnRecord;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.ui.ScanAddDeviceActivity;
import com.anlida.smartlock.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.location.LocationManager.GPS_PROVIDER;

public class HomeFragment extends FMFragment implements AMap.OnMarkerClickListener, AMap.OnMapClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view_result)
    RecyclerView recyclerViewResult;
    @BindView(R.id.map_view)
    MapView mapView;
    @BindView(R.id.btn_device_lock)
    Button btnDeviceLock;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.linear_layout_result)
    LinearLayout linearLayoutResult;
    @BindView(R.id.rl_monitor_warn)
    RelativeLayout rlMonitorWarn;
    @BindView(R.id.rl_unlock)
    RelativeLayout rlUnlock;

    private AMap aMap;
    private GeocodeSearch geocoderSearch;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private static final int REQUEST_ACCESS_FINE_LOCATION = 123;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mapView.onCreate(savedInstanceState);// 此方法必须重写
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {

        checkLocationPermission();

        initGPS();

        init();

        initRecyclerView();

    }

    MonitorWarnAdapter warnRecordAdapter, wrAdapterResult;

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        warnRecordAdapter = new MonitorWarnAdapter(getActivity(), 1);//默认创建一个数组，不创建会有空指针异常
        recyclerView.setAdapter(warnRecordAdapter);

        LinearLayoutManager llmResult = new LinearLayoutManager(getActivity());
        recyclerViewResult.setLayoutManager(llmResult);
        wrAdapterResult = new MonitorWarnAdapter(getActivity(), 2);//默认创建一个数组，不创建会有空指针异常
        recyclerViewResult.setAdapter(wrAdapterResult);

        getWarningRecord(1, 10);
        getWarningLocation();
    }

    private void getWarningRecord(int pageNum, int pageSize) {
        ReqWarnRecord reqDeviceManager = new ReqWarnRecord(pageNum, pageSize);
        HttpClient.getInstance().service.getWarningRecord(reqDeviceManager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respWarnRecord) {
                        if(respWarnRecord.getData().getList().size()>0) {
                            rlMonitorWarn.setVisibility(View.VISIBLE);
                            rlUnlock.setVisibility(View.VISIBLE);
                            List<RespWarnRecord.DataBean.ListBean> listBeans = new ArrayList<>();
                            List<RespWarnRecord.DataBean.ListBean> dealBeans = new ArrayList<>();

                            for (int i = 0; i < respWarnRecord.getData().getList().size(); i++) {
                                if ("1".equals(respWarnRecord.getData().getList().get(i).getStatus())) {//status 1未处理 2已处理
                                    listBeans.add(respWarnRecord.getData().getList().get(i));
                                } else if ("2".equals(respWarnRecord.getData().getList().get(i).getStatus())) {
                                    dealBeans.add(respWarnRecord.getData().getList().get(i));
                                }
                            }
                            warnRecordAdapter.setData(listBeans);
                            wrAdapterResult.setData(dealBeans);
                        }else {
                            rlMonitorWarn.setVisibility(View.GONE);
                            rlUnlock.setVisibility(View.GONE);
                        }
                    }
                });
    }


    private void getWarningLocation() {
        HttpClient.getInstance().service.getWarningLocation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnLocation>() {
                    @Override
                    public void onNext(RespWarnLocation respWarnLocation) {
                        for (int i = 0; i < respWarnLocation.getData().size(); i++) {
                            addCarMarkerToMap(respWarnLocation.getData().get(i).getLatitude(), respWarnLocation.getData().get(i).getLongitude());
                        }
                    }
                });
    }

    /**
     * 在地图上添加的marker
     */
    private void addCarMarkerToMap(double latitude, double longitude) {
        if (isAdded()) {
            MarkerOptions markerOption = new MarkerOptions()
                    .title("我的锁扣")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.img_1))
                    .position(new LatLng(latitude, longitude))
                    .snippet("1")
                    .draggable(true);

            Marker carMarker = aMap.addMarker(markerOption);
            carMarker.setObject("0");

            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 14f));
        }
    }

    @OnClick({R.id.btn_device_lock})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_device_lock:

                startActivity(new Intent(context, ScanAddDeviceActivity.class));

                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {   // 不在最前端显示 相当于调用了onPause();
            mapView.setVisibility(View.GONE);
            return;
        } else {  // 在最前端显示 相当于调用了onResume();
            mapView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initView(View rootView) {

    }


    @Override
    protected String description() {
        return "";
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }

    // 权限判断
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 申请READ_CONTACTS权限
            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestLocation();
            } else {
                ToastUtils.show(getActivity(), getString(R.string.pic_clipimg_permission));
            }
        }
    }

    public void requestLocation() {
        if (aMap != null && aMap.getMyLocation() != null && aMap.getMyLocation().getLatitude() != 0) {
            aMap.setMyLocationEnabled(true);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(aMap.getMyLocation().getLatitude(),
                    aMap.getMyLocation().getLongitude()), 400f));
        }
    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setScaleControlsEnabled(false);//缩放比例尺
        aMap.getUiSettings().setRotateGesturesEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);//默认缩放按钮
        aMap.getUiSettings().setLogoBottomMargin(-50);//隐藏logo

        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种

        setupLocationStyle();

        aMap.setOnMarkerClickListener(this);

        aMap.setOnMapClickListener(this);

        geocoderSearch = new GeocodeSearch(getActivity());

    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location));
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.radiusFillColor(getResources().getColor(android.R.color.transparent));
        myLocationStyle.strokeColor(getResources().getColor(android.R.color.transparent));

        aMap.setMyLocationStyle(myLocationStyle);
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }


    /**
     * Marker 点击回调
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        Object obj = marker.getObject();
        moveCameraToLocate(marker.getPosition().latitude, marker.getPosition().longitude, 17);

        if (obj == null) {
            marker.setTitle("我的位置");
        }
        return true;// false时，点击之后，marker移动至地图中心
    }

    private void moveCameraToLocate(double lat, double lng, int zoom) {
        CameraPosition cameraPosition = new CameraPosition(new LatLng(lat, lng), zoom, 0, 0);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.animateCamera(cameraUpdate, 1000, null);
    }

    public double getDistance(double startLat, double startLon, double endLat, double endLon) {
        double poiDistance = AMapUtils.calculateLineDistance(new LatLng(startLat, startLon),
                new LatLng(endLat, endLon));
        return poiDistance;
    }

    @OnClick({R.id.btn_device_lock,})
    public void onViewClicked(View view) {

        switch (view.getId()) {

            case R.id.btn_device_lock://搜索


                break;

        }
    }


    private void initGPS() {
        LocationManager mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        // 判断GPS模块是否开启，如果没有则开启
        if (!mLocationManager.isProviderEnabled(GPS_PROVIDER)) {
            Toast.makeText(getActivity(), "请打开GPS", Toast.LENGTH_SHORT).show();
            final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_LIGHT);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为方便获取您的位置，请先打开GPS");
            dialog.setPositiveButton("设置", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    // 转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(getContext(), "打开后直接点击返回键即可，若不打开返回下次将再次出现", Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
                }
            });
            dialog.setNeutralButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();
        }
    }


    //获取开始和结束的地址
    public void getAddress(double lat, double lon, TextView poiPlaceName) {
        new Thread() {

            @Override
            public void run() {
                super.run();

                LatLonPoint point = new LatLonPoint(lat, lon);
                RegeocodeQuery query = new RegeocodeQuery(point, 200,
                        GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                try {
                    RegeocodeAddress result = geocoderSearch.getFromLocation(query);// 设置同步逆地理编码请求

                    getActivity().runOnUiThread(() -> {
                        poiPlaceName.setText(result.getFormatAddress());
                    });

                } catch (AMapException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }


    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }

        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

}
