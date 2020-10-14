package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.xsd.jx.LoginActivity;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.databinding.FragmentOrderBinding;
import com.xsd.jx.listener.OnTabClickListener;
import com.xsd.jx.order.OrderListActivity;
import com.xsd.jx.order.OrderPresenter;
import com.xsd.jx.order.OrderView;
import com.xsd.jx.utils.TabUtils;
import com.xsd.jx.utils.UserUtils;

import java.util.Arrays;

/**
 * Date: 2020/1/3
 * author: SmallCake
 * 首页-订单
 */
public class OrderFragment extends BaseBindFragment<FragmentOrderBinding> implements OrderView {
    private static final String TAG = "OrderFragment";
    private int type=1;//类型 0:全部 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
    private OrderPresenter orderPresenter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {}
    @Override
    protected void onLazyLoad() {
        initView();
        onEvent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Apollo.isBind(this))
        Apollo.bind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Apollo.unBind$core(this);
    }

    private void initView() {
        if (!UserUtils.isLogin()){
            db.layoutNoLogin.setVisibility(View.VISIBLE);
            return;
        }
        db.layoutNoLogin.setVisibility(View.GONE);
        orderPresenter = new OrderPresenter(this);
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
                orderPresenter.setPage();
                orderPresenter.loadData();
            }
        });
    }

    private void onEvent() {
        db.layoutNoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goActivity(LoginActivity.class);
            }
        });

        db.tvOrderComment.setOnClickListener(view -> {
            if (!UserUtils.isLogin())return;
            goActivity(OrderListActivity.class,7);
        });//待评价
        db.tvOrderAll.setOnClickListener(view -> {
            if (!UserUtils.isLogin())return;
            goActivity(OrderListActivity.class,0);
        });//0全部订单1待评价

    }

    @Override
    public RecyclerView getRecyclerView() {
        return db.recyclerView;
    }

    @Override
    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return db.refreshLayout;
    }


    @Override
    public int getType() {
        return type;
    }

    @Receive(EventStr.LOGIN_SUCCESS)
    public void loginSuccess(){
        initView();
        orderPresenter.setPage();
        orderPresenter.loadData();
    }
    @Receive(EventStr.LOGIN_OUT)
    public void loginOut(){
        db.layoutNoLogin.setVisibility(View.VISIBLE);
    }
    @Receive(EventStr.UPDATE_ORDER_LIST)
    public void updateOrder(){
        orderPresenter.setPage();
        orderPresenter.loadData();
    }

    @Override
    public BaseActivity getBaseActivity() {
        return (BaseActivity) this.getActivity();
    }
}
