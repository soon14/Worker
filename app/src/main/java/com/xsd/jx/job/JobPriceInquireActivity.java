package com.xsd.jx.job;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.xsd.jx.R;
import com.xsd.jx.adapter.JobSearchAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.AddrBean;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.JobSearchBean;
import com.xsd.jx.databinding.ActivityJobPriceInquireBinding;
import com.xsd.jx.listener.OnAddr2Listener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.SoftInputUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * 查工价
 * 目前开通湖北，重庆地区
 */
public class JobPriceInquireActivity extends BaseBindBarActivity<ActivityJobPriceInquireBinding> {
    private JobSearchAdapter mAdapter = new JobSearchAdapter();
    private int selectCityId=1682;//默认湖北省-武汉市
    @Override
    protected int getLayoutId() {
        return R.layout.activity_job_price_inquire;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        checkPrice();

    }

    private void onEvent() {
        db.etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    //开始搜索keyWord相关内容
                    SoftInputUtils.closeSoftInput(JobPriceInquireActivity.this);
                    checkPrice();
                }
                return false;
            }
        });
        db.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyWord = db.etSearch.getText().toString();
                SoftInputUtils.closeSoftInput(JobPriceInquireActivity.this);
                checkPrice();
            }
        });
        db.tvAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopShowUtils.showAddrSelect(JobPriceInquireActivity.this, db.layoutSearch, new OnAddr2Listener() {
                    @Override
                    public void onAddrSelect(AddrBean province, AddrBean city) {
                        String address = province.getName() +"-"+ city.getName();
                        db.tvAddr.setText(address);
                        selectCityId = city.getId();
                        checkPrice();
                    }
                });
            }
        });
    }

    private void initView() {
        tvTitle.setText("查工价");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        AdapterUtils.setEmptyDataView(mAdapter);
    }

//    private ProvinceSelectPop provinceSelectPop;
//    private void showAddr() {
//        if (provinceSelectPop==null){
//            provinceSelectPop = new ProvinceSelectPop(this);
//            provinceSelectPop.setListener(new ProvinceSelectPop.OnCityListener() {
//                @Override
//                public void onCitySelect(AddrBean selectCity) {
//                    selectCityId=selectCity.getId();
//                    db.tvAddr.setText(selectCity.getName());
//                    checkPrice();
//                }
//            });
//            new XPopup.Builder(this)
//                    .atView(db.layoutSearch)
//                    .asCustom(provinceSelectPop)
//            .show();
//        }else {
//            provinceSelectPop.show();
//        }
//    }

    private void checkPrice() {
        String s = db.etSearch.getText().toString();
        dataProvider.work.price(selectCityId,s)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<List<JobSearchBean>>>() {
                    @Override
                    protected void onSuccess(BaseResponse<List<JobSearchBean>> baseResponse) {
                        List<JobSearchBean> data = baseResponse.getData();
                        mAdapter.setList(data);
                        if (data==null||data.size()==0){
                            ToastUtil.showLong("暂无此工种工价！");
                        }
                    }
                });
    }
}