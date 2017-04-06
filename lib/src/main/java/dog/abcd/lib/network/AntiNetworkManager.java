package dog.abcd.lib.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import java.io.UnsupportedEncodingException;
import java.util.Map;
import okhttp3.OkHttpClient;

/**
 * <b>网络请求管理器</b><br>
 * 维持一个请求队列，需要先在Application中初始化默认参数构造以及默认监听处理
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/22 下午 13:22
 */
public class AntiNetworkManager {

    private static AntiNetworkManager instance;

    /**
     * 默认参数与头的构造
     */
    public interface IDefaultParams {
        Map<String, String> getDefaultParams();

        Map<String, String> getDefaultHeaders();
    }

    /**
     * 默认请求处理回调
     */
    public interface IDefaultListener {
        boolean success(AntiNetwork network, NetworkResponse result);

        boolean error(AntiNetwork network, AntiNetworkException error);
    }

    private IDefaultListener defaultListener;

    private IDefaultParams defaultParams;

    private RequestQueue queue;

    private int timeOut = 5000;

    /**
     * @param context
     * @param certID          raw文件中的https证书id，如果不需要https则传0
     * @param defaultParams   默认参数构造
     * @param defaultListener 默认监听处理
     */
    private AntiNetworkManager(Context context, int certID, IDefaultParams defaultParams, IDefaultListener defaultListener, int timeOut) {
        this.defaultParams = defaultParams;
        this.defaultListener = defaultListener;
        this.timeOut = timeOut;
        OkHttpClient okHttpClient = new OkHttpClient();
        this.queue = Volley.newRequestQueue( context, new AntiNetworkStack( okHttpClient, context, certID ) );
    }

    /**
     * 初始化
     *
     * @param context
     * @param defaultParams   默认参数构造
     * @param defaultListener 默认监听处理
     * @return 建议由Application持有返回值，避免内存回收
     */
    public static AntiNetworkManager init(Context context, int certID, IDefaultParams defaultParams, IDefaultListener defaultListener) {
        if (instance == null) {
            synchronized (AntiNetworkManager.class) {
                if (instance == null) {
                    instance = new AntiNetworkManager( context, certID, defaultParams, defaultListener, 5000 );
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     * @param defaultParams   默认参数构造
     * @param defaultListener 默认监听处理
     * @param timeOut         请求超时
     * @return 建议由Application持有返回值，避免内存回收
     */
    public static AntiNetworkManager init(Context context, int certID, IDefaultParams defaultParams, IDefaultListener defaultListener, int timeOut) {
        if (instance == null) {
            synchronized (AntiNetworkManager.class) {
                if (instance == null) {
                    instance = new AntiNetworkManager( context, certID, defaultParams, defaultListener, timeOut );
                }
            }
        }
        return instance;
    }

    public static AntiNetworkManager getInstance() {
        return instance;
    }

    public IDefaultListener getDefaultListener() {
        return defaultListener;
    }

    public void setDefaultListener(IDefaultListener defaultListener) {
        this.defaultListener = defaultListener;
    }

    public IDefaultParams getDefaultParams() {
        return defaultParams;
    }

    public void setDefaultParams(IDefaultParams defaultParams) {
        this.defaultParams = defaultParams;
    }

    /**
     * 生成网络请求对象
     *
     * @param antiNetwork
     * @return
     */
    public Request createRequest(final AntiNetwork antiNetwork) {
        AntiResponseRequest request = new AntiResponseRequest( antiNetwork.getMethod().equals( AntiNetwork.Method.GET ) ? Request.Method.GET : Request.Method.POST, antiNetwork.getUrl(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(final NetworkResponse response) {
                Handler handler = new Handler( Looper.getMainLooper() );
                handler.post( new Runnable() {
                    @Override
                    public void run() {
                        if (defaultListener == null || !defaultListener.success( antiNetwork, response )) {
                            antiNetwork.getListener().success( antiNetwork, response );
                        }
                    }
                } );
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Handler handler = new Handler( Looper.getMainLooper() );
                handler.post( new Runnable() {
                    @Override
                    public void run() {
                        AntiNetworkException exception = new AntiNetworkException( error.networkResponse == null ? -1 : error.networkResponse.statusCode, error.getMessage() == null ? error.toString() : error.getMessage() );
                        if (defaultListener == null || !defaultListener.error( antiNetwork, exception )) {
                            antiNetwork.getListener().error( antiNetwork, exception );
                        }
                    }
                } );
            }
        } ) {
            //重写方法

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (defaultParams != null && defaultParams.getDefaultParams() != null) {
                    antiNetwork.getParams().putAll( defaultParams.getDefaultParams() );
                    return antiNetwork.getParams();
                } else if (antiNetwork.getParams() != null) {
                    return antiNetwork.getParams();
                } else {
                    return super.getParams();
                }
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (defaultParams != null && defaultParams.getDefaultHeaders() != null) {
                    antiNetwork.getHeaders().putAll( defaultParams.getDefaultHeaders() );
                    return antiNetwork.getHeaders();
                } else if (antiNetwork.getHeaders() != null) {
                    return antiNetwork.getHeaders();
                } else {
                    return super.getHeaders();
                }
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                if (antiNetwork.getBody() != null) {
                    try {
                        return antiNetwork.getBody().getBytes( getParamsEncoding() );
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return super.getBody();
                    }
                } else {
                    return super.getBody();
                }
            }

            @Override
            public String getBodyContentType() {
                if (antiNetwork.getBodyContentType() != null) {
                    return antiNetwork.getBodyContentType();
                } else {
                    return super.getBodyContentType();
                }
            }
        };
        request.setRetryPolicy( new DefaultRetryPolicy( timeOut, 0, 0 ) );
        request.setTag( antiNetwork.getTAG() );
        return request;
    }

    /**
     * 开始网络请求
     *
     * @param antiNetwork 网络请求对象
     */
    public void start(final AntiNetwork antiNetwork) {
        start( createRequest( antiNetwork ) );
    }

    /**
     * 开始自定义的网络请求，如果不是AntiNetworkManager生成的Request对象则不会调用默认参数与默认回调
     *
     * @param request
     */
    public void start(Request request) {
        queue.cancelAll( request.getTag() );
        queue.add( request );
    }

    /**
     * 中断网络请求
     *
     * @param TAG
     */
    public void stop(String TAG) {
        queue.cancelAll( TAG );
    }

    /**
     * 获取请求队列
     * @return
     */
    public RequestQueue getQueue() {
        return queue;
    }

}
