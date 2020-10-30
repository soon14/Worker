package com.xsd.jx.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.RecommendAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.RecommendResponse;
import com.xsd.jx.pop.BottomSharePop;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.jx.listener.OnAdapterListener;
import com.xsd.jx.utils.AdapterUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.SpannableStringUtils;
import com.xsd.utils.ToastUtil;

import java.util.List;

/**
 * 推荐的工友
 */
public class RecommendListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private RecommendAdapter mAdapter = new RecommendAdapter();
    private int page=1;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
        onEvent();
    }

    private void onEvent() {
        AdapterUtils.onAdapterEvent(mAdapter, db.refreshLayout, new OnAdapterListener() {
            @Override
            public void loadMore() {
                page++;
                loadData();
            }
            @Override
            public void onRefresh() {
                page=1;
                loadData();
            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_remind);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_remind:
                        remind();
                        break;
                }
            }
            //TODO 提醒上工
            private void remind() {
                ToastUtil.showLong("提醒上工成功！");
            }
        });
    }

    private void loadData() {
        dataProvider.user.recommend(page)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<RecommendResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<RecommendResponse> baseResponse) {
                        RecommendResponse data = baseResponse.getData();
                        List<RecommendResponse.ItemsBean> items = data.getItems();
                        if (page==1){
                            SpannableStringBuilder spanStr = SpannableStringUtils.getBuilder(data.getCount()+"位工友共帮您赚了")
                                    .append(data.getEarned()+"").setForegroundColor(Color.parseColor("#FFF000"))
                                    .append("元")
                                    .create();
                            tvDesc.setText(spanStr);
                        }

                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            mAdapter.getLoadMoreModule().loadMoreEnd();
                        }

                    }
                });
    }
    private TextView tvDesc;
    private void initView() {
        tvTitle.setText("我推荐的工友");
        tvRight.setText("奖励记录");
        tvRight.setOnClickListener(view -> goActivity(AwardActivity.class));
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View viewHead = LayoutInflater.from(this).inflate(R.layout.header_invite_list, null);
         tvDesc = viewHead.findViewById(R.id.tv_desc);
        ImageView ivInvite = viewHead.findViewById(R.id.iv_invite);
        ivInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        db.layoutRoot.addView(viewHead,1);

    }
    private void showShare() {
        new XPopup.Builder(this)
                .asCustom(new BottomSharePop(this))
                .show();
    }
}