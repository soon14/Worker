package com.xsd.jx.mine;

import android.os.Bundle;
import android.os.Handler;
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
import com.lxj.xpopup.core.BasePopupView;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.custom.BottomNationSelecterPop;
import com.xsd.jx.custom.GlideEngine;
import com.xsd.jx.databinding.ActivityRealNameAuthBinding;
import com.xsd.jx.utils.AliyunOSSUtils;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.jx.utils.UserUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.FileUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.List;

public class RealNameAuthActivity extends BaseBindBarActivity<ActivityRealNameAuthBinding> {
    String avatar ="https://www.yudaotu.com/images/2020/09/23/ZEWJ5y.jpg";
    private String nation = "汉族";
    private String workYears = "1~5年";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name_auth;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void initView() {
        tvTitle.setText("实名认证");
        int isCertification = UserUtils.getUser().getIsCertification();//是否实名认证 0否 1是
        if (isCertification==1){
            db.layoutInfos.setVisibility(View.GONE);
            db.layoutIsAuth.setVisibility(View.VISIBLE);
        }

    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()) {
                case R.id.iv_head:
                    getPermissionOfTakePhoto();
                    break;
                case R.id.tv_nation:
                    showNationList();
                    break;
                case R.id.tv_work_exp:
                    PopShowUtils.showWorkExp(db.tvWorkExp, (position, text) -> db.tvWorkExp.setText(text));
                    break;
                case R.id.tv_submit:
                    submit();
                    break;
            }
        });
    }

    private void submit() {
        if (EditTextUtils.isEmpty(db.etName,db.etCardNumber))return;
        String name = db.etName.getText().toString();
        String idCard = db.etCardNumber.getText().toString();
        dataProvider.user.certification(avatar,name,idCard,nation,workYears)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        Apollo.emit(EventStr.UPDATE_USER_INFO);
                        finish();
                    }
                });
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
                            XXPermissions.startPermissionActivity(RealNameAuthActivity.this, denied);
                        } else {
                            ToastUtil.showLong("获取拍照权限失败");
                        }
                    }
                });
    }

    private void tackPhoto(){
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
                        DataBindingAdapter.bindImageRoundUrl(db.ivHead,compressPath,6);
                        AliyunOSSUtils.getInstance().uploadAvatar(RealNameAuthActivity.this, compressPath, new AliyunOSSUtils.UploadImgListener() {
                            @Override
                            public void onUpLoadComplete(String url) {
                                L.e("图片上传完成=="+url);
                                avatar = url;

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

    private BasePopupView nationPop;
    private void showNationList() {
        if (nationPop==null){
            nationPop = new XPopup.Builder(this)
                    .asCustom(new BottomNationSelecterPop(this, nationName -> db.tvNation.setText(nationName)))
                    .show();
            new Handler().postDelayed(() -> {
                if (!nationPop.isShow())
                    nationPop.show();
            },300);
        }else
            nationPop.show();
    }







}