package dog.abcd.lib.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * <b>通用网络请求</b><br>
 * 返回的值需要调用AntiNetworkConvert进行转换
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/21 下午 12:57
 */
public class AntiResponseRequest extends Request<NetworkResponse> {
	private Response.Listener<NetworkResponse> mListener;

	public AntiResponseRequest(int method, String url, Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
		super( method, url, errorListener );
		this.mListener = listener;
	}

	@Override
	public void cancel() {
		super.cancel();
		this.mListener = null;
	}

	@Override
	protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
		return Response.success( response, HttpHeaderParser.parseCacheHeaders( response ) );
	}

	@Override
	protected void deliverResponse(NetworkResponse response) {
		if (mListener != null) {
			mListener.onResponse( response );
		}
	}
}
