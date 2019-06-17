package com.anlida.smartlock.listener;

public interface OnSelectListener {
    void onSelect(String province, String city, String busGroup);

    void onDismiss();
}
