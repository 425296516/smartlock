package com.anlida.smartlock.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.anlida.smartlock.R;
import com.anlida.smartlock.adapter.WarnRecordAdapter;
import com.anlida.smartlock.base.FMSubscriber;
import com.anlida.smartlock.base.LazyLoadFragment;
import com.anlida.smartlock.event.UpdateWarnRecord;
import com.anlida.smartlock.model.req.ReqSearchWarning;
import com.anlida.smartlock.model.req.ReqWarnRecord;
import com.anlida.smartlock.model.resp.RespWarnRecord;
import com.anlida.smartlock.network.HttpClient;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class WarningRecordFragment extends LazyLoadFragment {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.recycler_view_result)
    RecyclerView recyclerViewResult;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.tv_no_warn_data)
    TextView tvNoWarnData;
    @BindView(R.id.tv_no_warn_result_data)
    TextView tvNoWarnResultData;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_warning_record;
    }

    @Override
    public void initData() {
        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setCursorVisible(true);
            }
        });
    }


    @Override
    public void initView() {
        initRecyclerView();
    }

    @Override
    protected boolean isNeedReload() {
        return true;
    }

    @OnClick({R.id.tv_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search:

                getSearchWarningRecord(1, 300, etSearch.getText().toString(), 1);
                getSearchWarningRecord(1, 300, etSearch.getText().toString(), 2);

                break;
        }
    }

    @Override
    protected boolean needEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UpdateWarnRecord event) {
        getWarningRecord(1, 300);
    }


    @Override
    protected void loadData() {
        etSearch.setCursorVisible(false);

        getWarningRecord(1, 300);
    }

    @Override
    protected String description() {
        return null;
    }

    WarnRecordAdapter warnRecordAdapter, wrAdapterResult;

    public void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        warnRecordAdapter = new WarnRecordAdapter(getActivity(), 1);//默认创建一个数组，不创建会有空指针异常
        recyclerView.setAdapter(warnRecordAdapter);


        LinearLayoutManager llmResult = new LinearLayoutManager(getActivity());
        recyclerViewResult.setLayoutManager(llmResult);
        wrAdapterResult = new WarnRecordAdapter(getActivity(), 2);//默认创建一个数组，不创建会有空指针异常
        recyclerViewResult.setAdapter(wrAdapterResult);
    }


    private void getWarningRecord(int pageNum, int pageSize) {
        ReqWarnRecord reqDeviceManager = new ReqWarnRecord(pageNum, pageSize);
        HttpClient.getInstance().service.getWarningRecord(reqDeviceManager)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respWarnRecord) {
                        List<RespWarnRecord.DataBean.ListBean> listBeans = new ArrayList<>();
                        List<RespWarnRecord.DataBean.ListBean> dealBeans = new ArrayList<>();

                        for (int i = 0; i < respWarnRecord.getData().getList().size(); i++) {
                            if ("1".equals(respWarnRecord.getData().getList().get(i).getStatus())) {//status 1未处理 2已处理
                                listBeans.add(respWarnRecord.getData().getList().get(i));
                            } else if ("2".equals(respWarnRecord.getData().getList().get(i).getStatus())) {
                                dealBeans.add(respWarnRecord.getData().getList().get(i));
                            }
                        }

                        if (listBeans.size() == 0) {
                            tvNoWarnData.setVisibility(View.VISIBLE);
                            warnRecordAdapter.setData(listBeans);
                        } else {
                            tvNoWarnData.setVisibility(View.GONE);
                            warnRecordAdapter.setData(listBeans);
                        }

                        if (dealBeans.size() == 0) {
                            tvNoWarnResultData.setVisibility(View.VISIBLE);
                            wrAdapterResult.setData(dealBeans);
                        } else {
                            tvNoWarnResultData.setVisibility(View.GONE);
                            wrAdapterResult.setData(dealBeans);
                        }
                    }
                });

    }

    private void getSearchWarningRecord(int pageNum, int pageSize, String search, int status) {
        ReqSearchWarning reqSearchWarning = new ReqSearchWarning(pageNum, pageSize, search, status);
        HttpClient.getInstance().service.getSearchWarningRecord(reqSearchWarning)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FMSubscriber<RespWarnRecord>() {
                    @Override
                    public void onNext(RespWarnRecord respDeviceManager) {
                        if (0 == respDeviceManager.getCode()) {
                            if (status == 1) {
                                warnRecordAdapter.setData(respDeviceManager.getData().getList());
                            } else {
                                wrAdapterResult.setData(respDeviceManager.getData().getList());
                            }
                        }
                    }
                });

    }
}
