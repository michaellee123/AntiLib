package dog.abcd.lib.permission;

/**
 * <b>运行时权限请求</b><br>
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/23 下午 13:04
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/23 下午 13:04
 */
public class AntiPermission {
    public AntiPermission(String permissionName, AntiPermissionListener listener) {
        this.permissionName = permissionName;
        this.listener = listener;
    }

    String permissionName;
    AntiPermissionListener listener;
}