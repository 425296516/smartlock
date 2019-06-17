package com.anlida.component.ui;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

/**
 * 适用于Viewpager+Fragment配合使用
 */
public abstract class BaseTabFragment extends Fragment implements IFragment{
    protected boolean isViewInit;
    protected boolean isUserVisible;
    protected boolean isDataFetched;
    protected Context context;

    /**
     * 绑定数据
     */
    protected abstract void fetchData();


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInit = true;
        initData();
        loadData();
    }

    protected void loadData() {
        if (isViewInit && isUserVisible && !isDataFetched) {
            fetchData();
            isDataFetched = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUserVisible = isVisibleToUser;
        loadData();
    }
}
