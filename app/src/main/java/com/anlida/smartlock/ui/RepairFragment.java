package com.anlida.smartlock.ui;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqRepairInfo;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepairFragment extends LazyLoadFragment {

    @BindView(R.id.et_input_imei)
    EditText etInputImei;
    @BindView(R.id.et_repair_because)
    EditText etRepairBecause;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @OnClick({R.id.tv_submit})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_submit:

                uploadRepairInfo(etRepairBecause.getText().toString(), etInputImei.getText().toString());

                break;
        }
    }

    @Override
    protected void loadData() {

    }

    public void uploadRepairInfo(String causeOfDamage, String imei) {
        HttpClient.getInstance().service.uploadRepairInfo(new ReqRepairInfo(causeOfDamage, imei, DataWarehouse.getUserId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        etInputImei.setText("");
                        etRepairBecause.setText("");
                        ToastUtils.show(context, "反馈成功");
                    }
                });
    }

    @Override
    protected String description() {
        return null;
    }

}
