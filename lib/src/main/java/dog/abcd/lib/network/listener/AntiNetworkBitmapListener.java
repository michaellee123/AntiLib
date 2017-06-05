package dog.abcd.lib.network.listener;

import android.graphics.Bitmap;

import com.android.volley.NetworkResponse;

import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkConvert;
import dog.abcd.lib.network.AntiNetworkListener;

/**
 * <b>网络请求处理回调（结果为Bitmap）</b><br>
 * 从AntiNetworkListener重写
 * @see AntiNetworkListener
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/6/5 下午 16:02
 */
public abstract class AntiNetworkBitmapListener implements AntiNetworkListener {

    @Override
    public final void success(AntiNetwork network, NetworkResponse result) {
        success( network, AntiNetworkConvert.convertResponceToBitmap( result ) );
    }

    protected abstract void success(AntiNetwork network, Bitmap result);
}
