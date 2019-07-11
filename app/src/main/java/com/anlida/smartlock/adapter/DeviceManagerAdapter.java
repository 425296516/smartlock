package com.anlida.smartlock.adapter;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.listener.OnSelectListener;
import com.anlida.smartlock.model.resp.RespDeviceManager;
import com.anlida.smartlock.widget.DrawableCenterTextView;
import com.anlida.smartlock.widget.SelectDeviceManagerPopupWindow;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceManagerAdapter extends RecyclerView.Adapter<DeviceManagerAdapter.DeviceManagerViewHolder> {

    private List<RespDeviceManager.DataBean.ListBean> deviceManagerList;
    private Activity mActivity;
    private boolean mAllSelect;

    public DeviceManagerAdapter(Activity activity, int type) {
        mActivity = activity;
    }

    public void setData(List<RespDeviceManager.DataBean.ListBean> deviceManagerList) {
        this.deviceManagerList = deviceManagerList;
        notifyDataSetChanged();
    }

    public void setAllSelect(boolean allSelect) {
        mAllSelect = allSelect;
        notifyDataSetChanged();
    }

    public void addData(List<RespDeviceManager.DataBean.ListBean> deviceManagerList) {
        this.deviceManagerList.addAll(deviceManagerList);
        notifyDataSetChanged();
    }

    public ArrayList<String> getSelectList(){
        ArrayList arrayList = new ArrayList();
        if(!hashSet.isEmpty()){
            for(String s: hashSet) {
                arrayList.add(s);
            }
        }
        return arrayList;
    }

    private HashSet<String> hashSet = new HashSet<>();

    @NonNull
    @Override
    public DeviceManagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_manager, parent, false);
        DeviceManagerViewHolder holder = new DeviceManagerViewHolder(v);

        return holder;
    }

    protected void setLeftDrawable(DrawableCenterTextView textView,@DrawableRes int res) {
        if (textView != null) {
            Drawable drawable = mActivity.getResources().getDrawable(res);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            textView.setCompoundDrawables(drawable, null, null, null);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceManagerViewHolder holder, int position) {

        holder.tvImei.setText(deviceManagerList.get(position).getImei());
        holder.tvName.setText(deviceManagerList.get(position).getName());
        holder.tvIdcard.setText(deviceManagerList.get(position).getIdCard());
        holder.tvPhone.setText(deviceManagerList.get(position).getPhone());

        if(deviceManagerList.get(position).getStatus() == 0){
            holder.tvLockStatus.setText("未上锁");
            setLeftDrawable(holder.tvLockStatus,R.drawable.icon_3);
        }else if(deviceManagerList.get(position).getStatus() == 1){
            holder.tvLockStatus.setText("可上锁");
            holder.tvLockStatus.setTextColor(mActivity.getResources().getColor(R.color.color_F88D0E));
            setLeftDrawable(holder.tvLockStatus,R.drawable.icon_4);
        }else if(deviceManagerList.get(position).getStatus() == 4){
            holder.tvLockStatus.setText("已上锁");
            holder.tvLockStatus.setTextColor(mActivity.getResources().getColor(R.color.color_549203));
            setLeftDrawable(holder.tvLockStatus,R.drawable.icon_1);
        }else if(deviceManagerList.get(position).getStatus() == 5){
            holder.tvLockStatus.setText("上锁异常");
            holder.tvLockStatus.setTextColor(mActivity.getResources().getColor(R.color.color_FE400E));
            setLeftDrawable(holder.tvLockStatus,R.drawable.icon_2);
        }

        if (mAllSelect) {
            for (int i=0;i<deviceManagerList.size();i++){
                hashSet.add(deviceManagerList.get(i).getImei());
            }
            holder.ivSelect.setImageResource(R.drawable.btn_blue_pre);
        } else {
            for (int i=0;i<deviceManagerList.size();i++){
                hashSet.remove(deviceManagerList.get(i).getImei());
            }
            holder.ivSelect.setImageResource(R.drawable.btn_blue);
        }

        holder.ivSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivSelect.setSelected(!holder.ivSelect.isSelected());
                if (holder.ivSelect.isSelected()) {
                    holder.ivSelect.setImageResource(R.drawable.btn_blue_pre);
                    hashSet.add(deviceManagerList.get(position).getImei());
                } else {
                    hashSet.remove(deviceManagerList.get(position).getImei());
                    holder.ivSelect.setImageResource(R.drawable.btn_blue);
                }
            }
        });

        holder.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.ivMore.setImageResource(R.drawable.btn_more_pre);
                SelectDeviceManagerPopupWindow selectDeviceManagerPopupWindow = new SelectDeviceManagerPopupWindow(mActivity,deviceManagerList.get(position), new OnSelectListener() {
                    @Override
                    public void onSelect(String province, String city, String busGroup) {

                    }

                    @Override
                    public void onDismiss() {
                        holder.ivMore.setImageResource(R.drawable.btn_more);
                    }
                });
                selectDeviceManagerPopupWindow.showAsDropDown(holder.linearLayout);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(deviceManagerList==null || deviceManagerList.size()==0){
            return 0;
        }else {
            return deviceManagerList.size();
        }
    }

    static class DeviceManagerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_imei)
        TextView tvImei;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_idcard)
        TextView tvIdcard;
        @BindView(R.id.tv_phone)
        TextView tvPhone;
        @BindView(R.id.tv_lock_status)
        DrawableCenterTextView tvLockStatus;
        @BindView(R.id.tv_net_status)
        DrawableCenterTextView tvNetStatus;
        @BindView(R.id.iv_more)
        ImageView ivMore;
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.linearLayout)
        LinearLayout linearLayout;


        public DeviceManagerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
