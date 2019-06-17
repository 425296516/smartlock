package com.anlida.smartlock.zxing;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anlida.component.utils.ActivityCollector;
import com.anlida.smartlock.R;
import com.anlida.smartlock.event.QREvent;
import com.anlida.smartlock.ui.AddDeviceActivity;
import com.anlida.smartlock.utils.ToastUtils;
import com.anlida.smartlock.zxing.camera.BeepManager;
import com.anlida.smartlock.zxing.camera.CameraManager;
import com.anlida.smartlock.zxing.decode.CaptureActivityHandler;
import com.anlida.smartlock.zxing.decode.FinishListener;
import com.anlida.smartlock.zxing.decode.InactivityTimer;
import com.anlida.smartlock.zxing.view.ViewfinderView;
import com.blankj.utilcode.util.StringUtils;
import com.google.zxing.Result;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class QRCodeScanActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private Button mBtn_Light;//打开灯光按钮
    private Toolbar mTb_toolbar;//标题栏
    private boolean isOpenLight;//手电筒状态

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private TextView tvInput;

    private InactivityTimer inactivityTimer;
    private SurfaceView surfaceView;
    private ImageView ivBack;
    private CameraManager cameraManager;
    private SurfaceHolder surfaceHolder;
    private BeepManager beepManager;
    // 请求相机权限
    private static final int CAMERA_REQUEST_CODE = 101;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_qrcode_scan);

        surfaceView = findViewById(R.id.preview_view);
        viewfinderView = findViewById(R.id.viewfinder_view);
        tvInput = findViewById(R.id.tv_input);
        ivBack = findViewById(R.id.iv_back);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        initView();

        tvInput.setText(Html.fromHtml("<u>"+"扫描异常？点击此处手动输入"+"</u>"));
    }

    private void initView() {
        mBtn_Light = findViewById(R.id.a_Capture_OpenLight_Btn);
        mTb_toolbar = findViewById(R.id.a_Capture_Toolbar);
        initToolbar();

        //打开关闭灯光按钮
        mBtn_Light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 判断设备是否有闪光灯（不是所有的设备都有闪光灯的，比如以前的旧手机或者平板）
                if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                    Toast.makeText(getApplicationContext(), "设备没有闪光灯！", Toast.LENGTH_SHORT).show();
                } else {
                    //如果是打开状态则关灯否则开灯
                    if (isOpenLight) {//关灯
                        cameraManager.setTorch(true);
                        mBtn_Light.setText("关灯");
                        isOpenLight = false;
                    } else {  // 开灯
                        cameraManager.setTorch(false);
                        mBtn_Light.setText("开灯");
                        isOpenLight = true;
                    }
                }
            }
        });
        ivBack.setOnClickListener(v -> finish());

        tvInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QRCodeScanActivity.this, AddDeviceActivity.class));
                finish();
            }
        });

    }

    private void initToolbar() {
        //设置标题栏文字
        mTb_toolbar.setTitle("扫描");
        setSupportActionBar(mTb_toolbar);
        //让导航按钮显示出来（就是返回按钮，默认图标是一个返回箭头）
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //创建标题栏上的菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    //标题栏上菜单的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: //点击标题栏返回按钮
                finish();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        inactivityTimer.onActivity();
        // CameraManager必须在这里初始化，而不是在onCreate()中。
        // 这是必须的，因为当我们第一次进入时需要显示帮助页，我们并不想打开Camera,测量屏幕大小
        // 当扫描框的尺寸不正确时会出现bug
        cameraManager = new CameraManager(this);
        viewfinderView.setCameraManager(cameraManager);
        surfaceHolder = surfaceView.getHolder();
//        qrcodeSetting();
        if (hasSurface) {
            // activity在paused时但不会stopped,因此surface仍旧存在；surfaceCreated()不会调用，因此在这里初始化camera
            initCamera(surfaceHolder);
        } else {
            // 如果SurfaceView已经渲染完毕，会回调surfaceCreated，在surfaceCreated中调用initCamera()
            surfaceHolder.addCallback(this);
        }

        // 加载声音配置，其实在BeemManager的构造器中也会调用该方法，即在onCreate的时候会调用一次
        beepManager.updatePrefs();
        // 恢复活动监控器
        inactivityTimer.onResume();

    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        // 暂停活动监控器
        inactivityTimer.onPause();
        // 关闭摄像头
        cameraManager.closeDriver();
        if (!hasSurface) {
            surfaceView.getHolder().removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        ActivityCollector.removeActivity(this);
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     */
    public void handleDecode(Result result, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        // 画结果图片
        //drawResultPoints(barcode, scaleFactor, result);
        // 启动声音效果
        beepManager.playBeepSoundAndVibrate();
        String resultString = result.getText();
        if (StringUtils.isEmpty(resultString)) {
            ToastUtils.show(this, "扫描失败");

        } else {
            QREvent event = new QREvent();
            event.setResult(resultString);
            EventBus.getDefault().post(event);

            finish();
        }
    }


    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Log.e("", "initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            // 打开Camera硬件设备
            cameraManager.openDriver(surfaceHolder);
            // 创建一个handler来打开预览，并抛出一个运行时异常
            if (handler == null) {
                handler = new CaptureActivityHandler(this, null, null, cameraManager);
            }
            // decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            Log.e("", "摄像头异常：" + ioe);
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Log.e("", "摄像头异常：" + e);
            displayFrameworkBugMessageAndExit();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Log.e("", "*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                hasSurface = true;
                initCamera(holder);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    /**
     * 若摄像头被占用或者摄像头有问题则跳出提示对话框
     */
    private void displayFrameworkBugMessageAndExit() {
        new AlertDialog.Builder(this)
                .setIcon(R.mipmap.logo)
                .setTitle(getString(R.string.app_name))
                .setMessage("抱歉，Android相机可能被占用，您可能需要重启设备。")
                .setPositiveButton("确定", new FinishListener(this))
                .setOnCancelListener(new FinishListener(this))
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                hasSurface = true;
                surfaceHolder.addCallback(this);
            } else {
                ToastUtils.show(this, getString(R.string.pic_clipimg_permission));
            }
        }
    }
}