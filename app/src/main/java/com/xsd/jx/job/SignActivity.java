package com.xsd.jx.job;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsxiao.apollo.core.Apollo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CheckResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.custom.GlideEngine;
import com.xsd.jx.custom.SignPop;
import com.xsd.jx.databinding.ActivitySignBinding;
import com.xsd.jx.listener.OnSignTackPicListener;
import com.xsd.jx.utils.AliyunOSSUtils;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.FileUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.SpannableStringUtils;
import com.xsd.utils.TimeUtils;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.List;

/**
 * 工人端：
 * 【（考勤打卡）考勤签到】>>考勤记录{@link SignListActivity}
 */
public class SignActivity extends BaseBindBarActivity<ActivitySignBinding> {

    private boolean isUpWork=true;//是否应该上工打卡
    private String picPath;//上工图片地址
    private int workId;
    private String mobile;//上工图片地址
    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
        loadData();
    }
    private void initView() {
        tvTitle.setText("考勤签到");
        tvRight.setText("考勤记录");
        //姓名
        UserInfo user = UserUtils.getUser();
        db.tvName.setText(user.getName());
        //今天日期
        String todayDate = TimeUtils.getTodayDate();
        db.tvToday.setText(todayDate);
        AnimUtils.potView(db.ivPot);
        AnimUtils.potView(db.ivPot2);

        db.radarViewDown.setVisibility(View.GONE);
    }

    private void loadData() {
        dataProvider.work.check()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<CheckResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<CheckResponse> baseResponse) {
                        CheckResponse data = baseResponse.getData();
                        db.setItem(data);
                        mobile = data.getMobile();
                        workId = data.getWorkId();
                        db.tvAddress.setText("上工地点："+data.getAddress());

                        db.layoutNotWorking.setVisibility(View.GONE);
                        db.tvContact.setVisibility(View.VISIBLE);
                        db.layoutScrollView.setVisibility(View.VISIBLE);
                        mHandler.sendEmptyMessage(0);
                        String signInTime = data.getSignInTime();
                        String signOutTime = data.getSignOutTime();
                        isUpWork = TextUtils.isEmpty(signInTime);//是否是上工打卡
                        if (!TextUtils.isEmpty(signInTime)&&TextUtils.isEmpty(signOutTime)){
                            db.radarViewDown.setVisibility(View.VISIBLE);
                        }else {
                            db.radarViewDown.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onErr(String err) {
                        super.onErr(err);
                        db.layoutNotWorking.setVisibility(View.VISIBLE);
                        db.layoutScrollView.setVisibility(View.GONE);
                        db.tvContact.setVisibility(View.GONE);
                    }
                });
    }



    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            setTime();
            mHandler.sendEmptyMessageDelayed(0,1000);
            return false;
        }
    });

    private void setTime() {
        String time = TimeUtils.getTime("HH:mm:ss");
        SpannableStringBuilder spanStrUp = SpannableStringUtils.getBuilder(isUpWork?"上工打卡\n":"下工打卡\n").setProportion(1.125f).setBold().setForegroundColor(Color.parseColor("#ffffff"))
                .append(time).create();
        if (isUpWork)db.tvSignUp.setText(spanStrUp);
        else db.tvSignDown.setText(spanStrUp);
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.tv_sign_up://上工打卡
                    isUpWork=true;
                    signUp();
                    break;
                case R.id.tv_sign_down://下工打卡
                    isUpWork=false;
                    signUp();
                    break;
                case R.id.tv_contact://联系管理员
                    MobileUtils.callPhone(this,mobile);
                    break;
                case R.id.tv_go_for_work://找工作,点击跳转首页并弹推荐工作
                    finish();
                    Apollo.emit(EventStr.SHOW_PUSH_JOB);
                    break;
            }
        });
        tvRight.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignListActivity.class);
            intent.putExtra("mobile",mobile);
            startActivity(intent);
        });
    }
    private SignPop signPop;
    private void signUp() {
        if (signPop==null){
            signPop = new SignPop(this, new OnSignTackPicListener() {
                @Override
                public void tackPicComplete(String content) {
                    if (TextUtils.isEmpty(content))content=isUpWork?"开始上工":"今日工作已完成";
                    doCheck(content);
                }
                @Override
                public void tackPic() {
                    getPermissionOfTakePhoto();
                }
            },isUpWork);
            new XPopup.Builder(this)
                    .asCustom(signPop)
                    .show();
        }else {
            signPop.show();
        }
    }

    private void getPermissionOfTakePhoto() {
        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            tackPhoto();
                        } else {
                            ToastUtil.showLong("获取权限成功，部分权限未正常授予");
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.showLong("被永久拒绝授权，请手动授予拍照权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SignActivity.this, denied);
                        } else {
                            ToastUtil.showLong("获取拍照权限失败");
                        }
                    }
                });
    }

    public void tackPhoto() {
        PictureSelector.create(this)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isCompress(true)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        // onResult Callback
                        LocalMedia localMedia = result.get(0);
                        String compressPath = localMedia.getCompressPath();
                        L.e("图片地址=="+compressPath+" 图片大小=="+ FileUtils.getFileSize(new File(compressPath)));

                        AliyunOSSUtils.getInstance().uploadAvatar(SignActivity.this, compressPath, new AliyunOSSUtils.UploadImgListener() {
                            @Override
                            public void onUpLoadComplete(String url) {
                                L.e("图片上传完成=="+url);
                                picPath = url;
                                signPop.setIvTackPic(url);

                            }

                            @Override
                            public void onUpLoadProgress(int progress) {
                                L.e("图片上传中=="+progress);
                            }
                        });


                    }

                    @Override
                    public void onCancel() {
                        // onCancel Callback
                    }
                });
    }

    /**
     * 考勤打卡提交
     */
    private void doCheck(String desc){
        dataProvider.work.doCheck(workId,picPath,desc)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        if (isUpWork){
                            if (signPop!=null) signPop=null;
                            isUpWork=false;
                        }else {
                            isUpWork=true;
                        }
                        loadData();
                    }
                });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler!=null){
            mHandler.removeMessages(0);
            mHandler=null;
        }
    }
}