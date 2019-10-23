package com.anlida.smartlock.ui.user;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anlida.smartlock.MainActivity;
import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.LoginPost;
import com.anlida.smartlock.model.req.ReqPersonCenter;
import com.anlida.smartlock.model.resp.RespPersonCenter;
import com.anlida.smartlock.model.resp.UserInfo;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.ui.AuthenticActivity;
import com.anlida.smartlock.utils.CountDownUtils;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.DialogUtil;
import com.anlida.smartlock.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends FMActivity {

    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    @BindView(R.id.rl_pwd)
    RelativeLayout rlPwd;
    @BindView(R.id.cb_remember_pwd)
    CheckBox cbRememberPwd;
    @BindView(R.id.rl_forget_pwd)
    RelativeLayout rlForgetPwd;
    private long latestMillis;

    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPassword;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.tv_forget_pwd)
    TextView tvForgetPwd;
    @BindView(R.id.iv_pwd_show)
    ImageView ivPwdShow;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        starter.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(Bundle bundle) {
        checkPermission();
    }

    private String[] permissions = {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            /*Manifest.permission.CALL_PHONE, Manifest.permission.CAMERA*/};

    List<String> mPermissionList = new ArrayList<>();

    // 权限判断
    public void checkPermission() {

        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        /**
         * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了

        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(this, permissions, 1);
        }
    }

    private void login(String mobile, String pwd) {
        HttpClient.getInstance().service.login(new LoginPost(mobile, pwd))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<UserInfo>() {
                    @Override
                    public void onNext(UserInfo userInfo) {
                        if (0 == userInfo.getCode()) {
                            DataWarehouse.setLogin(true);
                            DataWarehouse.setToken(userInfo.getToken());
                            DataWarehouse.setUserId(userInfo.getUser().getUserId());

                            if (cbRememberPwd.isChecked()) {
                                DataWarehouse.setAccount(etAccount.getText().toString());
                                DataWarehouse.setPassword(etPassword.getText().toString());
                            } else {
                                DataWarehouse.setAccount(null);
                                DataWarehouse.setPassword(null);
                            }

                            getMainContactInfo();
                        }
                    }
                });


    }

    public void getMainContactInfo() {
        HttpClient.getInstance().service.getMainContactInfo(new ReqPersonCenter(DataWarehouse.getUserId()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespPersonCenter>() {
                    @Override
                    public void onNext(RespPersonCenter respPersonCenter) {
                        if (0 == respPersonCenter.getCode()) {
                            if (respPersonCenter.getData().size() > 0) {
                                MainActivity.start(LoginActivity.this);
                                finish();
                            } else {
                                DataWarehouse.setLogin(false);
                                startActivity(new Intent(context, AuthenticActivity.class));
                                finish();
                            }
                        }
                    }
                });
    }

    @Override
    public void initView(Bundle bundle) {
        tvForgetPwd.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        tvForgetPwd.getPaint().setAntiAlias(true);//抗锯齿
        etAccount.setText(DataWarehouse.getAccount());
        etPassword.setText(DataWarehouse.getPassword());

        if (TextUtils.isEmpty(DataWarehouse.getPassword()) && TextUtils.isEmpty(DataWarehouse.getAccount())) {
            cbRememberPwd.setChecked(false);
            cbRememberPwd.setButtonDrawable(R.drawable.unselect);
        } else {
            cbRememberPwd.setChecked(true);
            cbRememberPwd.setButtonDrawable(R.drawable.selected);
        }

        ivPwdShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ivPwdShow.isSelected()) {
                    ivPwdShow.setSelected(false);
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                } else {
                    ivPwdShow.setSelected(true);
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        cbRememberPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbRememberPwd.setButtonDrawable(R.drawable.selected);
                    DataWarehouse.setAccount(etAccount.getText().toString());
                    DataWarehouse.setPassword(etPassword.getText().toString());
                } else {
                    cbRememberPwd.setButtonDrawable(R.drawable.unselect);
                    DataWarehouse.setAccount(null);
                    DataWarehouse.setPassword(null);
                }
            }
        });
    }


    @OnClick({R.id.tv_login, R.id.tv_forget_pwd})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:

                login(etAccount.getText().toString(), etPassword.getText().toString());

                break;

            case R.id.tv_forget_pwd:

                showUpdatePhone(this);

                break;

        }
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
    protected boolean showToolBar() {
        return false;
    }

    @Override
    protected String description() {
        return null;
    }


    public AlertDialog showUpdatePhone(Activity activity) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity, AlertDialog.THEME_HOLO_DARK).create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        // 获取布局
        final Window window = alertDialog.getWindow();

        window.setContentView(R.layout.dialog_update_phone);
        TextView tvTitle = window.findViewById(R.id.tv_title);
        EditText etInputPhone = window.findViewById(R.id.et_input_phone);
        EditText etCode = window.findViewById(R.id.et_verficode);
        TextView tvGetCode = window.findViewById(R.id.tv_get_verficode);
        TextView tvConfirm = window.findViewById(R.id.tv_confirm);

        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        tvTitle.setText("忘记密码");

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
                                CountDownUtils.startCountDown(getApplicationContext(), tvGetCode);
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

                    getPassword(etInputPhone.getText().toString(), etCode.getText().toString());
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


    public void getPassword(String mobile, String code) {
        HttpClient.getInstance().service.getPassword(mobile, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if("0".equals(httpResult.getCode())){
                            DialogUtil.showMessageTip(LoginActivity.this);
                        }
                    }
                });
    }
}
