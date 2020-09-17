package com.xsd.jx.mine;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;

import androidx.core.content.FileProvider;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsxiao.apollo.core.Apollo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.custom.BottomNationSelecterPop;
import com.xsd.jx.databinding.ActivityRealNameAuthBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.FileNameUtils;
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
    String avatar ="https://upload.jianshu.io/users/upload_avatars/1503465/9d96f64dda43.png?imageMogr2/auto-orient/strip|imageView2/1/w/240/h/240";
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
                    PopShowUtils.showWorkExp(db.tvWorkExp, new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            db.tvWorkExp.setText(text);
                        }
                    });
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

    private static final int OPEN_CAMERA = 0X008;
    private String  headFile;
    public void tackPhoto() {
        headFile = FileNameUtils.getFileName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", new File(headFile));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
        }else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(headFile)));
        }
        startActivityForResult(intent, OPEN_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        L.e("回调==requestCode=="+requestCode);
        if (requestCode == OPEN_CAMERA && resultCode == RESULT_OK) {
            // 获取相机返回的数据，并转换为图片格式
            L.e("保存的pic路径=="+headFile);
            DataBindingAdapter.bindImageRoundUrl(db.ivHead, headFile,6);
            long fileSize = FileUtils.getFileSize(new File(headFile));
            L.e("fileSize=="+(fileSize/(1024))+"kb");
            //TODO 上传此文件
        }
    }



    BasePopupView nationPop;
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