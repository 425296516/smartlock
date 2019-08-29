package com.anlida.smartlock.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.event.QREvent;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqRepairInfo;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;
import com.anlida.smartlock.zxing.QRCodeScanActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RepairFragment extends LazyLoadFragment {

    @BindView(R.id.iv_scan_code)
    ImageView ivScanCode;
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
        etRepairBecause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etRepairBecause.setCursorVisible(true);
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @OnClick({R.id.tv_submit, R.id.iv_scan_code})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_scan_code:

                Intent intent = new Intent(context, QRCodeScanActivity.class);
                intent.putExtra("PAGE_TYPE", "RepairFragment");
                startActivity(intent);

                break;

            case R.id.tv_submit:

                if (!TextUtils.isEmpty(etRepairBecause.getText().toString()) && !TextUtils.isEmpty(etInputImei.getText().toString())) {
                    uploadRepairInfo(etRepairBecause.getText().toString(), etInputImei.getText().toString());
                } else {
                    ToastUtils.show(context, "请输入IMEI和报修报损原因");
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getImei(QREvent event) {
        String result = event.getResult();
        String pageType = event.getPageType();
        if ("RepairFragment".equals(pageType)) {
            if (!TextUtils.isEmpty(result)) {
                String mImei = result.replace("imei:", "");
                etInputImei.setVisibility(View.VISIBLE);
                etInputImei.setText(mImei);
                ivScanCode.setVisibility(View.GONE);
                ToastUtils.show(context, "扫描成功");
            } else {
                etInputImei.setVisibility(View.VISIBLE);
                ivScanCode.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected boolean isNeedReload() {
        return true;
    }

    @Override
    protected void loadData() {
        etRepairBecause.setCursorVisible(false);//隐藏光标
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
