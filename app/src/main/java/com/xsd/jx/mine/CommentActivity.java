package com.xsd.jx.mine;

import android.os.Bundle;

import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.OrderBean;
import com.xsd.jx.databinding.ActivityCommentBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.ToastUtil;

/**
 * 评价
 *
 * @param data {
 *             "allRate": 0,
 *             "content": "string",
 *             "id": 0,
 *             "isAnonymous": 0,
 *             "rate1": 0,
 *             "rate2": 0,
 *             "rate3": 0,
 *             "toUserId": 0,
 *             "userId": 0,
 *             "workId": 0
 *             }
Intent intent = new Intent(activity, CommentActivity.class);
intent.putExtra("workId",item.getListId());
intent.putExtra("toUserId",item.getEmployerId());
activity.startActivity(intent);
 */
public class CommentActivity extends BaseBindBarActivity<ActivityCommentBinding> {

    private OrderBean item;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        db.tvSubmit.setOnClickListener(v -> submit());
    }

    private void submit() {
        if (EditTextUtils.isEmpty(db.etContent)) return;
        String content = db.etContent.getText().toString();
        int allRate = (int) db.rateAll.getRating();
        int rate1 = (int) db.rate1.getRating();
        int rate2 = (int) db.rate2.getRating();
        int rate3 = (int) db.rate3.getRating();
        //是否匿名 1:是 2: 否
        int isAnonymous = db.cbIsAnon.isChecked() ? 1 : 2;
        /**
         @param joinId           报名ID
         @param toUserId         雇主ID
         @param workId           工作ID
         @param content          评价内容
         @param rate1            结算效率评分
         @param rate2            确认效率评分
         @param rate3            态度评分
         @param allRate          总体评分
         @param isAnonymous      是否匿名 1:是 2: 否
         */
        int joinId = item.getId();
        int toUserId = item.getEmployerId();
        int workId = item.getListId();
        dataProvider.order.comment(joinId,toUserId,workId,content,rate1,rate2,rate3,allRate,isAnonymous)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        Apollo.emit(EventStr.UPDATE_ORDER_LIST);
                    }
                });
    }

    private void initView() {
        tvTitle.setText("评价对方");
        item = (OrderBean) getIntent().getSerializableExtra("item");

    }
}