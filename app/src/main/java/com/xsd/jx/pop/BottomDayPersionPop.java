package com.xsd.jx.pop;

import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.lxj.xpopup.core.BottomPopupView;
import com.xsd.jx.R;
import com.xsd.jx.adapter.DayPersionAdapter;
import com.xsd.jx.bean.DayPersionBean;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.PopBottomDayPersionBinding;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/10/30
 * author: SmallCake
 */
public class BottomDayPersionPop extends BottomPopupView {
    private DayPersionAdapter mAdapter;
    private WorkerBean item;
    public BottomDayPersionPop(@NonNull Context context, WorkerBean item) {
        super(context);
        this.item = item;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_bottom_day_persion;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        PopBottomDayPersionBinding bind = DataBindingUtil.bind(getPopupImplView());
        bind.setItem(item);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        List<DayPersionBean> datas = item.getSettleList();
        mAdapter = new DayPersionAdapter(datas);
        recyclerView.setAdapter(mAdapter);

        mAdapter.addChildClickViewIds(R.id.tv_edit);
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                DayPersionBean item = (DayPersionBean) adapter.getItem(position);
                int num = item.getWorkNum();
                PopShowUtils.showEditNum(BottomDayPersionPop.this.getContext(), num, new ConfirmNumPop.ConfirmListener() {
                    @Override
                    public void onConfirmNum(int num) {
                        ToastUtil.showLong("修改为人数："+num);
                        item.setWorkNum(num);
                        mAdapter.notifyItemChanged(position);

                    }
                });
            }
        });

        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
