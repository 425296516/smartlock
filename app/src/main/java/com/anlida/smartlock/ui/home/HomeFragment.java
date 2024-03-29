package com.anlida.smartlock.ui.home;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.anlida.smartlock.R;
import com.anlida.smartlock.adapter.MonitorWarnAdapter;
import com.anlida.smartlock.adapter.UnLockAdapter;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.event.UpdateWarnRecord;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqAirQuality;
import com.anlida.smartlock.model.req.ReqUnLockList;
import com.anlida.smartlock.model.req.ReqWarnRecord;
import com.anlida.smartlock.model.resp.RespCityLocation;
import com.anlida.smartlock.model.resp.RespRemoteToken;
import com.anlida.smartlock.model.resp.RespWarnLocation;
import com.anlida.smartlock.model.resp.RespWarnRecord;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.ui.ScanAddDeviceActivity;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.location.LocationManager.GPS_PROVIDER;

public class HomeFragment extends LazyLoadFragment implements AMap.OnMarkerClickListener, AMap.OnMapClickListener {

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
    @BindView(R.id.rl_please_monitor_warn)
    RelativeLayout rlPleaseMonitorWarn;
    @BindView(R.id.rl_monitor_warn)
    RelativeLayout rlMonitorWarn;
    @BindView(R.id.rl_unlock)
    RelativeLayout rlUnlock;
    @BindView(R.id.rl_please_unlock)
    RelativeLayout rlPleaseUnlock;
    @BindView(R.id.iv_left_right)
    ImageView ivLeftRight;
    @BindView(R.id.iv_top_bottom)
    ImageView ivTopBottom;

    @BindView(R.id.linearLayout_unlock)
    LinearLayout linearLayoutUnlock;
    @BindView(R.id.recycler_view_unlock)
    RecyclerView recyclerViewUnlock;
    @BindView(R.id.linear_layout_unlock_result)
    LinearLayout linearLayoutUnlockResult;
    @BindView(R.id.recycler_view_unlock_result)
    RecyclerView recyclerViewUnlockResult;
    @BindView(R.id.tv_no_please_data)
    TextView tvNoPleaseData;
    @BindView(R.id.tv_no_deal_data)
    TextView tvNoDealData;
    @BindView(R.id.tv_no_warn_data)
    TextView tvNoWarnData;
    @BindView(R.id.tv_no_result_data)
    TextView tvNoResultData;
    @BindView(R.id.tv_all_select)
    TextView tvAllSelect;
    @BindView(R.id.btn_refresh_data)
    Button btnRefreshData;

