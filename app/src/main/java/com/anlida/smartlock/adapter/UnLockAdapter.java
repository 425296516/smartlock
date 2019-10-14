package com.anlida.smartlock.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.model.resp.RespWarnRecord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.internal.DebouncingOnClickListener;

public class UnLockAdapter extends RecyclerView.Adapter<UnLockAdapter.UnLockViewHolder> {
    private List<RespWarnRecord.DataBean.ListBean> listBeans;
    private OnClickListener onClickListener;
    private Activity mActivity;
    private int mType;
    private boolean mAllSelect;

    public UnLockAdapter(Activity activity, int type) {
        mActivity = activity;
        mType = type;
    }

    public void setAllSelect(boolean allSelect) {
        mAllSelect = allSelect;
        notifyDataSetChanged();
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

    public ArrayList<RespWarnRecord.DataBean.ListBean> getSelectList(){
        ArrayList arrayList = new ArrayList();
        if(!hashSet.isEmpty()){
            for(RespWarnRecord.DataBean.ListBean s: hashSet) {
                arrayList.add(s);
            }
        }
        return arrayList;
    }

    private HashSet<RespWarnRecord.DataBean.ListBean> hashSet = new HashSet<>();

    @NonNull
    @Override
    public UnLockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_please_unlock, parent, false);
        UnLockViewHolder holder = new UnLockViewHolder(v);
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
    public void onBindViewHolder(@NonNull UnLockViewHolder holder, int position) {
        holder.tvName.setText(listBeans.get(position).getUname());
        if ("1".equals(listBeans.get(position).getStatus())) {
            String time = listBeans.get(position).getCreateTime();
            holder.tvTime.setText(""+time);
        } else {
            String time = listBeans.get(position).getUpdateTime();
            holder.tvTime.setText(""+time);
        }

        if(listBeans.get(position).getWarningType().equals("请求上锁")){
            holder.tvType.setTextColor(mActivity.getResources().getColor(R.color.color_96BB65));
        }else  if(listBeans.get(position).getWarningType().equals("请求解锁")){
            holder.tvType.setTextColor(mActivity.getResources().getColor(R.color.color_ED8181));
        }

        holder.tvType.setText(listBeans.get(position).getWarningType());

        if (mAllSelect) {
            for (int i=0;i<listBeans.size();i++){
                hashSet.add(listBeans.get(i));
            }
            holder.ivSelect.setImageResource(R.drawable.btn_blue_pre);
        } else {
            for (int i=0;i<listBeans.size();i++){
                hashSet.remove(listBeans.get(i));
            }
            holder.ivSelect.setImageResource(R.drawable.btn_blue);
        }


        if (mType == 1) {
            holder.ivSelect.setVisibility(View.VISIBLE);
            holder.tvDealResult.setVisibility(View.GONE);

            holder.ivSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.ivSelect.setSelected(!holder.ivSelect.isSelected());
                    if (holder.ivSelect.isSelected()) {
                        holder.ivSelect.setImageResource(R.drawable.btn_blue_pre);
                        hashSet.add(listBeans.get(position));
                    } else {
                        hashSet.remove(listBeans.get(position));
                        holder.ivSelect.setImageResource(R.drawable.btn_blue);
                    }
                }
            });

        } else {
            holder.ivSelect.setVisibility(View.GONE);

            holder.tvDealResult.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        if (listBeans == null || listBeans.size() == 0) {
            return 0;
        } else {
            return listBeans.size();
        }
    }

    static class UnLockViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_deal_result)
        TextView tvDealResult;
        @BindView(R.id.iv_select)
        ImageView ivSelect;

        public UnLockViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnClickListener {
        void onClick(View v, int position);
    }
}
