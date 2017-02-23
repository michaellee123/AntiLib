/**
 *
 */
package dog.abcd.lib.watcher;

import java.util.Hashtable;
import java.util.Map;

/**
 * <b>信使</b><br>
 * 保存、传递数据
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016-4-15 上午9:27:32
 */
class AntiMessenger {
    private Map<String, Boolean> mapBoolean = new Hashtable<String, Boolean>();
    private Map<String, Integer> mapInteger = new Hashtable<String, Integer>();
    private Map<String, String> mapString = new Hashtable<String, String>();
    private Map<String, Double> mapDouble = new Hashtable<String, Double>();
    private Map<String, Float> mapFloat = new Hashtable<String, Float>();
    private Map<String, Object> mapObject = new Hashtable<String, Object>();

    public void putBoolean(String key, boolean value) {
        mapBoolean.put(key, value);
    }

    public boolean getBoolean(String key) {
        return mapBoolean.get(key);
    }

    public void putInteger(String key, int value) {
        mapInteger.put(key, value);
    }

    public int getInteger(String key) {
        return mapInteger.get(key);
    }

    public void putString(String key, String value) {
        mapString.put(key, value);
    }

    public String getString(String key) {
        return mapString.get(key);
    }

    public void putDouble(String key, double value) {
        mapDouble.put(key, value);
    }

    public double getDouble(String key) {
        return mapDouble.get(key);
    }

    public void putFloat(String key, float value) {
        mapFloat.put(key, value);
    }

    public float getFloat(String key) {
        return mapFloat.get(key);
    }

    public void putObject(String key, Object value) {
        mapObject.put(key, value);
    }

    public Object getObject(String key) {
        return mapObject.get(key);
    }

}
