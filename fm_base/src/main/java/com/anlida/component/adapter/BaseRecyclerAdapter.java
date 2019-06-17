package com.anlida.component.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.anlida.component.listener.BindViewListener;
import com.anlida.component.listener.OnItemClickListener;

import java.util.List;

/**
 * Created by jiang on 2017/5/3.
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected List<T> data;
    protected BindViewListener<T> listener;
    protected OnItemClickListener onItemClickListener;
    protected int layoutId;
    protected Context context;
    protected LayoutInflater inflater;


    public BaseRecyclerAdapter(Context context, int layoutId, List<T> data) {
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.get(context, parent, layoutId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }


    public abstract void convert(ViewHolder holder, T t, int position);
}
