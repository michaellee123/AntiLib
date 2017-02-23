package dog.abcd.lib.utils;

import android.util.Log;

/**
 * 
 * <b>日志打印</b> <br>
 * 封装了Log类的操作，方法调用与Log类一致，只是加入idDebug状态，
 * 在发布App的时候在Application中设置为false则不会打印日志出来了
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 下午1:45:53
 */
public class AntiLog {
	// 是否为调试状态
	private static boolean isDebug = true;

	/**
	 * 是否打印，默认开启
	 * 
	 * @param isDebug
	 */
	public static void setDebug(boolean isDebug) {
		AntiLog.isDebug = isDebug;
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (isDebug) {
			Log.d(tag, msg);
		}
	}

	public static void w(String tag, String msg) {
		if (isDebug) {
			Log.w(tag, msg);
		}
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, msg);
		}
	}

	public static void wtf(String tag, String msg) {
		if (isDebug) {
			Log.wtf(tag, msg);
		}
	}

}
