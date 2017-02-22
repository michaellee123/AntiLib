package dog.abcd.lib.alert;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>弹窗管理器</b><br>
 * 对所有弹窗进行管理
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/17 下午 16:35
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/1/17 下午 16:35
 */
public class AstiAlertManager {
    private AstiAlertManager() {
    }

    private static AstiAlertManager instance;

    public static AstiAlertManager getInstance() {
        if (instance == null) {
            synchronized (AstiAlertManager.class) {
                if (instance == null) {
                    instance = new AstiAlertManager();
                }
            }
        }
        return instance;
    }

    Map<String, AstiAlertBase> queue = new HashMap<>();

    /**
     * 显示
     *
     * @param TAG   标识
     * @param alert 弹窗
     */
    public void show(String TAG, AstiAlertBase alert) {
        alert.show(TAG);
    }

    /**
     * 隐藏
     *
     * @param TAG 标识
     */
    public void dismiss(String TAG) {
        queue.get(TAG).dismiss();
    }

    /**
     * 取消
     *
     * @param TAG 标识
     */
    public void cancel(String TAG) {
        queue.get(TAG).cancel();
    }

    /**
     * 添加到map
     *
     * @param alert
     */
    public void add(AstiAlertBase alert) {
        if (queue.containsKey(alert.getTAG())) {
            AstiAlertBase hasAlert = queue.get(alert.getTAG());
            if (hasAlert != null && hasAlert.getDialog().isShowing()) {
                hasAlert.dismiss();
            }
        }
        queue.put(alert.getTAG(), alert);
    }

    /**
     * 从map中删除
     *
     * @param alert
     */
    public void remove(AstiAlertBase alert) {
        queue.remove(alert.getTAG());
    }

    /**
     * 获取弹窗
     *
     * @param TAG 标识
     * @return
     */
    public AstiAlertBase getAlert(String TAG) {
        return queue.get(TAG);
    }
}
