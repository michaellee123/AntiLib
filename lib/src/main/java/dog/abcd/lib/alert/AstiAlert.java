package dog.abcd.lib.alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import dog.abcd.lib.R;

import dog.abcd.lib.utils.AntiDataUtils;
import dog.abcd.lib.utils.AntiScreenUtils;

/**
 * <b> 带按钮提示框</b><br>
 * 带一个按钮需要用户确认的重要信息的提示框
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016年2月15日 上午9:21:02
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017-01-17 下午4:44:51
 */
public class AstiAlert extends AstiAlertBase {

    private View alertView;
    private Button btnCancel;
    private TextView tvMessage, tvTitle, tvMessageHide;
    private boolean cancelable;

    public static Builder builder(Context context) {
        return new Builder(context);
    }

    private AstiAlert(Context context, String title, String message, String btnText, OnClickListener onClickListener, boolean cancelable) {
        setContext(context);
        alertView = initView(title, message, btnText, onClickListener);
        this.cancelable = cancelable;
    }

    protected View initView(String title, String message, String btnText, final OnClickListener onClickListener) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_alert, null);
        tvMessage = (TextView) view.findViewById(R.id.dialog_message);
        tvTitle = (TextView) view.findViewById(R.id.dialog_title);
        if (!AntiDataUtils.isEmpty(title)) {
            tvTitle.setText(title);
        }
        tvMessageHide = (TextView) view.findViewById(R.id.dialog_message_hide);
        if (!AntiDataUtils.isEmpty(message)) {
            tvMessage.setText(message);
            tvMessageHide.setText(message);
        }
        tvMessageHide.setMaxHeight((int) (AntiScreenUtils.getScreenHeight(getContext()) / 2.5));
        // 为确认按钮添加事件,执行退出应用操作
        btnCancel = (Button) view.findViewById(R.id.dialog_ok);
        if (!AntiDataUtils.isEmpty(btnText)) {
            btnCancel.setText(btnText);
        }
        btnCancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClick(v);
                }
                dismiss();
            }

        });
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


    /**
     * 获取按钮
     *
     * @return
     */
    public Button getButton() {
        return btnCancel;
    }

    /**
     * 获取标题
     *
     * @return
     */
    public TextView getTvTitle() {
        return tvTitle;
    }

    /**
     * 获取提示文字控件
     *
     * @return
     */
    public TextView getTvMessage() {
        return tvMessage;
    }

    public static class Builder {

        public Builder(Context context) {
            this.context = context;
        }

        private Context context;
        private String title, message, btnStr;
        private boolean cancelable = false;
        private OnClickListener onClickListener;

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
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

        public Builder setBtnStr(String btnStr) {
            this.btnStr = btnStr;
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

        public AstiAlert create() {
            return new AstiAlert(context, title, message, btnStr, onClickListener, cancelable);
        }

    }

}
