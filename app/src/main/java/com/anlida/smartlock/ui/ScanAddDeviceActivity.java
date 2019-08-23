package com.anlida.smartlock.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.event.QREvent;
import com.anlida.smartlock.event.UpdateDeviceManager;
import com.anlida.smartlock.listener.OnSelectBloodTypeListener;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqDeviceUse;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;
import com.anlida.smartlock.widget.BloodTypePopupWindow;
import com.anlida.smartlock.zxing.QRCodeScanActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ScanAddDeviceActivity extends FMActivity {

    @BindView(R.id.iv_scan_code)
    ImageView ivScanCode;
    @BindView(R.id.et_input_name)
    EditText etInputName;
    @BindView(R.id.et_input_wordid)
    EditText etInputWordid;
    @BindView(R.id.et_input_idcard)
    EditText etInputIdcard;
    @BindView(R.id.et_input_phone)
    EditText etInputPhone;
    @BindView(R.id.et_input_age)
    EditText etInputAge;
    @BindView(R.id.tv_input_bloodtype)
    TextView tvInputBloodtype;
    @BindView(R.id.cb_man)
    RadioButton cbMan;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    private String mImei;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_add_device;
    }

    @OnClick({R.id.iv_scan_code, R.id.tv_submit, R.id.cb_man, R.id.cb_woman,R.id.tv_input_bloodtype})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_scan_code:

                Intent intent = new Intent(context, QRCodeScanActivity.class);
                intent.putExtra("PAGE_TYPE","ScanAddDeviceActivity");
                startActivity(intent);

                break;

            case R.id.tv_submit:
                if(!TextUtils.isEmpty(mImei) && !TextUtils.isEmpty(etInputName.getText().toString())&&!TextUtils.isEmpty(etInputWordid.getText().toString())
                        && !TextUtils.isEmpty(etInputIdcard.getText().toString()) && !TextUtils.isEmpty(etInputPhone.getText().toString())
                        &&!TextUtils.isEmpty(etInputAge.getText().toString()) && !TextUtils.isEmpty(tvInputBloodtype.getText().toString())){

                        addDeviceAndUser(DataWarehouse.getUserId(), mImei, etInputName.getText().toString(),
                            etInputWordid.getText().toString(), etInputIdcard.getText().toString(), etInputPhone.getText().toString(),
                            etInputAge.getText().toString(), tvInputBloodtype.getText().toString(), cbMan.isChecked() ?"1" : "2");
                }else {
                    ToastUtils.show(context,"请填写所有数据");
                }
                break;


            case R.id.cb_man:

                cbMan.setChecked(true);
                cbWoman.setChecked(false);

                break;

            case R.id.cb_woman:

                cbMan.setChecked(false);
                cbWoman.setChecked(true);

                break;

            case R.id.tv_input_bloodtype:

                BloodTypePopupWindow bloodTypePopupWindow = new BloodTypePopupWindow(this, new OnSelectBloodTypeListener() {
                    @Override
                    public void onSelect(String bloodType) {
                        tvInputBloodtype.setText(bloodType);
                    }

                    @Override
                    public void onDismiss() {

                    }
                });

                bloodTypePopupWindow.showAtLocation(tvInputBloodtype, Gravity.CENTER,400,-40);

                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void bindDeviceLock(QREvent event) {
        String result = event.getResult();
        mImei = result.replace("imei:","");
        ToastUtils.show(context,"扫描成功");
    }

    @Override
    protected String description() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    public void initView(Bundle bundle) {

    }

    public void addDeviceAndUser(String createBy, String imei, String name, String workId, String idCard, String phone, String age, String bloodType,String sex) {
        HttpClient.getInstance().service.addDeviceAndUser(new ReqDeviceUse(createBy, imei, name, workId, idCard, phone, age, bloodType,sex))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        UpdateDeviceManager event = new UpdateDeviceManager();
                        EventBus.getDefault().post(event);
                        finish();
                    }
                });
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }

}
