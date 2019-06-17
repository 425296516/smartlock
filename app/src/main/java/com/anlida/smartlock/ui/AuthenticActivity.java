package com.anlida.smartlock.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anlida.smartlock.MainActivity;
import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMActivity;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.model.HttpResult;
import com.anlida.smartlock.model.req.ProvinceBean;
import com.anlida.smartlock.model.req.ReqManagerInfo;
import com.anlida.smartlock.model.req.ReqWorkerInfo;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.ui.user.ClipImageActivity;
import com.anlida.smartlock.utils.DataWarehouse;
import com.anlida.smartlock.utils.GetJsonDataUtil;
import com.anlida.smartlock.utils.ToastUtils;
import com.anlida.smartlock.widget.SelectPicPopupWindow;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.RegexUtils;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AuthenticActivity extends FMActivity {

    private SelectPicPopupWindow popupWindow;

    // 请求相机
    private static final int REQUEST_CAPTURE = 100;
    // 请求相册
    private static final int REQUEST_PICK = 101;
    // 请求截图
    private static final int REQUEST_CROP_PHOTO = 102;

    private File tempFile;
    private ImageView uploadImage;

    @BindView(R.id.ll_person_info)
    LinearLayout llPersonInfo;
    @BindView(R.id.tv_select_city)
    TextView tvSelectCity;
    @BindView(R.id.et_work_name)
    EditText etWorkName;
    @BindView(R.id.et_bys)
    EditText etBys;
    @BindView(R.id.et_worktype)
    EditText etWorktype;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_workId)
    EditText etWorkId;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.iv_idcard_front)
    ImageView ivIdcardFront;
    @BindView(R.id.iv_idcard_back)
    ImageView ivIdcardBack;

    @BindView(R.id.et_saddress)
    EditText etSaddress;
    @BindView(R.id.et_sbs)
    EditText etSbs;
    @BindView(R.id.et_sworkType)
    EditText etSworkType;
    @BindView(R.id.et_sName)
    EditText etSName;
    @BindView(R.id.et_sworkid)
    EditText etSworkid;
    @BindView(R.id.et_sidcard)
    EditText etSidcard;
    @BindView(R.id.et_sphone)
    EditText etSphone;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_authentic;
    }

    @Override
    protected String description() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        initJsonData();
    }

    private void addWorkerInfo(ReqWorkerInfo reqWorkerInfo) {
        HttpClient.getInstance().service.addWorkerInfo(reqWorkerInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if("0".equals(httpResult.getCode())) {
                            ReqManagerInfo reqManagerInfo = new ReqManagerInfo(DataWarehouse.getUserId(),
                                    tvSelectCity.getText().toString(), etSaddress.getText().toString(), etSbs.getText().toString(),
                                    etSworkType.getText().toString(), etSName.getText().toString(),
                                    etSworkid.getText().toString(), etSidcard.getText().toString(), etSphone.getText().toString(), DataWarehouse.getUserId());

                            addManagerInfo(reqManagerInfo);
                        }
                    }
                });
    }

    private void addManagerInfo(ReqManagerInfo reqManagerInfo) {
        HttpClient.getInstance().service.addManagerInfo(reqManagerInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {
                        if("0".equals(httpResult.getCode())) {
                            MainActivity.start(AuthenticActivity.this);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void initView(Bundle bundle) {

    }

    @OnClick({R.id.tv_get_verficode,R.id.ll_idcard_front, R.id.ll_idcard_back, R.id.tv_submit, R.id.tv_select_city})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_get_verficode:
                if(RegexUtils.isMobileSimple(etPhone.getText().toString())) {
                    sendCaptcha(etPhone.getText().toString());
                }else {
                    ToastUtils.show(context,"请输入正确的手机号");
                }
                break;


            case R.id.ll_idcard_front:
                uploadImage = ivIdcardFront;
                popupWindow = new SelectPicPopupWindow(this, popupSelectClick);
                // 让窗口从下方弹出
                popupWindow.showAtLocation(llPersonInfo, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;

            case R.id.ll_idcard_back:
                uploadImage = ivIdcardBack;
                popupWindow = new SelectPicPopupWindow(this, popupSelectClick);
                // 让窗口从下方弹出
                popupWindow.showAtLocation(llPersonInfo, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                break;

            case R.id.tv_submit:

                if(ivIdcardFront.isSelected() && ivIdcardBack.isSelected()
                && !TextUtils.isEmpty(tvSelectCity.getText().toString()) && !TextUtils.isEmpty(etWorkName.getText().toString())&& !TextUtils.isEmpty(etBys.getText().toString())
                &&!TextUtils.isEmpty(etWorktype.getText().toString()) && !TextUtils.isEmpty(etName.getText().toString())&& !TextUtils.isEmpty(etWorkId.getText().toString())
                &&!TextUtils.isEmpty(etIdcard.getText().toString()) && !TextUtils.isEmpty(etPhone.getText().toString())
                &&!TextUtils.isEmpty(etSaddress.getText().toString()) && !TextUtils.isEmpty(etSbs.getText().toString())&& !TextUtils.isEmpty(etSworkType.getText().toString())
                &&!TextUtils.isEmpty(etSName.getText().toString()) && !TextUtils.isEmpty(etSworkid.getText().toString())&& !TextUtils.isEmpty(etSidcard.getText().toString())) {

                    ReqWorkerInfo reqWorkerInfo = new ReqWorkerInfo(DataWarehouse.getUserId(),
                            tvSelectCity.getText().toString(), etWorkName.getText().toString(), etBys.getText().toString(),
                            etWorktype.getText().toString(), etName.getText().toString(),
                            etWorkId.getText().toString(), etIdcard.getText().toString(), etPhone.getText().toString(),etMessage.getText().toString());

                    addWorkerInfo(reqWorkerInfo);

                }else {
                    ToastUtils.show(context,"请填写完整的信息");
                }
                break;
            case R.id.tv_select_city:

                showPickerView();

                break;
        }
    }

    private List<ProvinceBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<ProvinceBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }


    public ArrayList<ProvinceBean> parseData(String result) {//Gson 解析
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;

                tvSelectCity.setText(tx);
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }


    private View.OnClickListener popupSelectClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.pic_camera_btn:

                    // 调用系统相机
                    startCamera();

                    break;

                // 相册
                case R.id.pic_photo_btn:

                    // 调用系统图库
                    startPhoto();
                    break;
            }
        }
    };

    /**
     * 调用系统图库
     */
    private void startPhoto() {
        // 如果要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.personal_choose_pic)), REQUEST_PICK);
    }

    /**
     * 调用系统相机
     */
    private void startCamera() {
        // 创建拍照存储的图片文件
        tempFile = new File(checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        // 调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(this, "com.anlida.smartlock.fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    /**
     * 检查文件是否存在
     */
    private static String checkDirPath(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return "";
        }
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dirPath;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_PICK: // RESULT_OK
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(intent.getData());
                }
                break;

            case REQUEST_CAPTURE: // 调用相机拍照的回调

                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;

            case REQUEST_CROP_PHOTO: // 取得裁剪后的图片
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }

                    Glide.with(this)
                            .load(uri)
                            .into(uploadImage);

                    File file = null;
                    try {
                        file = new File(new URI(uri.toString()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }

                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestBody);

                    RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), DataWarehouse.getUserId());

                    if(uploadImage == ivIdcardFront){
                        ivIdcardFront.setSelected(true);
                        uploadFrontImage(userIdBody, body);
                    }else  if(uploadImage == ivIdcardBack){
                        ivIdcardBack.setSelected(true);
                        uploadBackImage(userIdBody, body);
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }

    public void sendCaptcha(String mobile) {
        HttpClient.getInstance().service.getVerifiCode(mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {

                    }
                });
    }

    private void uploadFrontImage(RequestBody userId, MultipartBody.Part file) {
        HttpClient.getInstance().service.upLoadFrontImage(userId, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {

                    }
                });
    }

    private void uploadBackImage(RequestBody userId, MultipartBody.Part file) {
        HttpClient.getInstance().service.upLoadBackImage(userId, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<HttpResult>() {
                    @Override
                    public void onNext(HttpResult httpResult) {

                    }
                });
    }

    private void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }


    @Override
    protected boolean showToolBar() {
        return false;
    }

}
