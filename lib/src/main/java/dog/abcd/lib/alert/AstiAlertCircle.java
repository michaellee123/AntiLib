package dog.abcd.lib.alert;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import dog.abcd.lib.R;

/**
 * <b> 耗时提示框</b><br>
 * 圆条加载提示框 <br>
 * -更新弹出框线程不同步造成的显示问题
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016年1月28日 上午11:18:18
 */
public class AstiAlertCircle extends AstiAlertBase {

    View alertView;

    private boolean cancelable;

    private TextView tvMessage;

    public TextView getTvMessage() {
        return tvMessage;
    }

    /**
     * @param context
     * @param message    提示信息
     * @param cancelable 是否能够取消（默认为能够取消）
     * @return
     */
    public static AstiAlertCircle create(Context context, String TAG, String message, boolean cancelable) {
        return new AstiAlertCircle(context, TAG, message, cancelable);
    }

    public static AstiAlertCircle create(Context context, String TAG, String message) {
        return new AstiAlertCircle(context, TAG, message, true);
    }

    private AstiAlertCircle(Context context, String TAG, String message, boolean cancelable) {
        super(context, TAG);
        alertView = initView(message);
        this.cancelable = cancelable;
    }

    protected View initView(String message) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_circle, null);
        tvMessage = (TextView) view.findViewById(R.id.dialog_message);
        tvMessage.setText(message);
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
}
