package dog.abcd.demo;

import android.app.Application;

import dog.abcd.lib.network.AntiNetworkManager;
import dog.abcd.lib.utils.AntiImageLoader;

/**
 * <b>Title</b><br>
 * Description
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/24 下午 14:42
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化网络请求
        AntiNetworkManager.init(this, 0, null, null);
        //初始化图片加载
        AntiImageLoader.init(this);
//        AntiImageLoader.init(this,加载中的图片,加载图片为空时的图片,加载失败的图片);
//        AntiImageLoader.init(this,自定义图片加载配置);
    }
}
