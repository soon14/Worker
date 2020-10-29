package com.xsd.jx.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.google.gson.Gson;
import com.lsxiao.apollo.core.Apollo;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CommentRequest;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.WorkerBean;
import com.xsd.jx.databinding.ActivityTogetherCommentBinding;
import com.xsd.jx.databinding.ItemWaitCommentWorkersBinding;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 评价工人
 * 一起评价
 Intent intent = new Intent(this, TogetherCommentActivity.class);
 intent.putExtra("workId",item.getId());
 intent.putExtra("workers", (Serializable) item.getWorkers());
 startActivity(intent);
 */
public class TogetherCommentActivity extends BaseBindBarActivity<ActivityTogetherCommentBinding> {
    private int workId;
    private int userId;
    List<WorkerBean> workers;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_together_comment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.tvSubmit.setOnClickListener(v -> commentAll());
    }

    /**
     *  @param workId 用工ID
     *  @param data 评论内容数组json串
     *              {
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
    private void commentAll() {
        List<CommentRequest> objs = new ArrayList<>();
        int childCount = db.layoutWorks.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View viewWorkers = db.layoutWorks.getChildAt(i);
            ItemWaitCommentWorkersBinding bind = DataBindingUtil.bind(viewWorkers);
            int rateAll = (int) bind.rateAll.getRating();
            int rate1 = (int) bind.rate1.getRating();
            int rate2 = (int) bind.rate2.getRating();
            int rate3 = (int) bind.rate3.getRating();
            //是否匿名 1:是 2: 否
            int isAnonymous = bind.cbIsAnon.isChecked()?1:2;
            if (EditTextUtils.isEmpty(bind.etContent))return;
            String content = bind.etContent.getText().toString();
            CommentRequest obj = new CommentRequest.Builder()
                    .content(content)
                    .allRate(rateAll)
                    .rate1(rate1)
                    .rate2(rate2)
                    .rate3(rate3)
                    .isAnonymous(isAnonymous)
                    .userId(userId)
                    .toUserId(workers.get(i).getUserId())
                    .workId(workId)
                    .build();
            objs.add(obj);
        }
        String  data =new Gson().toJson(objs);
        L.e("要提交的data=="+data);
//        if (true)return;
        dataProvider.server.workComment(workId,data)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                        //评价提交后刷新列表
                        Apollo.emit(EventStr.UPDATE_COMMENT_LIST);

                    }
                });
    }

    private void initView() {
        tvTitle.setText("评价工人");
        workId = getIntent().getIntExtra("workId",0);
        workers = (List<WorkerBean>) getIntent().getSerializableExtra("workers");
        userId = UserUtils.getUserInfo().getId();
        if (workers==null)return;
        for (int i = 0; i <workers.size(); i++) {
            View viewWorkers = LayoutInflater.from(this).inflate(R.layout.item_wait_comment_workers, null);
            ItemWaitCommentWorkersBinding bind = DataBindingUtil.bind(viewWorkers);
            bind.setItem(workers.get(i));
            db.layoutWorks.addView(viewWorkers);
        }
    }
}