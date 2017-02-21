package dog.abcd.lib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 *
 * <b>系统帮助类</b><br>
 * 一些常用的系统方法调用
 *
 * @Company RZQC
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午10:56:01
 * @Mender Michael Lee<br>
 *         <b> change at </b>2017-01-17 上午11:56:01
 *
 */
public class AntiSystemUtils {

    private AntiSystemUtils(){

    }

    /**
     * 调用系统下载
     *
     * @param url
     * @param context
     */
    public static void download(String url, Context context) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            context.startActivity(i);
        } catch (Exception e) {
        }
    }

    /**
     * 跳转系统拨打电话界面
     *
     * @param context
     * @param number
     */
    public static void callPhone(Context context, String number) {
        try {
            Uri uri = Uri.parse("tel:" + number);
            Intent it = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(it);
        } catch (Exception e) {
        }
    }


}
