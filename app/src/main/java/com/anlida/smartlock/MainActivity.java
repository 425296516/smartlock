package com.anlida.smartlock;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.anlida.smartlock.adapter.TabPagerAdapter;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.ui.DeviceManagerFragment;
import com.anlida.smartlock.ui.RepairFragment;
import com.anlida.smartlock.ui.WarningRecordFragment;
import com.anlida.smartlock.ui.home.HomeFragment;
import com.anlida.smartlock.ui.user.UserFragment;
import com.anlida.smartlock.utils.ToastUtils;
import com.anlida.smartlock.widget.TabViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class MainActivity extends FMActivity implements ViewPager.OnPageChangeListener {

    private static final int TAB_CAR = 0, TAB_LOCATE = 1,
            TAB_RANKING = 2, TAB_REPAIR = 3,TAB_USER = 4;
    private static final String PARAM_TAG = "TAG";

    private List<Fragment> tabs = new ArrayList<>(5);
    private long latestMillis;
    private int currentTab;

    @BindView(R.id.tv_car)
    TextView tvCar;
    @BindView(R.id.tv_locate)
    TextView tvLocate;
    @BindView(R.id.tv_ranking)
    TextView tvRanking;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.tv_user)
    TextView tvUser;
    @BindView(R.id.pager)
    TabViewPager pager;


    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initData(Bundle bundle) {
        tabs.add(new HomeFragment());
        tabs.add(new DeviceManagerFragment());
        tabs.add(new WarningRecordFragment());
        tabs.add(new RepairFragment());
        tabs.add(new UserFragment());
        pager.setOffscreenPageLimit(tabs.size());
        pager.addOnPageChangeListener(this);
        pager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), tabs));
        if (bundle == null) {
            pager.setCurrentItem(0);
            changeState(TAB_CAR);
        } else {
            int restoreTab = bundle.getInt(PARAM_TAG, 0);
            pager.setCurrentItem(restoreTab);
            changeState(restoreTab);
        }
    }

    public void initView(Bundle bundle) {
        tvCar.setOnClickListener(v -> pager.setCurrentItem(TAB_CAR));
        tvLocate.setOnClickListener(v -> pager.setCurrentItem(TAB_LOCATE));
        tvRanking.setOnClickListener(v -> pager.setCurrentItem(TAB_RANKING));
        tvRepair.setOnClickListener(v -> pager.setCurrentItem(TAB_REPAIR));
        tvUser.setOnClickListener(v -> {
            pager.setCurrentItem(TAB_USER);
        });
        checkLocationPermission();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(PARAM_TAG, currentTab);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changeState(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void changeState(int tab) {
        currentTab = tab;
        tvCar.setSelected(TAB_CAR == tab);
        tvLocate.setSelected(TAB_LOCATE == tab);
        tvRanking.setSelected(TAB_RANKING == tab);
        tvRepair.setSelected(TAB_REPAIR == tab);
        tvUser.setSelected(TAB_USER == tab);
    }


    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - latestMillis > 2000) {
            latestMillis = System.currentTimeMillis();
            ToastUtils.show(this, getString(R.string.toast_exit));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected String description() {
        return null;
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }

    // 权限判断
    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // ACCESS_FINE_LOCATION
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    public static final int REQUEST_ACCESS_FINE_LOCATION = 409;
   /* //申请权限回调
    @Override
    public void
    (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                ToastUtils.show(this, getString(R.string.pic_clipimg_permission));
            }
        }
    }*/
}
