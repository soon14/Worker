
package com.xsd.utils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.xsd.utils.R;


public class BaseDialog extends Dialog {
    private DialogController dialogController;
    private Context mContext;

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        dialogController = new DialogController(this, getWindow());
    }

    /**
     * 获取View
     * @param id
     * @return
     */
    public View getView(int id){
        return dialogController.getView(id);
    }





    public static class Builder {
        private final DialogController.DialogParams P;





        public Builder(Context context) {
            this(context, R.style.dialog);
        }
        public Builder(Context context,int themeResId ) {
            P = new DialogController.DialogParams(context,themeResId);
        }

        public Builder setWidthWeight(float widthWeight) {
            P.setWidthWeight(widthWeight);
            return this;
        }

        /**
         * 设置内容布局
         * @param layoutId
         * @return
         */
        public Builder setContentView(int layoutId) {
            P.mLayoutId = layoutId;
            return this;
        }

        /**
         * 设置是否全屏
         * @return
         */
        public Builder setFullScreen() {
            P.mWidth = ViewGroup.LayoutParams.MATCH_PARENT;
            return this;
        }



        /**
         * 设置从底部弹出
         * @return
         */
        public Builder setFromBottom(boolean isAnimation) {
              if(isAnimation){
                  P.mAnimation = R.style.dialog_bottom;
              }
            P.mGravity = Gravity.BOTTOM;
            return this;
        }
        public Builder setCancelable(boolean cancelable) {
            P.mCancelable = cancelable;
            return this;
        }


        public Builder setOnCancelListener(OnCancelListener onCancelListener) {
            P.mOnCancelListener = onCancelListener;
            return this;
        }


        public Builder setOnDismissListener(OnDismissListener onDismissListener) {
            P.mOnDismissListener = onDismissListener;
            return this;
        }


        public Builder setOnKeyListener(OnKeyListener onKeyListener) {
            P.mOnKeyListener = onKeyListener;
            return this;
        }

        /**
         * 设置背景灰色程度
         *
         * @param level 0.0f-1.0f
         * @return Builder
         */
        public Builder setBackGroundLevel(float level) {
            P.mBgLevel = level;
            return this;
        }

        public BaseDialog create() {

            // Context has already been wrapped with the appropriate theme.
            final BaseDialog dialog = new BaseDialog(P.mContext, P.themeResId);
            P.apply(dialog.dialogController);
            dialog.setCancelable(P.mCancelable);
            if (P.mCancelable) {
                dialog.setCanceledOnTouchOutside(true);
            }
            dialog.setOnCancelListener(P.mOnCancelListener);
            dialog.setOnDismissListener(P.mOnDismissListener);
            if (P.mOnKeyListener != null) {
                dialog.setOnKeyListener(P.mOnKeyListener);
            }
            return dialog;
        }


        public BaseDialog show() {
            final BaseDialog dialog = create();
            dialog.show();
            return dialog;
        }

    }

    @Override
    public void dismiss() {
        super.dismiss();
        dialogController.setBackGroundLevel(1.0f);
    }
}
