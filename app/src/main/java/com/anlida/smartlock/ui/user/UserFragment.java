package com.anlida.smartlock.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ReqPersonCenter;
import com.anlida.smartlock.model.req.ReqUpdatePhone;
import com.anlida.smartlock.model.resp.RespPersonCenter;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.CountDownUtils;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UserFragment extends LazyLoadFragment {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_work_address)
    TextView tvWorkAddress;
    @BindView(R.id.tv_work_class)
    TextView tvWorkClass;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.tv_work_id)
    TextView tvWorkId;
    @BindView(R.id.tv_urgent_name)
    TextView tvUrgentName;
    @BindView(R.id.tv_urgent_work_address)
    TextView tvUrgentWorkAddress;
    @BindView(R.id.tv_work_class_urgent)
    TextView tvWorkClassUrgent;
    @BindView(R.id.tv_urgent_phone)
    TextView tvUrgentPhone;
    @BindView(R.id.tv_urgent_update)
    TextView tvUrgentUpdate;
    @BindView(R.id.tv_urgent_work_id)
    TextView tvUrgentWorkId;
    @BindView(R.id.et_phone)
    EditText etPhone;
    private String id, liableId;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @OnClick({R.id.btn_unlogin, R.id.tv_update, R.id.tv_urgent_update})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_unlogin:

                getActivity().finish();
                startActivity(new Intent(context, LoginActivity.class));
                DataWarehouse.setLogin(false);

                break;

            case R.id.tv_update:

                showUpdatePhone(v, getActivity());

                break;

            case R.id.tv_urgent_update:

                showUpdatePhone(v, getActivity());

                break;

        }
    }

    @Override
    protected boolean isNeedReload() {
        return true;
    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {

    }

    @Override
    protected void loadData() {

        getUserInfo(DataWarehouse.getUserId());

    }

    @Override
    protected String description() {
        return null;
    }


    public void getUserInfo(String userId) {
        HttpClient.getInstance().service.getMainContactInfo(new ReqPersonCenter(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespPersonCenter>() {
                    @Override
                    public void onNext(RespPersonCenter userInfo) {
                            if (isAdded() && userInfo.getCode() == 0) {
                                id = userInfo.getData().get(0).getId();
                                tvName.setText(userInfo.getData().get(0).getName());
                                tvWorkAddress.setText(userInfo.getData().get(0).getAddress());
                                tvWorkClass.setText(userInfo.getData().get(0).getContructionName());
                                tvPhone.setText(userInfo.getData().get(0).getPhone());
                                tvWorkId.setText(userInfo.getData().get(0).getWorkId());
                            }
                    }
                });


        HttpClient.getInstance().service.getUrgentContactInfo(new ReqPersonCenter(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespPersonCenter>() {
                    @Override
                    public void onNext(RespPersonCenter userInfo) {
                        if (isAdded() && userInfo.getCode() == 0) {
                            liableId = userInfo.getData().get(0).getId();
                            tvUrgentName.setText(userInfo.getData().get(0).getName());
                            tvUrgentWorkAddress.setText(userInfo.getData().get(0).getAddress());
                            tvWorkClassUrgent.setText(userInfo.getData().get(0).getContructionName());
                            tvUrgentPhone.setText(userInfo.getData().get(0).getPhone());
                            tvUrgentWorkId.setText(userInfo.getData().get(0).getWorkId());
                        }
                    }
                });
    }

    public AlertDialog showUpdatePhone(View view, Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        // 获取布局
        final Window window = alertDialog.getWindow();

        window.setContentView(R.layout.dialog_update_phone);
        EditText etInputPhone = window.findViewById(R.id.et_input_phone);
        EditText etCode = window.findViewById(R.id.et_verficode);
        TextView tvGetCode = window.findViewById(R.id.tv_get_verficode);
        TextView tvConfirm = window.findViewById(R.id.tv_confirm);

        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tvGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient.getInstance().service.getVerifiCode(etInputPhone.getText().toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new FMSubscriber<HttpResult>() {
                            @Override
                            public void onNext(HttpResult httpResult) {
                                // 获取验证码成功之后开启倒计时
                                CountDownUtils.startCountDown(context, tvGetCode);
                            }
                        });
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etInputPhone.getText().toString()) && !TextUtils.isEmpty(etCode.getText().toString())) {
                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }
                    if (view.getId() == R.id.tv_update) {
                        updatePhone(etInputPhone.getText().toString(), etCode.getText().toString());
                    } else if (view.getId() == R.id.tv_urgent_update) {
                        updateLiablePhone(etInputPhone.getText().toString(), etCode.getText().toString());
                    }
                }
            }
        });

        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if(tvGetCode!=null) {
                    CountDownUtils.stopCountdown(context, tvGetCode);
                }
            }
        });

        return alertDialog;
    }


    public void updatePhone(String mobile, String code) {
        ReqUpdatePhone reqUpdatePhone = new ReqUpdatePhone(id, mobile, code);
        HttpClient.getInstance().service.updatePhone(reqUpdatePhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if ("0".equals(httpResult.getCode())) {
                            ToastUtils.show(context, "修改成功");
                            tvPhone.setText(mobile);
                        } else {
                            ToastUtils.show(context, "修改失败");
                        }
                    }
                });
    }

    public void updateLiablePhone(String mobile, String code) {
        ReqUpdatePhone reqUpdatePhone = new ReqUpdatePhone(liableId, mobile, code);
        HttpClient.getInstance().service.updatePhone(reqUpdatePhone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if ("0".equals(httpResult.getCode())) {
                            ToastUtils.show(context, "修改成功");
                            tvUrgentPhone.setText(mobile);
                        } else {
                            ToastUtils.show(context, "修改失败");
                        }
                    }
                });
    }
}
