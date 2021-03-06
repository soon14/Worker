package com.xsd.jx.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.lsxiao.apollo.core.Apollo;
import com.lsxiao.apollo.core.annotations.Receive;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.LoginActivity;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseActivity;
import com.xsd.jx.base.BaseBindFragment;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.pop.BottomSharePop;
import com.xsd.jx.databinding.FragmentMineBinding;
import com.xsd.jx.manager.GetWorkersActivity;
import com.xsd.jx.mine.FavWorksActivity;
import com.xsd.jx.mine.FeedbackActivity;
import com.xsd.jx.mine.HelpRegistActivity;
import com.xsd.jx.mine.MessageActivity;
import com.xsd.jx.mine.PartnerActivity;
import com.xsd.jx.mine.RealNameAuthActivity;
import com.xsd.jx.mine.RecommendListActivity;
import com.xsd.jx.mine.ResumeActivity;
import com.xsd.jx.mine.SetActivity;
import com.xsd.jx.mine.WalletActivity;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;

/**
 * Date: 2020/1/3
 * author: SmallCake
 */
public class MineFragment extends BaseBindFragment<FragmentMineBinding> {
    private static final String TAG = "MineFragment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void onBindView(View view, ViewGroup container, Bundle savedInstanceState) {
    }

    @Override
    protected void onLazyLoad() {
        initView();
        onEvent();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (UserUtils.isLogin())loadUserInfo();
        }
    }


    private void initView() {
        AnimUtils.floatView(db.ivLq);
        if (!UserUtils.isLogin()){
            db.layoutNoLogin.setVisibility(View.VISIBLE);
        }
    }
    @Receive(EventStr.LOGIN_OUT)
    public void loginOut(){
        db.layoutNoLogin.setVisibility(View.VISIBLE);
    }

    //用户详情
    @Receive({EventStr.UPDATE_USER_INFO,EventStr.LOGIN_SUCCESS})
    public void loadUserInfo() {
        if (!UserUtils.isLogin()){
            db.layoutNoLogin.setVisibility(View.VISIBLE);
        }else {
            db.layoutNoLogin.setVisibility(View.GONE);
        }
        dataProvider.user.info()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UserInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UserInfoResponse> baseResponse) {
                        UserInfoResponse data = baseResponse.getData();
                        UserUtils.saveUser(data);
                        db.setItem(data.getInfo());
                    }
                });
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


    private void onEvent() {
        db.layoutNoLogin.setOnClickListener(v -> goActivity(LoginActivity.class));
        db.setClicklistener(v -> {
            switch (v.getId()) {
                case R.id.iv_set:
                    goActivity(SetActivity.class);
                    break;
                case R.id.iv_head:
                case R.id.tv_name:
                case R.id.tv_click_edit:
                case R.id.layout_head:
                    goActivity(ResumeActivity.class);
                    break;
                case R.id.tv_top_recruit:
                case R.id.tv_bottom_recruit:
                    if (!UserUtils.isCertification()){
                        PopShowUtils.showRealNameAuth((BaseActivity) this.getActivity());
                        return;
                    }
                    goActivity(GetWorkersActivity.class);
                    break;
                case R.id.layout_money:
                    goActivity(WalletActivity.class);
                    break;
                case R.id.tab1:
                    goActivity(HelpRegistActivity.class);
                    break;
                case R.id.tab2:
                    showShare();
                    break;
                case R.id.tab3:
                    goActivity(RecommendListActivity.class);
                    break;
                case R.id.tab4:
                    goActivity(FavWorksActivity.class);
                    break;
                case R.id.tab5:
                    goActivity(RealNameAuthActivity.class);
                    break;
                case R.id.tab6:
                    goActivity(FeedbackActivity.class);
                    break;
                case R.id.tab7:
                    goActivity(PartnerActivity.class);
                    break;
                case R.id.tab8:
                    PopShowUtils.callUs(this.getActivity());
                    break;
                case R.id.tab9:
                    goActivity(MessageActivity.class);
                    break;
            }
        });
    }



    private void showShare() {
        new XPopup.Builder(this.getActivity())
                .asCustom(new BottomSharePop((BaseActivity) this.getActivity()))
                .show();
    }
}
