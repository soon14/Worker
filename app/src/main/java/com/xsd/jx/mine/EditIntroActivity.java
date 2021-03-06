package com.xsd.jx.mine;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.lsxiao.apollo.core.Apollo;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.base.EventStr;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.bean.UserInfo;
import com.xsd.jx.bean.UserInfoResponse;
import com.xsd.jx.custom.GlideEngine;
import com.xsd.jx.databinding.ActivityEditIntroBinding;
import com.xsd.jx.listener.OnNationSelectListener;
import com.xsd.jx.utils.AliyunOSSUtils;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.FileUtils;
import com.xsd.utils.FormatUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 编辑资料
 */

public class EditIntroActivity extends BaseBindBarActivity<ActivityEditIntroBinding> {
    private int mYear, mMonth, mDay;//生日年月日
    private boolean isCertification;//是否实名认证；
    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_intro;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadUserInfo();
        onEvent();
    }

    private void loadUserInfo() {
        dataProvider.user.info()
                .subscribe(new OnSuccessAndFailListener<BaseResponse<UserInfoResponse>>() {
                    @Override
                    protected void onSuccess(BaseResponse<UserInfoResponse> baseResponse) {
                        UserInfoResponse data = baseResponse.getData();
                        UserInfo info = data.getInfo();
                        isCertification = info.getIsCertification()==1;
                        db.setItem(info);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Apollo.emit(EventStr.UPDATE_USER_INFO);
    }

    private void onEvent() {
        db.setClicklistener(v -> {
            switch (v.getId()) {
                case R.id.layout_edit_head:
                    getPermissionOfTakePhoto();
                    break;
                case R.id.layout_name:
                case R.id.tv_name:
                    if (isCertification)return;
                    new XPopup.Builder(this)
                            .asInputConfirm("请输入姓名", "", new OnInputConfirmListener() {
                                @Override
                                public void onConfirm(String text) {
                                    setName(text);
                                }
                            }).show();
                    break;
                case R.id.layout_sex:
                case R.id.tv_sex:
                    if (isCertification)return;
                    new XPopup.Builder(this)
                            .asBottomList("请选择性别", new String[]{"男", "女"}, new OnSelectListener() {
                                @Override
                                public void onSelect(int position, String text) {
                                    setSex(text.equals("男")?1:2);
                                }
                            }).show();
                    break;
                case R.id.layout_birthday:
                case R.id.tv_birthday:
                    if (isCertification)return;
                    showDateDialog();
                    break;
                case R.id.layout_nation:
                case R.id.tv_nation:
                    PopShowUtils.showNationList(this, new OnNationSelectListener() {
                        @Override
                        public void onSelect(String nationName) {
                            setNation(nationName);
                        }
                    });
                    break;
                case R.id.layout_work_exp:
                case R.id.tv_work_years:
                    PopShowUtils.showWorkExp(db.tvWorkYears, new OnSelectListener() {
                        @Override
                        public void onSelect(int position, String text) {
                            setWorkYears(text);
                        }
                    });
                    break;
            }
        });

    }

    private void showDateDialog() {
        new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    mYear = year;
                    mMonth = month;
                    mDay = dayOfMonth;
                    final String data = year+"年"+(month + 1) + "月" + dayOfMonth + "日";
                    setBirthday(data);
                },
                mYear, mMonth, mDay).show();
    }





    private void initView() {
        tvTitle.setText("编辑资料");
        tvRight.setText("保存");
        tvRight.setTextColor(Color.parseColor("#3C78FF"));
        tvRight.setOnClickListener(v -> {
            ToastUtil.showLong("已保存");
            finish();
        });

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }


    private void getPermissionOfTakePhoto() {
        XXPermissions.with(this)
                .permission(Permission.CAMERA)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            getPic();
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

    private String headFile;

    private void getPic() {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isWeChatStyle(true)
                .selectionMode(PictureConfig.SINGLE)
                .isCamera(false)
                .isCompress(true)
                .setOutputCameraPath(EditIntroActivity.this.getExternalCacheDir().getPath())
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        headFile = result.get(0).getCompressPath();
                        L.e("压缩后图片地址==" + headFile + " 图片大小==" + FormatUtils.formatSize(FileUtils.getFileSize(new File(headFile))));
                        //上传头像到阿里云，并编辑头像
                        AliyunOSSUtils.getInstance().uploadAvatar(EditIntroActivity.this, headFile, new AliyunOSSUtils.UploadImgListener() {
                            @Override
                            public void onUpLoadComplete(String url) {
                                DataBindingAdapter.avatar(db.ivHead,headFile);
                                setAvatar(url);
                            }
                            @Override
                            public void onUpLoadProgress(int progress) {
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }



    private void setName(String name) {
        setUserInfo("name", name, db.tvName);
    }

    private void setWorkYears(String workYears) {
        setUserInfo("workYears", workYears, db.tvWorkYears);
    }

    private void setAvatar(String avatar) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("avatar", RequestBody.create(MediaType.parse("text/plain"), avatar));
        dataProvider.user.profile(map)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                    }
                });
    }
    private void setNation(String nation) {
        setUserInfo("nation", nation, db.tvNation);
    }

    private void setBirthday(String birthday) {
        setUserInfo("birthday", birthday, db.tvBirthday);
    }

    private void setSex(int sex) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("sex", RequestBody.create(MediaType.parse("text/plain"), sex + ""));
        dataProvider.user.profile(map)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        db.tvSex.setText(sex == 1 ? "男" : "女");
                    }
                });
    }


    private void setUserInfo(String key, String value, TextView tv) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put(key, RequestBody.create(MediaType.parse("text/plain"), value));
        dataProvider.user.profile(map)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>() {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        tv.setText(value);
                        if (key.equals("name"))Apollo.emit(EventStr.UPDATE_USER_INFO);
                    }
                });
    }

}