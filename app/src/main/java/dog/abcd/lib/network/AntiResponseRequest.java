package dog.abcd.lib.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * <b>Title</b><br>
 * Description
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/21 下午 12:57
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/21 下午 12:57
 */
public class AntiResponseRequest extends Request<NetworkResponse> {
    private Response.Listener<NetworkResponse> mListener;

    public AntiResponseRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
    }

    @Override
    protected void onFinish() {
        super.onFinish();
        mListener = null;
    }

    @Override
    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(NetworkResponse response) {
        if (mListener != null) {
            mListener.onResponse(response);
        }
    }
}
