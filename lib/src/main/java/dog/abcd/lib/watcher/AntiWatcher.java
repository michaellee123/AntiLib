/**
 *
 */
package dog.abcd.lib.watcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>观察者</b><br>
 * 用于监测数据的改变，分别调用register和unregister进行注册和注销，调用put***方法修改数据，调用get***方法获取数据
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016-4-15 上午10:11:07
 */
public class AntiWatcher {
    private AntiMessenger theMessenger = new AntiMessenger();
    private Map<String, AntiChangedListener> listenerMap = new HashMap<>();
    private static AntiWatcher instance;

    private AntiWatcher() {
    }

    private static AntiWatcher getInstance() {
        if (instance == null) {
            synchronized (AntiWatcher.class) {
                if (instance == null) {
                    instance = new AntiWatcher();
                }
            }
        }
        return instance;
    }

    /**
     * 注册，注册后才能够获得回调
     *
     * @param listener
     */
    public static void register(AntiChangedListener listener) {
        getInstance().listenerMap.put(listener.getClass().getSimpleName(), listener);
    }

    /**
     * 注销
     *
     * @param listener
     */
    public static void unregister(AntiChangedListener listener) {
        getInstance().listenerMap.remove(listener.getClass().getSimpleName());
    }

    /**
     * 通知数据变化
     */
    private void notifyDataChanged(String changedKey) {
        Collection<AntiChangedListener> listeners = listenerMap.values();
        for (AntiChangedListener listener : listeners) {
            listener.onWatcherChanged(changedKey);
        }
    }

    /**
     * 通知数据变化
     */
    private void notifyDataChanged(String changedKey, String... className) {
        for (String str : className) {
            listenerMap.get(str).onWatcherChanged(changedKey);
        }
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putString(String key, String value) {
        getInstance().theMessenger.putString(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static String getString(String key) {
        final String string = getInstance().theMessenger.getString(key);
        return string;
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putBoolean(String key, boolean value) {
        getInstance().theMessenger.putBoolean(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static boolean getBoolean(String key) {
        final boolean bool = getInstance().theMessenger.getBoolean(key);
        return bool;
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putDouble(String key, double value) {
        getInstance().theMessenger.putDouble(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static double getDouble(String key) {
        final double d = getInstance().theMessenger.getDouble(key);
        return d;
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putFloat(String key, float value) {
        getInstance().theMessenger.putFloat(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static float getFloat(String key) {
        final float d = getInstance().theMessenger.getFloat(key);
        return d;
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putInteger(String key, int value) {
        getInstance().theMessenger.putInteger(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static int getInteger(String key) {
        final int i = getInstance().theMessenger.getInteger(key);
        return i;
    }

    /**
     * 通知所有类
     *
     * @param key
     * @param value
     */
    public static void putObject(String key, Object value) {
        getInstance().theMessenger.putObject(key, value);
        getInstance().notifyDataChanged(key);
    }

    public static Object getObject(String key) {
        final Object object = getInstance().theMessenger.getObject(key);
        return object;
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putString(String key, String value, String... className) {
        getInstance().theMessenger.putString(key, value);
        getInstance().notifyDataChanged(key, className);
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putBoolean(String key, boolean value, String... className) {
        getInstance().theMessenger.putBoolean(key, value);
        getInstance().notifyDataChanged(key, className);
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putDouble(String key, double value, String... className) {
        getInstance().theMessenger.putDouble(key, value);
        getInstance().notifyDataChanged(key, className);
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putFloat(String key, float value, String... className) {
        getInstance().theMessenger.putFloat(key, value);
        getInstance().notifyDataChanged(key, className);
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putInteger(String key, int value, String... className) {
        getInstance().theMessenger.putInteger(key, value);
        getInstance().notifyDataChanged(key, className);
    }

    /**
     * @param key
     * @param value
     * @param className 指定特点的类接受通知（getSimpleName）
     */
    public static void putObject(String key, Object value, String... className) {
        getInstance().theMessenger.putObject(key, value);
        getInstance().notifyDataChanged(key, className);
    }

}
