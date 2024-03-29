package com.anlida.smartlock.ui;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.adapter.DeviceManagerAdapter;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.event.UpdateDeviceManager;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqDeviceManager;
import com.anlida.smartlock.model.resp.RespDeviceManager;
import com.anlida.smartlock.model.resp.RespRemoteToken;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DeviceManagerFragment extends LazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.btn_device_lock)
    Button btnDeviceLock;
    @BindView(R.id.tv_all_select)
    TextView tvAllSelect;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;
    @BindView(R.id.tv_add_device_person)
    TextView tvAddDevicePerson;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @Override
    protected String description() {
        return "";
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_device_manager;
    }

    @Override
    public void initData() {
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setCursorVisible(true);
            }
        });

        initRecyclerView();

    }

    @Override
    protected void onRightClick() {

    }

    @OnClick({R.id.btn_refresh_data,R.id.tv_search, R.id.btn_device_lock, R.id.tv_add_device_person, R.id.tv_all_select})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_refresh_data:

                getDeviceManager(1, 300);

                break;

            case R.id.tv_search:

                getSearchDeviceManager(etSearch.getText().toString());

                break;

            case R.id.btn_device_lock:

                if (deviceManagerAdapter.getSelectList().size() == 0) {
                    ToastUtils.show(context, "请选择上锁设备");
                } else {
                    getRemoteToken();
                }

                break;

            case R.id.tv_add_device_person:

                startActivity(new Intent(context, ScanAddDeviceActivity.class));

                break;

            case R.id.tv_all_select:

                if (tvAllSelect.isSelected()) {
                    tvAllSelect.setSelected(false);
                } else {
                    tvAllSelect.setSelected(true);
                }

                deviceManagerAdapter.setAllSelect(tvAllSelect.isSelected());

                break;
        }
    }

    @Override
    public void initView(View rootView) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected boolean isNeedReload() {
        return true;
    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateDeviceManager event) {
        getDeviceManager(1, 300);
    }

    @Override
    protected void loadData() {
        etSearch.setCursorVisible(false);

        getDeviceManager(1, 300);
    }

    private DeviceManagerAdapter deviceManagerAdapter;

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        deviceManagerAdapter = new DeviceManagerAdapter(getActivity(), 1);//默认创建一个数组，不创建会有空指针异常
        recyclerView.setAdapter(deviceManagerAdapter);
    }


    private void getDeviceManager(int pageNum, int pageSize) {
        ReqDeviceManager reqDeviceManager = new ReqDeviceManager(pageNum, pageSize, "", DataWarehouse.getUserId());
        HttpClient.getInstance().service.getDeviceManager(reqDeviceManager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespDeviceManager>() {
                    @Override
                    public void onNext(RespDeviceManager respDeviceManager) {
                        if(respDeviceManager.getData()==null || respDeviceManager.getData().getList().size()==0){
                            tvNoData.setVisibility(View.VISIBLE);

                        }else {
                            tvNoData.setVisibility(View.GONE);
                            deviceManagerAdapter.setData(respDeviceManager.getData().getList());
                        }
                    }
                });
    }


    private void getSearchDeviceManager(String search) {
        ReqDeviceManager reqDeviceManager = new ReqDeviceManager(1, 300, search, DataWarehouse.getUserId());
        HttpClient.getInstance().service.searchDeviceManager(reqDeviceManager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespDeviceManager>() {
                    @Override
                    public void onNext(RespDeviceManager respDeviceManager) {
                        if(respDeviceManager.getData()==null || respDeviceManager.getData().getList().size()==0){
                            tvNoData.setVisibility(View.VISIBLE);
                            deviceManagerAdapter.setData(respDeviceManager.getData().getList());
                        }else {
                            tvNoData.setVisibility(View.GONE);
                            deviceManagerAdapter.setData(respDeviceManager.getData().getList());
                        }
                    }
                });

    }

    private void deviceLock(ArrayList<String> list, String command) {
        StringBuffer stringBuffer = new StringBuffer();

        for (int i =0 ;i <list.size();i++){
            if(i==0) {
                stringBuffer.append(list.get(i));
            }else {
                stringBuffer.append(","+list.get(i));
            }
        }

        HttpClient.getInstance(HttpClient.BASE_URL_CONTROL).service.deviceLocks(DataWarehouse.getUserId(),command,stringBuffer.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if ("200".equals(httpResult.getCode())) {
                           /* if (deviceManagerAdapter.getItemCount() == deviceManagerAdapter.getSelectList().size()) {
                                ToastUtils.show(context,"已上锁（全部）");
                            } else {
                                ToastUtils.show(context,"已锁定");
                            }*/
                            getDeviceManager(1, 300);
                        }else {
                            ToastUtils.show(context,"上锁失败");
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
                        deviceLock(deviceManagerAdapter.getSelectList(), "O33");
                    }
                });
    }
}
