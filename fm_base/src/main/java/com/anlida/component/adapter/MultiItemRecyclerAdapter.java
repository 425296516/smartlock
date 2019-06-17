package com.anlida.component.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by jiang on 17/7/11.
 */

public abstract class MultiItemRecyclerAdapter<T> extends BaseRecyclerAdapter<T>{
    protected MultiItemTypeSupport<T> multiItemTypeSupport;

    public MultiItemRecyclerAdapter(Context context, MultiItemTypeSupport<T> multiItemTypeSupport, List<T> data) {
        super(context, -1, data);
        this.multiItemTypeSupport=multiItemTypeSupport;
    }

    @Override
    public int getItemViewType(int position) {
        return multiItemTypeSupport.getItemViewType(position,data.get(position));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId=multiItemTypeSupport.getLayoutId(viewType);
        ViewHolder holder=ViewHolder.get(context,parent,layoutId);
        return holder;
    }
}
