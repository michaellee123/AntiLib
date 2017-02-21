package dog.abcd.lib.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import okhttp3.OkHttpClient;

/**
 * <b>网络请求管理器</b><br>
 * 维持一个请求队列，需要先在Application中初始化默认参数构造以及默认监听处理
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/22 下午 13:22
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/1/22 下午 13:22
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

    public interface IDefaultListener {
        boolean success(AntiNetwork network, NetworkResponse result);

        boolean error(AntiNetwork network, AntiNetworkException error);
    }

    private IDefaultListener defaultListener;

    private IDefaultParams defaultParams;

    private RequestQueue queue;

    /**
     * @param context
     * @param certID          raw文件中的https证书id，如果不需要https则传0
     * @param defaultParams   默认参数构造
     * @param defaultListener 默认监听处理
     */
    private AntiNetworkManager(Context context, int certID, IDefaultParams defaultParams, IDefaultListener defaultListener) {
        this.defaultParams = defaultParams;
        this.defaultListener = defaultListener;
        queue = Volley.newRequestQueue(context, new AntiNetworkStack(new OkHttpClient(), context, certID));
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
                    instance = new AntiNetworkManager(context, certID, defaultParams, defaultListener);
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
     * 开始网络请求
     *
     * @param antiNetwork 网络请求对象
     */
    public void start(final AntiNetwork antiNetwork) {
        queue.cancelAll(antiNetwork.getTAG());
        AntiResponseRequest request = new AntiResponseRequest(antiNetwork.getMethod().equals(AntiNetwork.Method.GET) ? Request.Method.GET : Request.Method.POST, antiNetwork.getUrl(), new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                if (defaultListener == null || !defaultListener.success(antiNetwork, response)) {
                    antiNetwork.getListener().success(antiNetwork, response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AntiNetworkException exception = new AntiNetworkException(error.networkResponse == null ? -1 : error.networkResponse.statusCode, error.getMessage() == null ? error.toString() : error.getMessage());
                if (defaultListener == null || !defaultListener.error(antiNetwork, exception)) {
                    antiNetwork.getListener().error(antiNetwork, exception);
                }
            }
        }) {
            //重写方法

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (defaultParams != null && defaultParams.getDefaultParams() != null) {
                    antiNetwork.getParams().putAll(defaultParams.getDefaultParams());
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
                    antiNetwork.getHeaders().putAll(defaultParams.getDefaultHeaders());
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
                        return antiNetwork.getBody().getBytes(getParamsEncoding());
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
        request.setTag(antiNetwork.getTAG());
        queue.add(request);
    }

    /**
     * 开始自定义的网络请求，不会调用默认参数与默认回调
     *
     * @param request
     */
    public void start(Request request) {
        queue.add(request);
    }

    /**
     * 中断网络请求
     *
     * @param TAG
     */
    public void stop(String TAG) {
        queue.cancelAll(TAG);
    }

}
