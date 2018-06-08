package dog.abcd.lib.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * <b>Toast类的封装</b><br>
 * 避免了多次调用后一直显示Toast的问题
 *
 * @author Michael Lee<br>
 * <b> create at </b>2016-1-28 下午1:51:56
 */
public class AntiToast {

    private AntiToast() {

    }

    private static Toast toast;

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param message 显示文字
     */
    public static Toast show(Context context, CharSequence message) {
        if (null == toast) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            toast.setText(message);
        }
        toast.show();
        return toast;
    }

    /**
     * 显示Toast
     *
     * @param context  上下文
     * @param message  显示文字
     * @param showTime 显示停留的时间
     */
    public static Toast show(Context context, CharSequence message, int showTime) {
        if (null == toast) {
            toast = Toast.makeText(context, message, showTime);
        } else {
            toast.setText(message);
        }
        toast.show();
        return toast;
    }

    /**
     * 隐藏Toast
     */
    public static void hidden() {
        if (null != toast) {
            toast.cancel();
            toast = null;
        }
    }
}
