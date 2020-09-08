package com.xsd.jx.mine;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.databinding.ActivityEditIntroBinding;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.FileNameUtils;
import com.xsd.utils.FileUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.List;

/**
 * 编辑资料
 */

public class EditIntroActivity extends BaseBindBarActivity<ActivityEditIntroBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_intro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        onEvent();
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()){
                case R.id.layout_edit_head:
                    getPermissionOfTakePhoto();
                    break;
                case R.id.tv_work_experience:
                    showWorkExperList();
                    break;
            }
        });

    }

    private void initView() {
        tvTitle.setText("编辑资料");
        tvRight.setText("保存");
        tvRight.setTextColor(Color.parseColor("#3C78FF"));
        tvRight.setOnClickListener(v -> ToastUtil.showLong("已保存"));
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
                            XXPermissions.startPermissionActivity(EditIntroActivity.this, denied);
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
            DataBindingAdapter.bindImageCircleUrl(db.ivHead, headFile);
            long fileSize = FileUtils.getFileSize(new File(headFile));
            L.e("fileSize=="+(fileSize/(1024))+"kb");
            //TODO 上传此文件
        }
    }


    private void showWorkExperList() {
        new XPopup.Builder(this)
                .atView(db.tvWorkExperience)
                .asBottomList("工龄选择",
                        new String[]{"1-3年", "3-5年", "5-10年", "10-15年","15-20年","20-30年"},
                        null,
                        -1,
                        false,
                        new OnSelectListener() {
                            @Override
                            public void onSelect(int position, String text) {
                                db.tvWorkExperience.setText(text);
                            }
                        },
                        0,
                        0).show();
    }
}