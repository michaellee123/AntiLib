package dog.abcd.lib.permission;

/**
 * <b>运行时权限请求回调</b><br>
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/23 下午 13:04
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/23 下午 13:04
 */
public interface AntiPermissionListener {
    /**
     * 成功
     */
    void success();

    /**
     * 失败
     */
    void failed();

    /**
     * 用户选择了不再提示
     */
    void refuse();
}
