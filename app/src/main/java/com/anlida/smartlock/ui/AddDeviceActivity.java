package com.anlida.smartlock.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.anlida.smartlock.MainActivity;
import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqDeviceUse;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DataWarehouse;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AddDeviceActivity extends FMActivity {

    @BindView(R.id.et_imei)
    EditText etImei;
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
    @BindView(R.id.et_input_bloodtype)
    EditText etInputBloodtype;

    @BindView(R.id.cb_man)
    RadioButton cbMan;
    @BindView(R.id.cb_woman)
    RadioButton cbWoman;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device;
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

    @OnClick({R.id.tv_submit, R.id.cb_man, R.id.cb_woman})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_submit:

                addDeviceAndUser(DataWarehouse.getUserId(),etImei.getText().toString(),etInputName.getText().toString(),
                        etInputWordid.getText().toString(),etInputIdcard.getText().toString(),etInputPhone.getText().toString(),
                        etInputAge.getText().toString(),etInputBloodtype.getText().toString(),"1");

                break;

            case R.id.cb_man:

                cbMan.setChecked(true);
                cbWoman.setChecked(false);

                break;

            case R.id.cb_woman:

                cbMan.setChecked(false);
                cbWoman.setChecked(true);

                break;
        }
    }

    public void addDeviceAndUser(String createBy, String imei, String name, String workId, String idCard, String phone, String age, String bloodType,String sex) {
        HttpClient.getInstance().service.addDeviceAndUser(new ReqDeviceUse(createBy, imei, name, workId, idCard, phone, age, bloodType,sex))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        MainActivity.start(context);
                        finish();

                    }
                });
    }

    @Override
    protected boolean showToolBar() {
        return false;
    }


}
