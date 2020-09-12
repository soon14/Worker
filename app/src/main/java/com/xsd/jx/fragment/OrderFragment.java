package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.xsd.jx.R;
import com.xsd.jx.adapter.OrderAdapter;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.bean.OrderResponse;
import com.xsd.jx.databinding.FragmentOrderBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.mine.CommentActivity;
import com.xsd.jx.order.OrderAllActivity;
import com.xsd.jx.order.OrderInfoActivity;
import com.xsd.jx.order.OrderWaitCommentActivity;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.TabUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 首页-订单
 */
public class OrderFragment extends BaseBindFragment<FragmentOrderBinding>{
    private static final String TAG = "OrderFragment";
    private OrderAdapter mAdapter = new OrderAdapter();
    private int page=1;
    private int type=1;//类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {

    }

    @Override
    protected void onLazyLoad() {
        initView();
        loadData();
        onEvent();
    }
    private void initView() {
        //类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
        TabUtils.setDefaultTab(this.getContext(), db.tabLayout,  Arrays.asList("报名中","待开工","工期中","待结算"), new OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                switch (position){
                    case 0: type=1;break;
                    case 1: type=2;break;
                    case 2: type=5;break;
                    case 3: type=6;break;
                }
                page=1;
                loadData();
            }
        });
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        db.recyclerView.setAdapter(mAdapter);
        mAdapter.setEmptyView(LayoutInflater.from(this.getContext()).inflate(R.layout.empty_view_nodata,null));

    }

    private void loadData() {
        dataProvider.order.list(page,type)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<OrderResponse>>(db.refreshLayout) {
                    @Override
                    protected void onSuccess(BaseResponse<OrderResponse> baseResponse) {
                        OrderResponse data = baseResponse.getData();
                        List<OrderBean> items = data.getItems();
                        if (items!=null&&items.size()>0){
                            if (page==1)mAdapter.setList(items);else mAdapter.addData(items);
                            mAdapter.getLoadMoreModule().loadMoreComplete();
                        }else {
                            if (page==1){
                                mAdapter.setList(items);
                            }else mAdapter.getLoadMoreModule().loadMoreEnd();
                        }
                    }
                });
    }

    private void onEvent() {
        db.tvOrderComment.setOnClickListener(view -> goActivity(OrderWaitCommentActivity.class));
        db.tvOrderAll.setOnClickListener(view -> goActivity(OrderAllActivity.class));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            OrderBean item = (OrderBean) adapter.getItem(position);
            int itemType = item.getItemType();
            goActivity(OrderInfoActivity.class,itemType);
            switch (itemType){
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
            }
        });
        mAdapter.addChildClickViewIds(R.id.tv_order_comment);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()){
                    case R.id.tv_order_comment:
                        goActivity(CommentActivity.class);
                        break;
                }
            }
        });

        //加载更多
        mAdapter.getLoadMoreModule().setOnLoadMoreListener(() -> {
            page++;
            loadData();
        });

        //下拉刷新
        db.refreshLayout.setOnRefreshListener(() -> {
            page=1;
            loadData();
        });
    }


}
