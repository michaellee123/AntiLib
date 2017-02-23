package dog.abcd.lib.network;

import com.android.volley.NetworkResponse;

/**
 * <b>网络请求处理回调</b><br>
 * 如果在当做AntiNetworkManager里面的默认监听时方法中返回了true，则不会再调用之后的方法
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/20 下午 14:31
 */
public interface AntiNetworkListener {

    void success(AntiNetwork network, NetworkResponse result);

    void error(AntiNetwork network, AntiNetworkException error);
}
