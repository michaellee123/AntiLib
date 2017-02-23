package dog.abcd.lib.network;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>网络请求类</b><br>
 * 调用builder方法获取一个构造器，再调用create创建AntiNetwork类，再调用start或stop
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/22 下午 17:34
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/1/22 下午 17:34
 */
public class AntiNetwork {

    public enum Method {
        POST, GET
    }

    private Method method;

    private String TAG;

    private String url;

    private Map<String, String> params = new HashMap<>();

    private Map<String, String> headers = new HashMap<>();

    private String body;

    private String bodyContentType;

    private AntiNetworkListener listener;

    public Method getMethod() {
        return method;
    }

    public String getTAG() {
        return TAG;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getBody() {
        return body;
    }

    public String getBodyContentType() {
        return bodyContentType;
    }

    public AntiNetworkListener getListener() {
        return listener;
    }

    private AntiNetwork(Builder builder) {
        if (builder.TAG == null || builder.url == null) {
            throw new IllegalArgumentException("arguments that named TAG or url can not be null");
        }
        this.method = builder.method;
        this.TAG = builder.TAG;
        this.url = builder.url;
        this.body = builder.body;
        this.params = builder.params;
        this.headers = builder.headers;
        this.listener = builder.listener;
        this.bodyContentType = builder.bodyContentType;
    }

    public void start() {
        AntiNetworkManager.getInstance().start(this);
    }

    public void stop() {
        AntiNetworkManager.getInstance().stop(this.getTAG());
    }

    /**
     * 获取构造器
     *
     * @return
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
        }

        public static final String CONTENT_TYPE_JSON = "application/json; charset=utf-8";
        public static final String CONTENT_TYPE_DEFAULT = "application/x-www-form-urlencoded; charset=utf-8";

        private Method method = Method.POST;

        private String TAG;

        private String url;

        private Map<String, String> params = new HashMap<>();

        private Map<String, String> headers = new HashMap<>();

        private String body;

        private String bodyContentType;

        private AntiNetworkListener listener;

        /**
         * 设置请求方法
         *
         * @param method
         * @return
         */
        public Builder setMethod(Method method) {
            this.method = method;
            return this;
        }

        /**
         * 设置标记
         *
         * @param TAG
         * @return
         */
        public Builder setTAG(String TAG) {
            this.TAG = TAG;
            return this;
        }

        /**
         * 设置链接地址
         *
         * @param url
         * @return
         */
        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        /**
         * 新增参数
         *
         * @param key
         * @param value
         * @return
         */
        public Builder putParam(String key, String value) {
            params.put(key, value);
            return this;
        }

        /**
         * 设置参数Map，会覆盖之前put过的参数，如果需要再put参数的话，把put方法写在set后面
         *
         * @param params
         * @return
         */
        public Builder setParams(Map<String, String> params) {
            this.params = params;
            return this;
        }

        /**
         * 新增头
         *
         * @param key
         * @param value
         * @return
         */
        public Builder putHeader(String key, String value) {
            headers.put(key, value);
            return this;
        }

        /**
         * 设置头Map，会覆盖之前put过的头，如果需要再put头的话，把put方法写在set后面
         *
         * @param headers
         * @return
         */
        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * 直接设置body字符串，如果不设置或者设置为null则调用参数拼接后处理作为body，如果不为空，则使用此字符串作为http包体
         *
         * @param body
         * @return
         */
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        /**
         * 设置包体内容类型，不设置则调用默认
         *
         * @param contentType
         * @return
         */
        public Builder setBodyContentType(String contentType) {
            this.bodyContentType = contentType;
            return this;
        }

        /**
         * 设置请求监听
         *
         * @param listener
         * @return
         */
        public Builder setListener(AntiNetworkListener listener) {
            this.listener = listener;
            return this;
        }

        /**
         * 获取网络请求对象
         *
         * @return
         */
        public AntiNetwork create() {
            return new AntiNetwork(this);
        }


    }

}
