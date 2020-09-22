package com.xsd.jx.mine;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.DatePicker;
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
import com.luck.picture.lib.style.PictureParameterStyle;
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
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.jx.utils.PopShowUtils;
import com.xsd.utils.L;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 编辑资料
 */

public class EditIntroActivity extends BaseBindBarActivity<ActivityEditIntroBinding> {
    private int mYear, mMonth, mDay;//生日年月日
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        final String data = year+"年"+(month + 1) + "月" + dayOfMonth + "日";
                        setBirthday(data);
                    }
                },
                mYear, mMonth, mDay);
        Calendar ca = Calendar.getInstance();

//        DatePicker datePicker = datePickerDialog.getDatePicker();
//        datePicker.setMaxDate(ca.getTimeInMillis());
//        datePicker.setMinDate(Calendar.getInstance().getTimeInMillis());
//        datePicker.setCalendarViewShown(true);
//        datePicker.setSpinnersShown(true);
        datePickerDialog.show();
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

        Calendar d = Calendar.getInstance();

        //也可以用compareTo方法比较,返回一个int，之前-1，之后1，相等0
        d.add(Calendar.YEAR,1);
        L.e("c时间是否在d之前："+c.compareTo(d));

        c.clear(Calendar.MONTH);//清除月份，并以最小月份代替
        L.e("d时间是否在c之前："+new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime()));

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
        PictureParameterStyle pictureParameterStyle = new PictureParameterStyle();
        pictureParameterStyle.pictureStatusBarColor = 0xFF393a3e;
        pictureParameterStyle.pictureTitleBarBackgroundColor = 0xFF393a3e;
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .isWeChatStyle(true)
                .selectionMode(PictureConfig.SINGLE)
                .isWithVideoImage(true)
                .isPreviewImage(true)
                .isCamera(true)
                .isCompress(true)
                .compressSavePath(this.getExternalCacheDir().getPath())
                .setPictureStyle(pictureParameterStyle)
                .forResult(new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        headFile = result.get(0).getCompressPath();
                        uploadAvatar();
                    }

                    @Override
                    public void onCancel() {
                    }
                });
    }

    private void uploadAvatar() {
        //TODO 上传头像，格式暂时不支持jpeg,调试中...
        if (TextUtils.isEmpty(headFile)) return;
        L.e("上传的图片路径==" + headFile);
        File file = new File(headFile);
        RequestBody fileRQ = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), fileRQ);
        dataProvider.user.uploadAvatar(part)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        DataBindingAdapter.avatar(db.ivHead, headFile);
                        Apollo.emit(EventStr.UPDATE_USER_INFO);
                    }
                });
    }


    private void setName(String name) {
        setUserInfo("name", name, db.tvName);
    }

    private void setWorkYears(String workYears) {
        setUserInfo("workYears", workYears, db.tvWorkYears);
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