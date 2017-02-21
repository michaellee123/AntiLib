package dog.abcd.lib.demo;

import android.app.Application;

import com.android.volley.NetworkResponse;

import java.util.Map;

import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkException;
import dog.abcd.lib.network.AntiNetworkManager;
import dog.abcd.lib.network.AntiNetworkManager.IDefaultListener;
import dog.abcd.lib.network.AntiNetworkManager.IDefaultParams;
import dog.abcd.lib.utils.AntiImageLoader;

/**
 * <b>Title</b><br>
 * Description
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/20 下午 17:57
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/20 下午 17:57
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AntiNetworkManager.init(this, 0, new IDefaultParams() {
            @Override
            public Map<String, String> getDefaultParams() {
                return null;
            }

            @Override
            public Map<String, String> getDefaultHeaders() {
                return null;
            }
        }, new IDefaultListener() {
            @Override
            public boolean success(AntiNetwork network, NetworkResponse result) {
                return false;
            }

            @Override
            public boolean error(AntiNetwork network, AntiNetworkException error) {
                return false;
            }
        });
        AntiImageLoader.init(this);
    }
}
