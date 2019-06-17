package com.anlida.component.adapter;

/**
 * Created by jiang on 17/7/11.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itemType);
    int getItemViewType(int position, T t);
}
