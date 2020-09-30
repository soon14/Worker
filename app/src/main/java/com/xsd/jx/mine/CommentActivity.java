package com.xsd.jx.mine;

import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CommentRequest;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityCommentBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

/**
 * 评价
 * type
 * 0:用户评价雇主
 * 1:雇主评价用户（用工方评价上工者）
 * @param data  {
 *   "allRate": 0,
 *   "content": "string",
 *   "id": 0,
 *   "isAnonymous": 0,
 *   "rate1": 0,
 *   "rate2": 0,
 *   "rate3": 0,
 *   "toUserId": 0,
 *   "userId": 0,
 *   "workId": 0
 * }
 */
public class CommentActivity extends BaseBindBarActivity<ActivityCommentBinding> {
    int userId;
    int toUserId;
    int workId;
    int type;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        db.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });
    }

    private void submit() {
        if (EditTextUtils.isEmpty(db.etContent))return;
        String content = db.etContent.getText().toString();

        if (type==0){

        }else {//我是雇主，评价工人

            int rateAll = (int) db.rateAll.getRating();
            int rate1 = (int) db.rate1.getRating();
            int rate2 = (int) db.rate2.getRating();
            int rate3 = (int) db.rate3.getRating();
            //是否匿名 1:是 2: 否
            int isAnonymous = db.cbIsAnon.isChecked()?1:2;

            CommentRequest obj = new CommentRequest.Builder()
                    .content(content)
                    .allRate(rateAll)
                    .rate1(rate1)
                    .rate1(rate2)
                    .rate1(rate3)
                    .isAnonymous(isAnonymous)
                    .userId(userId)
                    .toUserId(toUserId)
                    .workId(workId)
                    .build();

            String  data =new Gson().toJson(obj);
            L.e("要提交的data=="+data);
            if (true)return;
            dataProvider.server.workComment(workId,data)
                    .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                        @Override
                        protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                            ToastUtil.showLong(baseResponse.getData().getMessage());
                            finish();
                        }
                    });
        }
    }

    private void initView() {
        userId = UserUtils.getUser().getId();
        tvTitle.setText("评价对方");
         type = getIntent().getIntExtra("type", 0);
         workId = getIntent().getIntExtra("workId", 0);
        toUserId = getIntent().getIntExtra("userId", 0);
        if (type==0){
            db.etContent.setText("确认快，老板人好，结款干脆，期待下次合作");
        }else{
            db.etContent.setText("踏实肯干，效率高，质量好，期待下次合作");
        }



    }
}