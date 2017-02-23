/**
 *
 */
package dog.abcd.lib.watcher;

/**
 * <b>检查数据改变的接口</b><br>
 * 需要检查数据改变的类实现该接口以及方法
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016-4-15 上午9:27:45
 */
public interface AntiChangedListener {
    void onWatcherChanged(String changedKey);
}
