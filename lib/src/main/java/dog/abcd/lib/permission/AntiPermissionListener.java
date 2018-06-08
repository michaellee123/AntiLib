package dog.abcd.lib.permission;

/**
 * <b>运行时权限请求回调</b><br>
 *
 * @author Michael Lee<br>
 * <b> create at </b>2017/2/23 下午 13:04
 */
public interface AntiPermissionListener {
    void onPermissionRequestFinish(String[] success, String[] failed, String[] refuse);
}
