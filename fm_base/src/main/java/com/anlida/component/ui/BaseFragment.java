package com.anlida.component.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.anlida.component.R;


public abstract class BaseFragment extends Fragment implements IFragment {
    protected Toolbar toolbar;
    protected View rootView;
    protected Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            parseArgument(getArguments());
        }
        if (rootView == null) {
            if (showToolBar() && getToolBarResource() != 0) {
                rootView = inflater.inflate(getBaseLayoutId(), container, false);
                ViewStub stub = rootView.findViewById(R.id.vs_toolbar);
                FrameLayout content = rootView.findViewById(R.id.fl_container);
                stub.setLayoutResource(getToolBarResource());
                stub.inflate();
                inflater.inflate(getLayoutId(), content, true);
            } else {
        rootView = inflater.inflate(getLayoutId(), container, false);
            }
        }
        if (showToolBar()) {
            toolbar = rootView.findViewById(R.id.toolbar);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(rootView);
        initData();
    }


    protected void parseArgument(Bundle argument) {
    }

    protected boolean showToolBar() {
        return false;
    }

    protected int getToolBarResource() {
        return R.layout.base_include_toolbar;
    }

    protected int getBaseLayoutId() {
        return R.layout.base_main;
    }

    protected abstract int getLayoutId();


}
