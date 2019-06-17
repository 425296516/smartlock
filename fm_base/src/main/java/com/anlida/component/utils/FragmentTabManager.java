package com.anlida.component.utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment管理工具类，用于Fragment的添加、隐藏、初始化等
 */
public class FragmentTabManager {
    private Context context;
    private FragmentManager fragmentManager;
    private int containResource;
    private List<TabInfo> tabs;
    private TabInfo currentTab;
    private TabHost.OnTabChangeListener tabChangeListener;

    public FragmentTabManager(Context context, FragmentManager fragmentManager, int containResource) {
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.containResource = containResource;
        tabs = new ArrayList<>();
    }

    /**
     * 添加选项卡
     */
    public FragmentTabManager addTab(String tag, Class<?> clazz, Bundle args) {
        TabInfo tab = new TabInfo(tag, clazz, args);
        tabs.add(tab);
        return this;
    }

    /**
     * @return 当前可见的Fragment
     */
    public Fragment getCurrentFragment() {
        return currentTab == null ? null : currentTab.fragment;
    }

    /**
     * @return 当前可见Fragment的Tag
     */
    public String getCurrentTag() {
        return currentTab == null ? null : currentTab.tag;
    }

    public TabInfo getCurrentTab() {
        return currentTab;
    }

    /**
     * 设置Tab变化监听
     */
    public void setonTabChangeListener(TabHost.OnTabChangeListener listener) {
        this.tabChangeListener = listener;
    }

    /**
     * 切换选项卡
     */
    public void changeTab(String tag) {
        if (tag == null) {
            throw new IllegalArgumentException("tag not allowed null");
        }
        FragmentTransaction ft = doChangeTab(tag);
        if (ft != null) {
            ft.commit();
        }
        if (tabChangeListener != null) {
            tabChangeListener.onTabChanged(tag);
        }

    }

    /**
     * fragment状态保存
     *
     * @param tag
     */
    public void restoreTab(String tag) {
        for (TabInfo tab : tabs) {
            tab.fragment = fragmentManager.findFragmentByTag(tab.tag);
            if (tab.tag.equalsIgnoreCase(tag)) {
                currentTab = tab;
                break;
            }
        }
    }

    private FragmentTransaction doChangeTab(String tag) {
        FragmentTransaction ft = null;
        TabInfo changeTab = null;
        for (TabInfo tab : tabs) {
            if (tab.tag.equalsIgnoreCase(tag)) {
                changeTab = tab;
                break;
            }
        }
        if (changeTab == null) {
            throw new IllegalStateException("Tab not find" + tag);
        }

        if (currentTab!=changeTab) {
            ft = fragmentManager.beginTransaction();
            if (changeTab.fragment != null) {
                ft.show(changeTab.fragment);
            } else {
                changeTab.fragment = Fragment.instantiate(context, changeTab.clazz.getName(), changeTab.bundle);
                ft.add(containResource, changeTab.fragment, tag);
            }
            if (currentTab != null && currentTab.fragment != null) {
                ft.hide(currentTab.fragment);
            }
            currentTab = changeTab;
        }
        return ft;
    }

    public void destory(String tag) {
        TabInfo changeTab = null;
        for (TabInfo tab : tabs) {
            if (tab.tag.equalsIgnoreCase(tag)) {
                changeTab = tab;
                break;
            }
        }
        if (changeTab == null) {
            throw new IllegalStateException("Tab not find" + tag);
        }
        if (changeTab.fragment != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.remove(changeTab.fragment).commit();
            changeTab.fragment = null;
        }
        if (currentTab == changeTab) {
            currentTab = null;
        }
    }


    /**
     * 选项卡实体
     */
    class TabInfo {
        private String tag;
        private Class<?> clazz;
        private Bundle bundle;
        private Fragment fragment;

        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clazz = clazz;
            this.bundle = args;
        }
    }
}
