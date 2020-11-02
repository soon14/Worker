package com.xsd.jx.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.databinding.ActivityOrderInfoBinding;
import com.xsd.jx.job.JobInfoActivity;
import com.xsd.jx.mine.CommentActivity;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.ClipboardUtils;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.ToastUtil;

/**
 * 报名中
 * 待开工
 * 工期中
 * 待结算
 * 待评价
 * 全部订单：除了上面5个还有：已完成，已取消，已招满
 */
public class OrderInfoActivity extends BaseBindActivity<ActivityOrderInfoBinding> {
    private int id;//订单id
    private int workId;//招工信息id
    private String sn;//订单编号
    private String employerPhone;//雇主手机号
    private OrderBean item;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }

    private void loadData() {
        dataProvider.order.detail(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<OrderBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<OrderBean> orderBeanBaseResponse) {
                        item = orderBeanBaseResponse.getData();
                        db.setItem(item);
                        workId = item.getListId();
                        employerPhone = item.getEmployerPhone();
                        sn = item.getSn();
                        int type = item.getStatus();//状态 1:未确认 2:待开工 3:已招满（被拒绝）4:已取消 5:进行中 6:待结算 7:待评价 8:已完成
                        switch (type){
                            case 1:
                                db.tvStatus.setText("报名中");
                                db.tvDesc.setText("请耐心等待雇主确认，确认通过后即可上工");
                                db.tvPersionNum.setText("报名人数：15人");
                                db.tvCancelJoin.setVisibility(View.VISIBLE);
                                db.tvContactEmployer.setVisibility(View.GONE);
                                db.layoutCenter.setVisibility(View.GONE);
                                db.tvConfirmTime.setVisibility(View.GONE);
                                break;
                            case 2:
                                db.tvStatus.setText("待开工");
                                db.tvDesc.setText("请按要求完成工作后才能结算工资");
                                db.tvConfirmTime.setVisibility(View.GONE);
                                break;
                            case 5:
                                db.tvStatus.setText("工期中");
                                db.tvDesc.setText("请按要求完成工作后才能结算工资");
                                db.tvConfirmTime.setVisibility(View.GONE);
                                break;
                            case 6:
                                db.tvStatus.setText("待结算");
                                db.tvDesc.setText("您已完成工作，雇主正在为您打款，您辛苦了");
                                db.tvPersionNum.setText("待结算人数：15人");
                                db.tvConfirmTime.setVisibility(View.GONE);
                                break;
                            case 7:
                                db.tvStatus.setText("待评价");
                                db.tvDesc.setText("您的工资已到账，快评价下对方吧");
                                db.tvCommentEmployer.setVisibility(View.VISIBLE);
                                db.tvPayedTime.setVisibility(View.VISIBLE);
                                db.tvContactEmployer.setVisibility(View.GONE);
                                break;
                            case 8:
                                db.tvStatus.setText("已完成");
                                db.tvDesc.setText("您的工资已到账，感谢您的付出");
                                db.tvPayedTime.setVisibility(View.VISIBLE);
                                setTopColor();
                                break;
                            case 4:
                                db.tvStatus.setText("已取消");
                                db.tvCancelTime.setVisibility(View.VISIBLE);
                                db.tvConfirmTime.setVisibility(View.GONE);
                                db.tvDesc.setText("更好的工作在等您");
                                setTopColor();
                                break;
                            case 3:
                                db.tvStatus.setText("已招满");
                                db.tvDesc.setText("快去看看其他工作吧");
                                db.layoutCenter.setVisibility(View.GONE);
                                db.tvConfirmTime.setVisibility(View.GONE);
                                setTopColor();
                                break;

                        }
                    }
                });
    }

    private void onEvent() {
        db.ivBack.setOnClickListener(v->finish());
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.tv_more://查看更多，进入招工详情页
                    goActivity(JobInfoActivity.class,workId);
                    break;
                case R.id.tv_contact_us:
                    PopShowUtils.callUs(OrderInfoActivity.this);
                    break;
                case R.id.tv_cancel_join://取消报名
                    orderCancel();
                    break;
                case R.id.tv_contact_employer://联系雇主
                    MobileUtils.callPhone(OrderInfoActivity.this,employerPhone);
                    break;
                case R.id.tv_comment_employer://评价雇主
                    Intent intent = new Intent(this, CommentActivity.class);
                    intent.putExtra("item",item);
                    startActivity(intent);
                    break;
                case R.id.tv_copy://复制订单编号
                    ClipboardUtils.copy(sn);
                    break;
            }
        });
    }



    private void initView() {
        db.toolbar.setTitleTextColor(Color.WHITE);
        db.toolbar.setSubtitleTextColor(Color.WHITE);
        id = getIntent().getIntExtra("type", 0);

    }

    private void setTopColor() {
        int gray = Color.parseColor("#9A9A9A");
        db.appbar.setBackgroundColor(gray);
        db.layoutHead.setContentScrimColor(gray);
        db.layoutBtns.setVisibility(View.GONE);
        db.tvWorkPrice.setTextColor(gray);
    }

    //取消订单id
    private void orderCancel() {
        dataProvider.order.cancel(id)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.UPDATE_ORDER_LIST);
                    }
                });

    }
}