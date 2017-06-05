package dog.abcd.lib.network.listener;

import com.android.volley.NetworkResponse;

import org.json.JSONObject;

import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkConvert;
import dog.abcd.lib.network.AntiNetworkListener;

/**
 * <b>网络请求处理回调（结果为JSON [org.json.JSONObject]）</b><br>
 * 从AntiNetworkListener重写
 * @see AntiNetworkListener
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/6/5 下午 16:00
 */
public abstract class AntiNetworkJsonListener implements AntiNetworkListener {

    @Override
    public final void success(AntiNetwork network, NetworkResponse result) {
        success( network, AntiNetworkConvert.convertResponceToJson( result ) );
    }

    protected abstract void success(AntiNetwork network, JSONObject result);
}
