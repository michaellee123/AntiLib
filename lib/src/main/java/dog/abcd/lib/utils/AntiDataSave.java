package dog.abcd.lib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * <b> 数据缓存类</b><br>
 * 封装SharedPreferences的简单操作
 * 
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午11:02:16
 */
public class AntiDataSave {
	private Context context;

	private SharedPreferences sp;

	public AntiDataSave(Context context) {
		this.context = context;
	}

	/**
	 * 获取SharedPreferences对象
	 * 
	 * @param file
	 *            文件名
	 * @return SharedPreferences
	 */
	public SharedPreferences getSharedPreferences(String file) {
		sp = context.getSharedPreferences(file, Activity.MODE_PRIVATE);
		return sp;
	}

	/**
	 * 存储数据
	 * 
	 * @param file
	 *            文件名
	 * @param key
	 *            键
	 * @param value
	 *            值
	 */
	public void save(String file, String key, String value) {
		try{
		sp = context.getSharedPreferences(file, Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
		}catch(Exception e){
			
		}
	}

	/**
	 * 读取数据
	 * 
	 * @param file
	 *            文件名
	 * @param key
	 *            键
	 * @param defaultValue
	 *            默认值
	 * @return 字符串
	 */
	public String read(String file, String key, String defaultValue) {
		try{
		sp = context.getSharedPreferences(file, Activity.MODE_PRIVATE);
		return sp.getString(key, defaultValue);
		}catch(Exception exception){
			return null;
		}
	}

	/**
	 * 清除缓存
	 * 
	 * @param file
	 *            文件名
	 * @return 操作结果(always be true)
	 */
	public boolean clearData(String file) {
		try{
		sp = context.getSharedPreferences(file, Activity.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.clear();
		editor.commit();
		return true;
		}catch(Exception e){
			return false;
		}
	}
}
