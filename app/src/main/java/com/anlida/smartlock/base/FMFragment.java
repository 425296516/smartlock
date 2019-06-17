package com.anlida.smartlock.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anlida.component.ui.BaseFragment;

import com.anlida.smartlock.R;
import com.anlida.smartlock.utils.RxLifecycleUtils;
import com.uber.autodispose.AutoDisposeConverter;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class FMFragment extends BaseFragment {
    protected Unbinder unbinder;
    protected ImageView ivLeft;
    protected TextView tvTitle;
    protected TextView tvRight;
    protected LinearLayout llBar;
    protected CompositeDisposable disposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (needEventBus()) {
            EventBus.getDefault().register(this);
        }
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (showToolBar()) {
            initToolbar(rootView);
        }
        return rootView;
    }

    @Override
    public void startActivity(Intent intent) {
        //未登录跳转到登录页面
//        if (!DataWarehouse.isLogin()) {
//            context.startActivity(new Intent(context, LoginActivity.class));
//        } else {
            super.startActivity(intent);
//        }
    }

    //未登录跳转到登录页面
    public boolean isToLoginActivity() {
//        if (!DataWarehouse.isLogin()) {
//            startActivity(new Intent(getActivity(), LoginActivity.class));
//            return true;
//        }
        return false;
    }

    protected void initToolbar(View rootView) {
        tvTitle = rootView.findViewById(R.id.center);
        ivLeft = rootView.findViewById(R.id.left);
        tvRight = rootView.findViewById(R.id.right);
        View paddingView = rootView.findViewById(R.id.view_padding);
        if (paddingView != null) {
            paddingView.setVisibility(View.VISIBLE);
        }
        llBar = rootView.findViewById(R.id.ll_bar);
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

    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        return RxLifecycleUtils.bindLifecycle(this);
    }

    /**
     * 设置标题中间文字
     */
    protected void setTitleText(String text) {
        if (tvTitle != null) {
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
     * 设置标题背景图片
     */
    protected void setBarDrawable(@DrawableRes int res) {
        if (llBar != null) {
            llBar.setBackgroundResource(res);
        }
    }

    /**
     * 设置标题左侧图片
     */
    protected void setLeftDrawable(@DrawableRes int res) {
        if (ivLeft != null && isAdded()) {
            ivLeft.setImageResource(res);
        }
    }

    /**
     * 设置标题右侧图片
     */
    protected void setRightDrawable(@DrawableRes int res) {
        if (tvRight != null && isAdded()) {
            Drawable drawable = getResources().getDrawable(res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            tvRight.setCompoundDrawables(null, null, drawable, null);
        }
    }

    /**
     * 设置中间标题右侧图片
     */
    protected void setCenterDrawable(@DrawableRes int res) {
        if (tvTitle != null && isAdded()) {
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

    }

    protected void onCenterClick() {

    }

    protected void onRightClick() {

    }

    @Override
    public void onResume() {
        super.onResume();
        String des = description() == null ? this.getClass().getSimpleName() : description();
        MobclickAgent.onPageStart(des);
        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        String des = description() == null ? this.getClass().getSimpleName() : description();
        MobclickAgent.onPageEnd(des);
        MobclickAgent.onPause(getContext());
    }

    /**
     * 埋点描述
     *
     * @return 对Activity的描述
     */

    protected abstract String description();


    @Override
    protected int getToolBarResource() {
        return R.layout.include_common_toolbar;
    }

    protected boolean needEventBus() {
        return false;
    }

    @Override
    public void onDestroyView() {
        if (needEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        if (unbinder != null) {
            unbinder.unbind();
        }

        disposable.dispose();
        super.onDestroyView();
    }
}