    private AMap aMap;

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
    public void initView(View rootView) {


        initRecyclerView();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void loadData() {
        checkLocationPermission();

        initGPS();

        init();

        getWarningRecord(1, 300);
        getDeviceLocation();
        getWarningLocation();

        getUnDeal(1, 300);
        getHaveDeal(1, 300);
    }

    @Override
    protected boolean isNeedReload() {
        return true;
    }

    @Override
    public void initData() {
        airquality();
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

    MonitorWarnAdapter warnRecordAdapter, wrAdapterResult;
    UnLockAdapter unLockAdapter, unLockResultAdapter;

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        warnRecordAdapter = new MonitorWarnAdapter(getActivity(), 1);//默认创建一个数组，不创建会有空指针异常
        recyclerView.setAdapter(warnRecordAdapter);

        LinearLayoutManager llmResult = new LinearLayoutManager(getActivity());
        recyclerViewResult.setLayoutManager(llmResult);
        wrAdapterResult = new MonitorWarnAdapter(getActivity(), 2);//默认创建一个数组，不创建会有空指针异常
        recyclerViewResult.setAdapter(wrAdapterResult);

        LinearLayoutManager unLockllm = new LinearLayoutManager(getActivity());
        recyclerViewUnlock.setLayoutManager(unLockllm);
        unLockAdapter = new UnLockAdapter(getActivity(), 1);//默认创建一个数组，不创建会有空指针异常
        recyclerViewUnlock.setAdapter(unLockAdapter);

        LinearLayoutManager unLockllmResult = new LinearLayoutManager(getActivity());
        recyclerViewUnlockResult.setLayoutManager(unLockllmResult);
        unLockResultAdapter = new UnLockAdapter(getActivity(), 2);//默认创建一个数组，不创建会有空指针异常
        recyclerViewUnlockResult.setAdapter(unLockResultAdapter);

        tvAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvAllSelect.isSelected()) {
                    tvAllSelect.setSelected(false);
                } else {
                    tvAllSelect.setSelected(true);
                }
                unLockAdapter.setAllSelect(tvAllSelect.isSelected());
            }
        });

    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateWarnRecord event) {
        getWarningRecord(1, 300);
    }


    private void getWarningRecord(int pageNum, int pageSize) {
        ReqWarnRecord reqDeviceManager = new ReqWarnRecord(pageNum, pageSize, DataWarehouse.getUserId());
        HttpClient.getInstance().service.getWarningRecord(reqDeviceManager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respWarnRecord) {
                        if (respWarnRecord.getData().getList().size() > 0) {
                            //rlMonitorWarn.setVisibility(View.VISIBLE);

                            List<RespWarnRecord.DataBean.ListBean> listBeans = new ArrayList<>();
                            List<RespWarnRecord.DataBean.ListBean> dealBeans = new ArrayList<>();

                            for (int i = 0; i < respWarnRecord.getData().getList().size(); i++) {
                                if ("1".equals(respWarnRecord.getData().getList().get(i).getStatus())) {//status 1未处理 2已处理
                                    listBeans.add(respWarnRecord.getData().getList().get(i));
                                } else if ("2".equals(respWarnRecord.getData().getList().get(i).getStatus())) {
                                    dealBeans.add(respWarnRecord.getData().getList().get(i));
                                }
                            }

                            if (listBeans.size() == 0) {
                                tvNoWarnData.setVisibility(View.VISIBLE);
                                warnRecordAdapter.setData(listBeans);
                            } else {
                                rlMonitorWarn.setVisibility(View.VISIBLE);
                                tvNoWarnData.setVisibility(View.GONE);
                                warnRecordAdapter.setData(listBeans);
                            }

                            if (dealBeans.size() == 0) {
                                tvNoResultData.setVisibility(View.VISIBLE);
                                wrAdapterResult.setData(dealBeans);
                            } else {
                                tvNoResultData.setVisibility(View.GONE);
                                wrAdapterResult.setData(dealBeans);
                            }
                        } else {
                            //rlMonitorWarn.setVisibility(View.GONE);
                            ivLeftRight.setImageResource(R.drawable.btn_left);

                            tvNoWarnData.setVisibility(View.VISIBLE);
                            tvNoResultData.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void getUnDeal(int pageNum, int pageSize) {
        HttpClient.getInstance().service.getUnlockList(new ReqUnLockList(pageNum, pageSize, DataWarehouse.getUserId(), 3))//status 3未处理 4已处理
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respWarnRecord) {
                        if (0 == respWarnRecord.getCode() && respWarnRecord.getData().getList().size() > 0) {
                            rlUnlock.setVisibility(View.VISIBLE);
                            tvNoPleaseData.setVisibility(View.GONE);
                            unLockAdapter.setData(respWarnRecord.getData().getList());
                            unLockAdapter.setAllSelect(false);

                        } else {
                            tvNoPleaseData.setVisibility(View.VISIBLE);
                            unLockAdapter.setData(null);
                        }
                    }
                });
    }


    private void getHaveDeal(int pageNum, int pageSize) {
        HttpClient.getInstance().service.getUnlockList(new ReqUnLockList(pageNum, pageSize, DataWarehouse.getUserId(), 4))//status 3未处理 4已处理
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respWarnRecord) {
                        if (0 == respWarnRecord.getCode() && respWarnRecord.getData().getList().size() > 0) {
                            tvNoDealData.setVisibility(View.GONE);
                            unLockResultAdapter.setData(respWarnRecord.getData().getList());
                        } else {
                            tvNoDealData.setVisibility(View.VISIBLE);
                            unLockResultAdapter.setData(null);
                            ivTopBottom.setImageResource(R.drawable.btn_top);
                        }
                    }
                });
    }


    private void getRemoteToken() {
        DataWarehouse.setAccessToken("Basic Ymxlc3NlZDpibGVzc2Vk");
        HttpClient.getInstance(HttpClient.BASE_URL_CONTROL).service.getRemoteToken("password", "admin", "admin", "all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespRemoteToken>() {
                    @Override
                    public void onNext(RespRemoteToken respRemoteToken) {
                        DataWarehouse.setAccessToken("Bearer " + respRemoteToken.getAccess_token());
                        deviceLock();
                        unlockDevice();
                    }
                });
    }

    private void airquality() {
        HttpClient.getInstance("http://api.map.baidu.com/geocoder/").service.getLocationCity("nuqaURQzdHFbhKaMNX6P9La7EYFmaeWc", DataWarehouse.getLatitude() + "," + DataWarehouse.getLongitude(), "json", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespCityLocation>() {
                    @Override
                    public void onNext(RespCityLocation respCityLocation) {
                        HttpClient.getInstance().service.airquality(new ReqAirQuality(DataWarehouse.getUserId(), respCityLocation.getResult().getAddressComponent().getCity(),DataWarehouse.getLatitude()+"" , ""+DataWarehouse.getLongitude()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new FMSubscriber<HttpResult>() {
                                    @Override
                                    public void onNext(HttpResult httpResult) {

                                    }
                                });
                    }
                });

    }

    private void deviceLock() {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<RespWarnRecord.DataBean.ListBean> list = unLockAdapter.getSelectList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWarningType().equals("请求上锁")) {
                stringBuffer.append("," + list.get(i).getImei());
            }
        }

        if (stringBuffer.length() > 0) {
            HttpClient.getInstance(HttpClient.BASE_URL_CONTROL).service.deviceLocks(DataWarehouse.getUserId(), "O33", stringBuffer.toString().substring(1, stringBuffer.length()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FMSubscriber<HttpResult>() {
                        @Override
                        public void onNext(HttpResult httpResult) {
                            if ("200".equals(httpResult.getCode())) {
                                ToastUtils.show(context, "操作执行成功");
                            } else {
                                ToastUtils.show(context, "上锁失败");
                            }
                        }
                    });
        }
    }


    private void unlockDevice() {
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList<RespWarnRecord.DataBean.ListBean> list = unLockAdapter.getSelectList();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getWarningType().equals("请求解锁")) {
                stringBuffer.append("," + list.get(i).getImei());
            }
        }
        if (stringBuffer.length() > 0) {
            HttpClient.getInstance(HttpClient.BASE_URL_CONTROL).service.deviceunLock(DataWarehouse.getUserId(),"S44", stringBuffer.toString().substring(1, stringBuffer.length()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new FMSubscriber<HttpResult>() {
                        @Override
                        public void onNext(HttpResult httpResult) {
                            if ("200".equals(httpResult.getCode())) {
                                //DialogUtil.showDeviceUnLock(getActivity());
                                ToastUtils.show(context, "操作执行成功");

                                getUnDeal(1, 300);
                                getHaveDeal(1, 300);
                            } else {
                                ToastUtils.show(context, "解锁失败");
                            }
                        }
                    });
        }
    }

    private void getDeviceLocation() {
        HttpClient.getInstance().service.getDeviceLocation(DataWarehouse.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnLocation>() {
                    @Override
                    public void onNext(RespWarnLocation respWarnLocation) {
                        if (respWarnLocation != null && !TextUtils.isEmpty(respWarnLocation.getMsg())) {
                            new Handler().postDelayed(new Runnable() {//目的是替换定位的
                                @Override
                                public void run() {
                                    for (int i = 0; i < respWarnLocation.getData().size(); i++) {
                                        if (respWarnLocation.getData().get(i).getLatitude() > 0 && respWarnLocation.getData().get(i).getLongitude() > 0) {
                                            addMarkerToMap(respWarnLocation.getData().get(i), 2);
                                        }
                                    }
                                }
                            }, 500);
                        }
                    }
                });
    }

    private void getWarningLocation() {
        HttpClient.getInstance().service.getWarningLocation(DataWarehouse.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnLocation>() {
                    @Override
                    public void onNext(RespWarnLocation respWarnLocation) {
                        new Handler().postDelayed(new Runnable() {//目的是替换定位的
                            @Override
                            public void run() {
                                for (int i = 0; i < respWarnLocation.getData().size(); i++) {
                                    if (respWarnLocation.getData().get(i).getLatitude() > 0 && respWarnLocation.getData().get(i).getLongitude() > 0) {
                                        addMarkerToMap(respWarnLocation.getData().get(i), 1);
                                    }
                                }
                            }
                        }, 500);
                    }
                });
    }


    /**
     * 在地图上添加的marker
     */
    private void addMarkerToMap(RespWarnLocation.DataBean dataBean, int type) {
        if (isAdded()) {
            MarkerOptions markerOption = new MarkerOptions()
                    .title("我的锁扣")
                    .position(new LatLng(dataBean.getLatitude(), dataBean.getLongitude()))
                    .snippet("1")
                    .draggable(true);
            if (type == 1) {
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.img_2));
            } else {
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.img_gps_green));
            }
            Marker carMarker = aMap.addMarker(markerOption);
            carMarker.setObject(dataBean);
            mMarkerList.add(carMarker);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dataBean.getLatitude(), dataBean.getLongitude()), 14f));
        }
    }


    @OnClick({R.id.btn_refresh_data, R.id.btn_unlock, R.id.btn_device_lock, R.id.iv_top_bottom, R.id.iv_left_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {

            case R.id.btn_refresh_data:

                getWarningRecord(1, 300);
                getDeviceLocation();
                getWarningLocation();

                getUnDeal(1, 300);
                getHaveDeal(1, 300);

                break;

            case R.id.btn_unlock:

                if (unLockAdapter.getSelectList().size() == 0) {
                    ToastUtils.show(context, "请选择解锁锁设备");
                } else {
                    getRemoteToken();
                }

                break;

            case R.id.btn_device_lock:

                startActivity(new Intent(context, ScanAddDeviceActivity.class));

                break;

            case R.id.iv_top_bottom:

                if (rlUnlock.getVisibility() == View.VISIBLE) {
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.unlock_hide);
                    rlPleaseUnlock.startAnimation(animation);

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {

                            rlUnlock.setVisibility(View.GONE);
                            ivTopBottom.setImageResource(R.drawable.btn_top);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    rlUnlock.setVisibility(View.VISIBLE);
                    rlPleaseUnlock.setVisibility(View.VISIBLE);
                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.unlock_show);
                    rlPleaseUnlock.startAnimation(animation);

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ivTopBottom.setImageResource(R.drawable.btn_bottom);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }

                break;

            case R.id.iv_left_right:

                if (rlMonitorWarn.getVisibility() == View.VISIBLE) {
                    rlPleaseMonitorWarn.setVisibility(View.GONE);

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.monitor_warn_hide);
                    rlPleaseMonitorWarn.startAnimation(animation);

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            rlPleaseMonitorWarn.setVisibility(View.VISIBLE);
                            rlMonitorWarn.setVisibility(View.GONE);
                            ivLeftRight.setImageResource(R.drawable.btn_left);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else {
                    rlMonitorWarn.setVisibility(View.VISIBLE);
                    rlPleaseMonitorWarn.setVisibility(View.VISIBLE);

                    Animation animation = AnimationUtils.loadAnimation(context, R.anim.monitor_warn_show);
                    rlPleaseMonitorWarn.startAnimation(animation);

                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            ivLeftRight.setImageResource(R.drawable.btn_right);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                }

                break;
        }
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
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    //申请权限回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults!=null && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
                    aMap.getMyLocation().getLongitude()), 100f));

            airquality();
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

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            View infoWindow = View.inflate(context, R.layout.map_window_info, null);

            @Override
            public View getInfoWindow(Marker marker) {
                if (marker.getObject() instanceof RespWarnLocation.DataBean) {
                    if (infoWindow == null) {
                        infoWindow = View.inflate(context, R.layout.map_window_info, null);
                    }
                    RespWarnLocation.DataBean dataBean = (RespWarnLocation.DataBean) marker.getObject();
                    TextView tvName = infoWindow.findViewById(R.id.tv_name);
                    TextView tvException = infoWindow.findViewById(R.id.tv_exception);
                    TextView tvPhone = infoWindow.findViewById(R.id.tv_phone);
                    TextView tvIdcard = infoWindow.findViewById(R.id.tv_idcard);
                    TextView tvBloodType = infoWindow.findViewById(R.id.tv_blood_type);
                    TextView tvDeviceId = infoWindow.findViewById(R.id.tv_device_id);

                    tvName.setText("工人姓名：" + dataBean.getUname());
                    tvException.setText("异常原因：" + dataBean.getWarningType());
                    tvPhone.setText("手机号码：" + dataBean.getPhone());
                    tvIdcard.setText("身份证号：" + dataBean.getIdCard());
                    tvBloodType.setText("血型：" + dataBean.getBloodType());
                    tvDeviceId.setText("设备号：" + dataBean.getImei());

                    return infoWindow;
                }
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });

    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle() {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.icon_my_location));
        //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.radiusFillColor(getResources().getColor(android.R.color.transparent));
        myLocationStyle.strokeColor(getResources().getColor(android.R.color.transparent));

        aMap.setMyLocationStyle(myLocationStyle);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        for (Marker marker : mMarkerList) {
            if (marker != null && marker.getObject() != null && marker.getObject() instanceof RespWarnLocation.DataBean) {
                marker.hideInfoWindow();
            }
        }
    }

    /**
     * Marker 点击回调
     *
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        RespWarnLocation.DataBean dataBean = null;
        if (marker.getObject() instanceof RespWarnLocation.DataBean) {
            dataBean = (RespWarnLocation.DataBean) marker.getObject();
            showMarkerPopupWindow(dataBean);
        }
        //moveCameraToLocate(marker.getPosition().latitude, marker.getPosition().longitude, 17);
        return true;// false时，点击之后，marker移动至地图中心
    }

    private List<Marker> mMarkerList = new ArrayList<>();

    //弹出选择
    private void showMarkerPopupWindow(RespWarnLocation.DataBean dataBean) {

        for (Marker marker : mMarkerList) {
            if (marker != null && marker.getObject() != null && marker.getObject().equals(dataBean)) {
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(marker.getPosition().latitude,
                        marker.getPosition().longitude), 14f));
                marker.showInfoWindow();
            } else {
            }
        }
    }

    private void moveCameraToLocate(double lat, double lng, int zoom) {
        CameraPosition cameraPosition = new CameraPosition(new LatLng(lat, lng), zoom, 0, 0);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        aMap.animateCamera(cameraUpdate, 1000, null);
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
