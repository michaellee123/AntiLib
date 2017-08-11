package dog.abcd.lib.alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import dog.abcd.lib.R;

import dog.abcd.lib.utils.AntiDataUtils;

/**
 * <b> 自定义弹窗</b><br>
 * 带两个按钮的提示窗口
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016年2月15日 上午9:22:03
 */
public class AstiAlertDialog extends AstiAlertBase {
    private Button btnLeft;
    private Button btnRight;
    private TextView tvTitle;
    private TextView tvMessage;
    private boolean cancelable;
    View alertView;

    public static Builder builder(Context context) {
        return new Builder( context );
    }

    private AstiAlertDialog(Context context, String TAG, String title, String message, String btnLeftStr, String btnRightStr, OnClickListener leftOnClick, OnClickListener rightOnClick, boolean cancelable) {
        super( context, TAG );
        alertView = initView( title, message, btnLeftStr, btnRightStr, leftOnClick, rightOnClick );
        this.cancelable = cancelable;
    }

    protected View initView(String title, String message, String btnLeftStr, String btnRightStr, final OnClickListener leftOnClick, final OnClickListener rightOnClick) {
        View view = LayoutInflater.from( getContext() ).inflate( R.layout.layout_alert_dialog, null );
        tvMessage = ((TextView) view
                .findViewById( R.id.tv_message ));
        if (!AntiDataUtils.isEmpty( message )) {
            tvMessage.setText( message );
        }
        tvTitle = ((TextView) view.findViewById( R.id.tv_title ));
        if (!AntiDataUtils.isEmpty( message )) {
            tvTitle.setText( title );
        }
        // 为确认按钮添加事件,执行退出应用操作
        btnRight = (Button) view.findViewById( R.id.btn_right );
        if (!AntiDataUtils.isEmpty( btnRightStr )) {
            btnRight.setText( btnRightStr );
        }
        btnRight.setOnClickListener( new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rightOnClick != null) {
                    rightOnClick.onClick( v );
                }
                dismiss();
            }
        } );
        btnLeft = (Button) view.findViewById( R.id.btn_left );
        if (!AntiDataUtils.isEmpty( btnLeftStr )) {
            btnLeft.setText( btnLeftStr );
        }
        btnLeft.setOnClickListener( new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (leftOnClick == null) {
                    leftOnClick.onClick( v );
                }
                dismiss();
            }

        } );
        return view;
    }

    @Override
    protected View createView() {
        return alertView;
    }

    @Override
    protected boolean cancelable() {
        return cancelable;
    }

    public TextView getTvMessage() {
        return tvMessage;
    }

    public Button getBtnLeft() {
        return btnLeft;
    }

    public Button getBtnRight() {
        return btnRight;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public static class Builder {
        Context context;

        private Builder(Context context) {
            this.context = context;
        }

        private String TAG;
        private String title;
        private String message;
        private String leftBtnStr;
        private String rightBtnStr;
        private OnClickListener leftOnClick, rightOnClick;
        private boolean cancelable = false;

        public Builder setTAG(String TAG) {
            this.TAG = TAG;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setLeftBtnStr(String leftBtnStr) {
            this.leftBtnStr = leftBtnStr;
            return this;
        }

        public Builder setRightBtnStr(String rightBtnStr) {
            this.rightBtnStr = rightBtnStr;
            return this;
        }

        public Builder setLeftOnClick(OnClickListener leftOnClick) {
            this.leftOnClick = leftOnClick;
            return this;
        }

        public Builder setRightOnClick(OnClickListener rightOnClick) {
            this.rightOnClick = rightOnClick;
            return this;
        }

        /**
         * 默认是FALSE
         *
         * @param cancelable
         * @return
         */
        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public AstiAlertDialog create() {
            return new AstiAlertDialog( context, TAG, title, message, leftBtnStr, rightBtnStr, leftOnClick, rightOnClick, cancelable );
        }

    }

}
