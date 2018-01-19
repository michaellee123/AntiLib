package dog.abcd.lib.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.NetworkResponse;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * <b>网络请求结果转换</b><br>
 * 由返回的Response的data传进来转换成需要的类型
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/21 下午 15:04
 */
public class AntiNetworkConvert {
	protected static final String PROTOCOL_CHARSET = "utf-8";

	public static String convertResponceToString(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String( response.data, HttpHeaderParser.parseCharset( response.headers, PROTOCOL_CHARSET ) );
		} catch (UnsupportedEncodingException e) {
			parsed = new String( response.data );
		}
		return parsed;
	}


	public static JSONObject convertResponceToJson(NetworkResponse response) {
		try {
			String jsonString = new String( response.data,
					HttpHeaderParser.parseCharset( response.headers, PROTOCOL_CHARSET ) );
			return new JSONObject( jsonString );
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (JSONException je) {
			return null;
		}
	}

	public static JSONArray convertResponceToJsonArray(NetworkResponse response) {
		try {
			String jsonString = new String( response.data,
					HttpHeaderParser.parseCharset( response.headers, PROTOCOL_CHARSET ) );
			return new JSONArray( jsonString );
		} catch (UnsupportedEncodingException e) {
			return null;
		} catch (JSONException je) {
			return null;
		}
	}

	public static Bitmap convertResponceToBitmap(NetworkResponse response) {
		byte[] data = response.data;
		BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
		// If we have to resize this image, first get the natural bounds.
		decodeOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray( data, 0, data.length, decodeOptions );
		// Decode to the nearest power of two scaling factor.
		decodeOptions.inJustDecodeBounds = false;
		// decodeOptions.inPreferQualityOverSpeed = PREFER_QUALITY_OVER_SPEED;
		Bitmap tempBitmap =
				BitmapFactory.decodeByteArray( data, 0, data.length, decodeOptions );
		return tempBitmap;
	}

}
