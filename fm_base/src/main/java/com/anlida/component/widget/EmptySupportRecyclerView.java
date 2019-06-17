package com.anlida.component.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class EmptySupportRecyclerView extends RecyclerView {

    private View emptyView;

    private View errorView;

    public EmptySupportRecyclerView(Context context) {
        super(context);
    }

    public EmptySupportRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptySupportRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                errorView.setVisibility(GONE);
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(VISIBLE);
                    EmptySupportRecyclerView.this.setVisibility(GONE);
                } else {
                    emptyView.setVisibility(GONE);
                    EmptySupportRecyclerView.this.setVisibility(VISIBLE);
                }
            }
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        emptyObserver.onChanged();
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public void setErrorView(View errorView) {
        this.errorView = errorView;

    }

    public void setErrorVisible() {
        if (errorView != null) {
            errorView.setVisibility(VISIBLE);
            EmptySupportRecyclerView.this.setVisibility(GONE);
            emptyView.setVisibility(GONE);
        }
    }


}
