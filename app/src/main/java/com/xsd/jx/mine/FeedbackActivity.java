package com.xsd.jx.mine;

import android.graphics.drawable.AnimationDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.xsd.jx.R;
import com.xsd.jx.base.BaseBindBarActivity;
import com.xsd.jx.bean.BaseResponse;
import com.xsd.jx.bean.MessageBean;
import com.xsd.jx.databinding.ActivityFeedbackBinding;
import com.xsd.jx.utils.AnimUtils;
import com.xsd.jx.utils.OnSuccessAndFailListener;
import com.xsd.utils.DpPxUtils;
import com.xsd.utils.EditTextUtils;
import com.xsd.utils.L;
import com.xsd.utils.ScreenUtils;
import com.xsd.utils.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


/**
 * 意见反馈
 */
public class FeedbackActivity extends BaseBindBarActivity<ActivityFeedbackBinding> {
    private static final String TAG = "FeedbackActivity";
    private AnimationDrawable drawableVoice;//声音动画文件
    private String contentUrl="007.mp3";//TODO 上传音频到服务器或阿里云得到地址信息



    @Override
    protected int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMedia();
        onEvent();
    }
    private void initView() {
        tvTitle.setText("意见反馈");
        drawableVoice = (AnimationDrawable) db.ivVoice.getBackground();
    }

    private void onEvent() {
        db.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               submit();
            }
        });
        db.tvRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissionOfRecord();
            }
        });
        db.ivNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecord(false);
            }
        });
        db.ivYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopRecord(true);
            }
        });
        db.layoutVoiceOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                L.e("开始播放声音...");
                if (!isPlay){
                    isPlay=true;
                    mediaPlayer.start();
                    drawableVoice.start();
                }else {
                    isPlay=false;
                    mediaPlayer.pause();
                    drawableVoice.stop();
                    drawableVoice.selectDrawable(0);//回到第一帧
                }
            }
        });
    }

    private void submit() {
        if (EditTextUtils.isEmpty(db.etContent))return;
        String content = db.etContent.getText().toString();
        dataProvider.user.feedback(content,contentUrl)
                .subscribe(new OnSuccessAndFailListener<BaseResponse<MessageBean>>(dialog) {
                    @Override
                    protected void onSuccess(BaseResponse<MessageBean> baseResponse) {
                        ToastUtil.showLong(baseResponse.getData().getMessage());
                        finish();
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            isPlay=false;
            mediaPlayer.pause();
            mediaPlayer = null;
        }
    }

    private void getPermissionOfRecord() {
        XXPermissions.with(FeedbackActivity.this)
                .permission(Permission.RECORD_AUDIO)
                .request(new OnPermission() {
                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            startRecord();
                        } else {
                            ToastUtil.showLong("获取权限成功，部分权限未正常授予");
                        }
                    }
                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.showLong("被永久拒绝授权，请手动授予录音权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(FeedbackActivity.this, denied);
                        } else {
                            ToastUtil.showLong("获取录音权限失败");
                        }
                    }
                });
    }

    //播放声音
    private MediaPlayer mediaPlayer;
    private boolean isPlay;//是否正在播放
    private void initMedia() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(mp -> {
            L.e("加载完毕，开始播放");
        });
        mediaPlayer.setOnCompletionListener(mp -> {
            L.e("播放完毕");
            isPlay=false;
            drawableVoice.stop();
            drawableVoice.selectDrawable(0);//回到第一帧

        });
    }
    /**
     * 获取录音的声音分贝值
     * @return
     */
    public int getDB(){
        int ratio = mMediaRecorder.getMaxAmplitude();
        int db = 0;// 分贝
        if (ratio > 1)
            db =(int) (20 * Math.log10(ratio));
        L.e(TAG,"分贝值："+((int) db));
        if (db < 44) {
            return 0;
        } else if (db < 52) {
            return 1;
        } else if (db < 60) {
            return 2;
        } else if (db < 68) {
            return 3;
        } else if (db < 76) {
            return 4;
        } else if (db < 84) {
            return 5;
        } else if (db < 92) {
            return 6;
        } else if (db < 100) {
            return 7;
        }
        return 0;
    }
    private void setVoiceSection(int size){
        for (int i = 0; i <8; i++) {
            View childAt = db.layoutVoices.getChildAt(i);
            boolean isWhite = size> (7-i);
            childAt.setBackgroundResource(isWhite?R.mipmap.ic_white_voice_round:R.mipmap.ic_trans_voice_round);
        }
    }
    /**
     * 显示录音中...
     */
    private MediaRecorder mMediaRecorder;//录音器
    private String filePath;//录音文件保存路径
    public void startRecord() {
        L.e(TAG,"startRecord");
        db.layoutRecord.setVisibility(View.VISIBLE);
        db.layoutRecord.setFocusable(true);
        AnimUtils.tabSelect(db.layoutMic);

        db.tvRecordDesc.setText("正在录音...");
        mHandler.sendEmptyMessage(0);

        if (mMediaRecorder == null) mMediaRecorder = new MediaRecorder();
        try {
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            String fileName = DateFormat.format("yyyyMMdd_HHmmss", Calendar.getInstance(Locale.CHINA)) + ".m4a";
            filePath = Objects.requireNonNull(this).getExternalCacheDir()+ File.separator + fileName;
            mMediaRecorder.setOutputFile(filePath);
            mMediaRecorder.prepare();
            mMediaRecorder.start();
            L.e(TAG,"录音文件保存路径=="+filePath);
        } catch (IllegalStateException e) {
            L.i("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        } catch (IOException e) {
            L.i("call startAmr(File mRecAudioFile) failed!" + e.getMessage());
        }
    }
    public void stopRecord(boolean isSave) {
        L.e(TAG,"stopRecord");


        //1.停止后，先释放录音资源
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        } catch (Exception e) {
            L.e(e.toString());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            File file = new File(filePath);
            if (file.exists()) file.delete();
            filePath = "";
        }
        //2.停止刷新录音时间，并隐藏录音的控件
        mHandler.removeMessages(0);
        db.layoutRecord.setVisibility(View.GONE);
        //3.如果不保存就删除声音文件
        if (!isSave){
            File file = new File(filePath);
            if (file.exists()) file.delete();
            filePath = "";

            //4.如果要保存就隐藏录音按钮，并显示播放声音的控件
        }else {
            db.tvRecord.setVisibility(View.GONE);
            db.layoutVoiceOver.setVisibility(View.VISIBLE);
            getVoiceLength();
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(filePath);
                mediaPlayer.prepareAsync();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0://开始录音计时
                    int time = msg.arg1;
                    if (time<=59){
                        setVoiceSection(getDB());
                        db.tvRecordDesc.setText("正在录音中 "+time+"秒");
                        time++;
                        Message message = mHandler.obtainMessage();
                        message.what=0;
                        message.arg1 = time;
                        mHandler.sendMessageDelayed(message,1000);
                    }else {
                        stopRecord(true);
                    }

                    break;
            }
            return false;
        }
    });
    private void getVoiceLength(){
        try {
            MediaMetadataRetriever metaRetriever = new MediaMetadataRetriever();
            metaRetriever.setDataSource(filePath);
            String duration =
                    metaRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            L.e("time", duration);
            long dur = Long.parseLong(duration);
            int seconds = (int) (dur  / 1000);
            metaRetriever.release();
            db.tvVoiceTime.setText(seconds+"秒");
            //根据时间计算长度
            int stepLength = (ScreenUtils.getRealWidth() / 2)/60;
            int width = DpPxUtils.dp2px(60)+stepLength*seconds;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, DpPxUtils.dp2px(36));
            db.layoutVoiceLength.setLayoutParams(layoutParams);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            ToastUtil.showLong("录音失败！");
        }

    }


}