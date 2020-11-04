package com.xsd.jx.job;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import androidx.core.content.FileProvider;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.CheckResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.pop.SignPop;
import com.xsd.jx.databinding.ActivitySignBinding;
import com.xsd.jx.listener.OnSignTackPicListener;
import com.xsd.jx.utils.AliyunOSSUtils;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.FileNameUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.FileUtils;
import com.xsd.utils.FormatUtils;
import com.xsd.utils.L;
import com.xsd.utils.MobileUtils;
import com.xsd.utils.SpannableStringUtils;
import com.xsd.utils.TimeUtils;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 工人端：
 * 【考勤签到】>>考勤记录{@link SignListActivity}
 */
public class SignActivity extends BaseBindBarActivity<ActivitySignBinding> {

    private boolean isUpWork = true;//是否应该上工打卡
    private int workId;
    private String mobile;


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
        AnimUtils.potView(db.ivPot);
        AnimUtils.potView(db.ivPot2);

        db.radarViewUp.setVisibility(View.GONE);
        db.radarViewDown.setVisibility(View.GONE);
        //TODO 选择工地
        db.tvAddr.setText("重庆市渝中区石油路102号协信阿卡迪亚");
        db.layoutAddr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] addrs= new String[]{"重庆市渝中区石油路102号协信阿卡迪亚","湖北省武汉市江岸区岸边边"};
                new XPopup.Builder(SignActivity.this)
                        .atView(db.layoutAddr)
                        .hasShadowBg(false)
                        .offsetY(-3)
                        .popupAnimation(PopupAnimation.ScrollAlphaFromTop)
                        .asAttachList(addrs, null, new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                db.tvAddr.setText(text);
                            }
                        },R.layout.pop_site_selector,0).show();
            }
        });

    }

    private void loadData() {
        dataProvider.work.check()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<CheckResponse>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<CheckResponse> baseResponse) {
                        CheckResponse data = baseResponse.getData();
                        db.setItem(data);
                        mobile = data.getMobile();
                        workId = data.getWorkId();
                        //TODO 选择工地

                        String signInTime = data.getSignInTime();
                        String signOutTime = data.getSignOutTime();

                        isUpWork = TextUtils.isEmpty(signInTime);//是否是上工打卡
                        mHandler.sendEmptyMessage(0);


                        db.layoutNotWorking.setVisibility(View.GONE);
                        db.layoutScrollView.setVisibility(View.VISIBLE);
                        db.tvContact.setVisibility(View.VISIBLE);
                        if (TextUtils.isEmpty(signInTime)) {
                            db.radarViewUp.setVisibility(View.VISIBLE);
                        } else {
                            db.radarViewUp.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(signInTime) && TextUtils.isEmpty(signOutTime)) {
                            db.radarViewDown.setVisibility(View.VISIBLE);
                        } else {
                            db.radarViewDown.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    protected void onErr(BaseResponse err) {
//                        super.onErr(err);
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
            mHandler.sendEmptyMessageDelayed(0, 1000);
            return false;
        }
    });

    private void setTime() {
        String time = TimeUtils.getTime("HH:mm:ss");
        SpannableStringBuilder spanStrUp = SpannableStringUtils.getBuilder(isUpWork ? "上工打卡\n" : "下工打卡\n").setProportion(1.125f).setBold().setForegroundColor(Color.parseColor("#ffffff"))
                .append(time).create();
        if (isUpWork) db.tvSignUp.setText(spanStrUp);
        else db.tvSignDown.setText(spanStrUp);
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()) {
                case R.id.tv_sign_up://上工打卡
                    isUpWork = true;
                    signUp();
                    break;
                case R.id.tv_sign_down://下工打卡
                    isUpWork = false;
                    signUp();
                    break;
                case R.id.tv_contact://联系管理员
                    MobileUtils.callPhone(this, mobile);
                    break;
                case R.id.tv_go_for_work://找工作,点击跳转首页并弹推荐工作
                    finish();
                    Apollo.emit(EventStr.SHOW_PUSH_JOB);
                    break;
            }
        });
        tvRight.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignListActivity.class);
            intent.putExtra("mobile", mobile);
            startActivity(intent);
        });
    }

    private SignPop signPop;

    private void signUp() {
        if (signPop == null) {
            signPop = new SignPop(this, new OnSignTackPicListener() {
                @Override
                public void tackPicComplete(String content) {
                    if (TextUtils.isEmpty(content)) content = isUpWork ? "开始上工" : "今日工作已完成";
                    doCheck(content);
                }

                @Override
                public void tackPic() {
                    getPermissionOfTakePhoto();
                }
            }, isUpWork);
            new XPopup.Builder(this)
                    .asCustom(signPop)
                    .show();
        } else {
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

    public static final int REQUEST_CAMERA = 909;
    private String compressPath;//拍照后的图片地址
    public void tackPhoto() {
        compressPath = FileNameUtils.getFileName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", new File(compressPath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(compressPath)));
        }
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CAMERA) {
            L.e("图片地址==" + compressPath + " 图片大小==" + FormatUtils.formatSize(FileUtils.getFileSize(new File(compressPath))));
            if (TextUtils.isEmpty(compressPath)) return;
            Luban.with(this)
                    .load(compressPath)
                    .ignoreBy(100)
                    .setTargetDir(getExternalCacheDir().getPath())//压缩后的图片保存到应用缓存目录
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {}
                        @Override
                        public void onSuccess(File file) {
                            compressPath = file.getPath();
                            L.e("压缩后图片地址==" + file.getPath() + " 图片大小==" + FormatUtils.formatSize(FileUtils.getFileSize(file)));
                            signPop.setIvTackPic(compressPath);
                        }

                        @Override
                        public void onError(Throwable e) {}
                    })
                    .launch();

        }
    }


    /**
     * 考勤打卡提交
     */
    private void doCheck(String desc) {
        if (TextUtils.isEmpty(compressPath)) {
            return;
        }
        //图片上传完成后，提交打卡
        AliyunOSSUtils.getInstance().sign(SignActivity.this, compressPath, new AliyunOSSUtils.UploadImgListener() {
            @Override
            public void onUpLoadComplete(String url) {
                dataProvider.work.doCheck(workId, url, desc)
                        .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                            @Override
                            protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                                ToastUtil.showLong(baseResponse.getData().getMessage());
                                if (isUpWork) {
                                    if (signPop != null) signPop = null;
                                    isUpWork = false;
                                } else {
                                    isUpWork = true;
                                }
                                loadData();
                            }
                        });
            }

            @Override
            public void onUpLoadProgress(int progress) {
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeMessages(0);
            mHandler = null;
        }
    }
}