package com.xsd.utils.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


public class DialogController {
    public BaseDialog mDialog;
    public Window mWindow;

    public DialogController(BaseDialog dialog, Window window) {
        this.mDialog = dialog;
        this.mWindow = window;
    }


    public BaseDialog getDialog() {
        return mDialog;
    }

    public Window getWindow() {
        return mWindow;
    }

    public View getView(int id) {
        return mDialog.findViewById(id);
    }

    /**
     * 设置背景灰色程度
     *
     * @param level 0.0f-1.0f
     */
    void setBackGroundLevel(float level) {
//        mWindow = ((Activity) context).getWindow();
        WindowManager.LayoutParams params = mWindow.getAttributes();
        params.alpha = level;
        mWindow.setAttributes(params);
    }

    public static class DialogParams {

        public int mWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
        public Context mContext;
        public int themeResId;
        public boolean mCancelable = true;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public int mLayoutId = 0;
        public int mGravity = Gravity.CENTER;
        public int mAnimation = 0;
        public float mBgLevel = 1.0f;
        public float widthWeight=0.8f;

        public DialogParams(Context mContext, int themeResId) {
            this.mContext = mContext;
            this.themeResId = themeResId;
        }


        public void apply(DialogController dialogController) {
            if (mLayoutId != 0) {
                View view = dialogController.getDialog().getLayoutInflater().inflate(mLayoutId, null);
                dialogController.getDialog().setContentView(view);
            }

            Window window = dialogController.getWindow();

            //设置显示位置
            window.setGravity(mGravity);

            if (mWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
                WindowManager wm = window.getWindowManager();
                Display d = wm.getDefaultDisplay();
                mWidth = (int) (d.getWidth() * widthWeight);
            }

            //设置是否全屏 默认包裹
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = mWidth;
            attributes.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            attributes.alpha = mBgLevel;
            window.setAttributes(attributes);

            //设置底部弹出动画
            if (mAnimation != 0)
                window.setWindowAnimations(mAnimation);

        }

        public void setWidthWeight(float widthWeight) {
            this.widthWeight = widthWeight;
        }
    }

}
