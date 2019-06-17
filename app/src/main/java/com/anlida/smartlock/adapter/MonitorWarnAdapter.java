package com.anlida.smartlock.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.model.req.ReqDealWarning;
import com.anlida.smartlock.model.resp.RespWarnRecord;
import com.anlida.smartlock.network.HttpClient;
import com.anlida.smartlock.utils.DialogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MonitorWarnAdapter extends RecyclerView.Adapter<MonitorWarnAdapter.MonitorWarnViewHolder> {

    private List<RespWarnRecord.DataBean.ListBean> listBeans;
    private OnClickListener onClickListener;
    private Activity mActivity;
    private int mType;

    public MonitorWarnAdapter(Activity activity, int type) {
        mActivity = activity;
        mType = type;
    }

    public void setData(List<RespWarnRecord.DataBean.ListBean> listBeans) {
        this.listBeans = listBeans;
        notifyDataSetChanged();
    }

    public void addData(List<RespWarnRecord.DataBean.ListBean> listBeans) {
        this.listBeans.addAll(listBeans);
        notifyDataSetChanged();
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MonitorWarnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_monitor_warn, parent, false);
        MonitorWarnViewHolder holder = new MonitorWarnViewHolder(v);
        holder.itemView.setOnClickListener(new DebouncingOnClickListener() {
            @Override
            public void doClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(view, (int) view.getTag());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MonitorWarnViewHolder holder, int position) {

        holder.tvWarn.setText(listBeans.get(position).getWarningType());
        holder.tvName.setText(listBeans.get(position).getUname());
        if ("1".equals(listBeans.get(position).getStatus())) {
            String time = listBeans.get(position).getCreateDate();
            if(!TextUtils.isEmpty(time) && time.length()>5) {
                holder.tvTime.setText(time.substring(time.length() - 5, time.length()));
            }
        } else {
            String time = listBeans.get(position).getUpdateDate();
            if(!TextUtils.isEmpty(time) && time.length()>5) {
                holder.tvTime.setText(time.substring(time.length() - 5, time.length()));
            }
        }

        if (mType == 1) {
            holder.tvDeal.setVisibility(View.VISIBLE);
            holder.tvDealResult.setVisibility(View.GONE);
            holder.tvDeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dealWarningRecord(listBeans.get(position).getId() + "", "2");
                    DialogUtil.showDialogunLock(mActivity);
                }
            });

        } else {
            holder.tvDeal.setVisibility(View.GONE);
            holder.tvDealResult.setVisibility(View.VISIBLE);
        }

    }

    private void dealWarningRecord(String id, String status) {
        ReqDealWarning reqDealWarning = new ReqDealWarning(id, status);
        HttpClient.getInstance().service.dealWarningRecord(reqDealWarning)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respDeviceManager) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        if (listBeans == null || listBeans.size() == 0) {
            return 0;
        } else {
            return listBeans.size();
        }
    }

    static class MonitorWarnViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_warn)
        TextView tvWarn;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_deal_result)
        TextView tvDealResult;
        @BindView(R.id.tv_deal)
        TextView tvDeal;

        public MonitorWarnViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }
}
