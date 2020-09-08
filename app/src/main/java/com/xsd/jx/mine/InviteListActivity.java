package com.xsd.jx.mine;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.adapter.InviteAdapter;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.InviteResponse;
import com.xsd.jx.custom.BottomSharePop;
import com.xsd.jx.databinding.ActivityRecyclerviewBinding;
import com.xsd.utils.SpannableStringUtils;

/**
 * 推荐的工友
 */
public class InviteListActivity extends BaseBindBarActivity<ActivityRecyclerviewBinding> {
    private InviteAdapter mAdapter = new InviteAdapter();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView() {
        tvTitle.setText("我推荐的工友");
        db.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.recyclerView.setAdapter(mAdapter);
        View viewHead = LayoutInflater.from(this).inflate(R.layout.header_invite_list, null);
        TextView tvDesc = viewHead.findViewById(R.id.tv_desc);
        ImageView ivInvite = viewHead.findViewById(R.id.iv_invite);
        SpannableStringBuilder spanStr = SpannableStringUtils.getBuilder("3位工友共帮您赚了").setProportion(1.5f).setBold()
                .append("485.52").setProportion(1.5f).setBold().setForegroundColor(Color.RED)
                .append("元\n").setProportion(1.5f).setBold()
                .append("工友越多收益越快，赶紧把身边的工友邀请到匠心吧~")
                .create();
        tvDesc.setText(spanStr);
        ivInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
        db.layoutRoot.addView(viewHead,1);
        for (int i = 0; i < 20; i++) {
            mAdapter.addData(new InviteResponse());
        }
    }
    private void showShare() {
        new XPopup.Builder(this)
                .asCustom(new BottomSharePop(this))
                .show();
    }
}