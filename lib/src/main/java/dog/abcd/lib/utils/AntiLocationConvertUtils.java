package dog.abcd.lib.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>坐标类型转换</b><br>
 * GCJ02和BD09的互相转换
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午11:30:33
 */
public class AntiLocationConvertUtils {

	private AntiLocationConvertUtils() {

	}

	private static double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
	private static Map<String, Double> map = new HashMap<String, Double>();

	/**
	 * GCJ02坐标转BD09
	 * 
	 * @param lat
	 * @param lng
	 * @return 一个Map，由lat和lng分别取值
	 */
	public static Map<String, Double> Convert_GCJ02_To_BD09(double lat,
			double lng) {
		double x = lng, y = lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
		lng = z * Math.cos(theta) + 0.0065;
		lat = z * Math.sin(theta) + 0.006;
		map.put("lng", lng);
		map.put("lat", lat);
		return map;
	}

	/**
	 * BD09坐标转GCJ02
	 * 
	 * @param lat
	 * @param lng
	 * @return 一个Map，由lat和lng分别取值
	 */
	public static Map<String, Double> Convert_BD09_To_GCJ02(double lat,
			double lng) {
		double x = lng - 0.0065, y = lat - 0.006;
		double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
		double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
		lng = z * Math.cos(theta);
		lat = z * Math.sin(theta);
		map.put("lng", lng);
		map.put("lat", lat);
		return map;
	}
}
