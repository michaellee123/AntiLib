package dog.abcd.lib.network.listener;

import com.android.volley.NetworkResponse;

import org.json.JSONArray;

import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkConvert;
import dog.abcd.lib.network.AntiNetworkListener;

/**
 * <b>网络请求处理回调（JSONArray [org.json.JSONArray]）</b><br>
 * 从AntiNetworkListener重写
 * @see AntiNetworkListener
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/6/5 下午 16:02
 */
public abstract class AntiNetworkJsonArrayListener implements AntiNetworkListener {

    @Override
    public final void success(AntiNetwork network, NetworkResponse result) {
        success( network, AntiNetworkConvert.convertResponseToJsonArray( result ) );
    }

    protected abstract void success(AntiNetwork network, JSONArray result);
}
