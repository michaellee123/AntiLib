package dog.abcd.lib.alert;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;

import dog.abcd.lib.R;

/**
 * <b>自定义弹窗的父类</b><br>
 * 继承后实现createView方法再调用show方法即可
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/17 下午 16:38
 */
public abstract class AstiAlertBase {

    private Context context;
    private String TAG;
    private AlertDialog dlg;
    private Window window;

    public AstiAlertBase(Context context, String TAG) {
        if (TAG == null || context == null) {
            throw new IllegalArgumentException("arguments that named TAG or context can not be null!");
        }
        this.TAG = TAG;
        this.context = context;
    }

    protected abstract View createView();

    protected abstract boolean cancelable();

    public void show() {
        dismiss();
        try {
            dlg = new AlertDialog.Builder(context).create();
            dlg.show();
            window = dlg.getWindow();
            dlg.setCancelable(cancelable());
            window.setContentView(createView());
            window.setBackgroundDrawableResource(R.color.alpha);
            AstiAlertManager.getInstance().add(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消显示
     */
    public void dismiss() {
        try {
            dlg.dismiss();
        } catch (Exception e) {

        }
    }

    /**
     * 取消显示
     */
    public void cancel() {
        try {
            dlg.cancel();
        } catch (Exception e) {

        }
    }

    public String getTAG() {
        return TAG;
    }

    public Context getContext() {
        return context;
    }

    /**
     * 获取窗口
     *
     * @return
     */
    public Window getWindow() {
        return window;
    }

    /**
     * @return 获取弹出窗口
     */
    public AlertDialog getDialog() {
        return dlg;
    }

    /**
     * 设置取消的回调
     *
     * @param onCancelListener
     */
    public void setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        dlg.setOnCancelListener(onCancelListener);
    }

    /**
     * 设置隐藏时的回调
     *
     * @param onDismissListener
     */
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        dlg.setOnDismissListener(onDismissListener);
    }

}
