package dog.abcd.lib.network.listener;

import com.android.volley.NetworkResponse;

import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkException;

/**
 * <b>网络请求处理回调</b><br>
 * 直接实现本接口可以获取到最原始的网络请求数据回调，再通过AntiNetworkConvert类进行转换，或者自行转换，现有四个不同类型的二次封装抽象
 * <p>
 * 如果在当做AntiNetworkManager里面的默认监听时方法中返回了true，则不会再调用之后的方法
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/20 下午 14:31
 * @see dog.abcd.lib.network.AntiNetworkConvert
 * @see AntiNetworkBitmapListener
 * @see AntiNetworkJsonArrayListener
 * @see AntiNetworkJsonListener
 * @see AntiNetworkStringListener
 */
public interface AntiNetworkListener {

    void success(AntiNetwork network, NetworkResponse result);

    void error(AntiNetwork network, AntiNetworkException error);
}
