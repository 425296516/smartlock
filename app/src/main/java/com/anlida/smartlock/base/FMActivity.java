package com.anlida.smartlock.base;

import android.arch.lifecycle.Lifecycle;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anlida.component.ExitEvent;
import com.anlida.component.ui.BaseActivity;
import com.anlida.smartlock.R;
import com.anlida.smartlock.utils.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class FMActivity extends BaseActivity {
    protected Unbinder unbinder;
    protected ImageView ivLeft;
    protected TextView tvTitle;
    protected TextView tvRight;
    protected View paddingView;
    protected LinearLayout llBar;
    protected CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (needEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
        initToolbar();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            setPaddingViewVisibility(View.VISIBLE);
        } else {
            setPaddingViewVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String des = description() == null ? this.getClass().getSimpleName() : description();
        MobclickAgent.onPageStart(des);
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String des = description() == null ? this.getClass().getSimpleName() : description();
        MobclickAgent.onPageEnd(des);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        if (needEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
        disposable.dispose();
        super.onDestroy();
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    protected <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event event) {
        return RxLifecycleUtils.bindLifecycle(this,event);
    }

    private void initToolbar() {
        llBar=findViewById(R.id.ll_bar);
        tvTitle = findViewById(R.id.center);
        ivLeft = findViewById(R.id.left);
        tvRight = findViewById(R.id.right);
        paddingView = findViewById(R.id.view_padding);
        if (tvTitle != null) {
            tvTitle.setOnClickListener(v -> onCenterClick());
        }
        if (ivLeft != null) {
            ivLeft.setOnClickListener(v -> onLeftClick());
        }
        if (tvRight != null) {
            tvRight.setOnClickListener(v -> onRightClick());
        }
    }

    /**
     * 设置标题中间文字
     */
    protected void setTitleText(String text) {
        if (tvTitle != null && !TextUtils.isEmpty(text)) {
            tvTitle.setText(text);
        }
    }

    /**
     * 设置标题右侧文字
     */
    protected void setRightText(String text) {
        if (tvRight != null && !TextUtils.isEmpty(text)) {
            tvRight.setText(text);
        }
    }

    /**
     * 设置标题左侧图片
     */
    protected void setLeftDrawable(@DrawableRes int res) {
        if (ivLeft != null) {
            ivLeft.setImageResource(res);
        }
    }

    /**
     * 设置标题右侧图片
     */
    protected void setRightDrawable(@DrawableRes int res) {
        if (tvRight != null) {
            Drawable drawable = getResources().getDrawable(res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvRight.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置中间标题右侧图片
     */
    protected void setCenterDrawable(@DrawableRes int res) {
        if (tvTitle != null) {
            Drawable drawable = getResources().getDrawable(res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvTitle.setCompoundDrawablePadding(10);
            tvTitle.setCompoundDrawables(null, null, drawable, null);
        }
    }

    protected void setLeftVisibility(int flag) {
        if (ivLeft != null) {
            ivLeft.setVisibility(flag);
        }
    }

    protected void setCenterVisibility(int flag) {
        if (tvTitle != null) {
            tvTitle.setVisibility(flag);
        }
    }

    protected void setRightVisibility(int flag) {
        if (tvRight != null) {
            tvRight.setVisibility(flag);
        }
    }

    protected void setPaddingViewVisibility(int flag) {
        if (paddingView != null) {
            paddingView.setVisibility(flag);
        }
    }

    /**
     * 设置标题背景图片
     */
    protected void setBarDrawable(@DrawableRes int res) {
        if (llBar != null) {
            llBar.setBackgroundResource(res);
        }
    }

    /**
     * 标题中间点击事件
     */
    protected void setOnCenterClick(View.OnClickListener listener) {
        if (tvTitle != null) {
            tvTitle.setOnClickListener(listener);
        }
    }

    /**
     * 标题左侧点击事件
     */
    protected void setOnLeftClick(View.OnClickListener listener) {
        if (ivLeft != null) {
            ivLeft.setOnClickListener(listener);
        }
    }

    /**
     * 标题右侧点击事件
     */
    protected void setOnRightClick(View.OnClickListener listener) {
        if (tvRight != null) {
            tvRight.setOnClickListener(listener);
        }
    }

    protected void onLeftClick() {
        finish();
    }

    protected void onCenterClick() {

    }

    protected void onRightClick() {

    }

    @Override
    protected void onBeforeSetContentView(Bundle savedInstanceState) {

    }

    /**
     * 是否需要使用eventBus
     */
    protected boolean needEventBus() {
        return true;
    }

    @Override
    protected int getToolBarResource() {
        return R.layout.include_common_toolbar;
    }

    /**
     * 埋点描述
     *
     * @return 对Activity的描述
     */

    protected abstract String description();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    //隐藏软键盘
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    //是否应该隐藏软键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    /**
     * activity统一退出，无需覆写
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void finishActivity(ExitEvent event) {
        finish();
    }
}
