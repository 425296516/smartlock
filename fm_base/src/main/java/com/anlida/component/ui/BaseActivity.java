package com.anlida.component.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.anlida.component.R;
import com.anlida.component.utils.ActivityCollector;
import com.anlida.component.widget.ElasticTouchListener;


public abstract class BaseActivity extends AppCompatActivity implements IActivity {
    protected Toolbar toolbar;
    protected Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onBeforeSetContentView(savedInstanceState);
        ActivityCollector.addActivity(this);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
        if (showToolBar()) {
            toolbar = findViewById(R.id.toolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        }
        context = this;
        if (getIntent() != null) {
            Bundle bundle = getIntent().getExtras();
            parseBundle(bundle);
        }
        initData(savedInstanceState);
        initView(savedInstanceState);
        if (hasFrame() && getFrameResource() != 0) {
            handleFrameView();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == 0) {
            throw new RuntimeException("layoutResID==0 have you create a layout?");
        }
        if (showToolBar() && getToolBarResource() != -1) {
            View rootView;
            if (hasFrame() && getFrameResource() != 0) {
                rootView = LayoutInflater.from(this).inflate(R.layout.base_main_frame, null, false);
                ViewStub vsFrame = rootView.findViewById(R.id.vs_frame);
                vsFrame.setLayoutResource(getFrameResource());
                vsFrame.inflate();
            } else {
                rootView = LayoutInflater.from(this).inflate(R.layout.base_main, null, false);
            }
            FrameLayout container = rootView.findViewById(R.id.fl_container);
            container.setOnTouchListener(new ElasticTouchListener());
            ViewStub stub = rootView.findViewById(R.id.vs_toolbar);
            stub.setLayoutResource(getToolBarResource());
            stub.inflate();
            LayoutInflater.from(this).inflate(layoutResID, container, true);
            setContentView(rootView);

        } else {
            if (hasFrame() && getFrameResource() != 0) {
                View rootView = LayoutInflater.from(this).inflate(R.layout.base_main_frame, null, false);
                ViewStub vsFrame = rootView.findViewById(R.id.vs_frame);
                vsFrame.setLayoutResource(getFrameResource());
                vsFrame.inflate();
                FrameLayout container = rootView.findViewById(R.id.fl_container);
                container.setOnTouchListener(new ElasticTouchListener());
                LayoutInflater.from(this).inflate(layoutResID, container, true);
                setContentView(rootView);
            } else {
                super.setContentView(layoutResID);
            }


        }
    }

    /**
     * 是否显示Toolbar
     */
    protected boolean showToolBar() {
        return true;
    }

    /**
     * 设置toolbar的资源
     */
    protected int getToolBarResource() {
        return R.layout.base_include_toolbar;
    }

    /**
     * 设置Frame资源
     */
    protected int getFrameResource() {
        return 0;
    }

    /**
     * 获取toolbar
     */
    protected Toolbar getToolbar() {
        return toolbar;
    }

    /**
     * 是否需要全屏遮罩
     */
    protected boolean hasFrame() {
        return false;
    }

    /**
     * 处理遮View
     */
    protected void handleFrameView() {

    }


    /**
     * 设置ContentView之前进行的操作
     */
    protected void onBeforeSetContentView(Bundle savedInstanceState) {
    }

    /**
     * 解析跳转Intent的bundle数据
     */
    protected void parseBundle(Bundle bundle) {
    }

    /**
     * 加载布局资源Id
     */
    protected abstract int getLayoutId();

}
