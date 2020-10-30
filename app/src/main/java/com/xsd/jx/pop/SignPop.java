package com.xsd.jx.pop;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lxj.xpopup.core.CenterPopupView;
import com.xsd.jx.R;
import com.xsd.jx.listener.OnSignTackPicListener;
import com.xsd.jx.utils.DataBindingAdapter;
import com.xsd.utils.ToastUtil;

/**
 * Date: 2020/8/20
 * author: SmallCake
 */
public class SignPop extends CenterPopupView {
    private ImageView ivTackPic;
    private boolean isUpPic;//是否已经拍摄了照片
    private boolean isUpWork = true;//是否是拍摄上工照片
    private OnSignTackPicListener listener;


    public SignPop(@NonNull Context context,OnSignTackPicListener listener,boolean isUpWork) {
        super(context);
        this.listener=listener;
        this.isUpWork = isUpWork;
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_sign;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        TextView tvDesc = findViewById(R.id.tv_desc);
        tvDesc.setText(isUpWork?"请上传现场上工照片，若发生纠纷，此将做为重要判定依据。":"请上传现场下工照片，若发生纠纷，此将做为重要判定依据。");
        EditText etContent = findViewById(R.id.et_content);
        etContent.setText(isUpWork?"开始上工":"今日工作已完成");

        etContent.setSelection(etContent.getText().length());
        findViewById(R.id.tv_confirm).setOnClickListener(v -> {
            if (!isUpPic){
                ToastUtil.showLong("请先拍照！");
                return;
            }
            dismiss();
            String s = etContent.getText().toString();
            listener.tackPicComplete(s);

        });
        findViewById(R.id.tv_cancel).setOnClickListener(v->dismiss());
        ivTackPic = findViewById(R.id.iv_tack_pic);
        ivTackPic.setOnClickListener(v -> listener.tackPic());

    }

    public void setIvTackPic(String path) {
        if (ivTackPic!=null){
            isUpPic=true;
            DataBindingAdapter.bindImageRoundUrl(ivTackPic, path,6);
        }
    }

}
