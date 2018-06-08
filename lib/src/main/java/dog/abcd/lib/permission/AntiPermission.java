package dog.abcd.lib.permission;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>运行时权限请求</b><br>
 *
 * @author Michael Lee<br>
 * <b> create at </b>2017/2/23 下午 13:04
 */
class AntiPermission {
    private List<String> permissionsSuccess = new ArrayList<>();
    private List<String> permissionsFailed = new ArrayList<>();
    private List<String> permissionsRefuse = new ArrayList<>();
    private List<String> permissionsToRequest = new ArrayList<>();
    private AntiPermissionListener listener;
    private Activity activity;

    public AntiPermission(Activity activity, AntiPermissionListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    public void addRequestPermission(String permission) {
        permissionsToRequest.add(permission);
    }

    public int getRequestCount() {
        return permissionsToRequest.size();
    }

    public String[] getRequestPermissions() {
        return permissionsToRequest.toArray(new String[]{});
    }

    public void addPermissionSuccess(String permission) {
        permissionsSuccess.add(permission);
    }

    public void addPermissionFailed(String permission) {
        permissionsFailed.add(permission);
    }

    public void addPermissionRefuse(String permission) {
        permissionsRefuse.add(permission);
    }

    public void doCallBack() {
        String[] empty = new String[]{};
        listener.onPermissionRequestFinish(permissionsSuccess.toArray(empty), permissionsFailed.toArray(empty), permissionsRefuse.toArray(empty));
        permissionsSuccess.clear();
        permissionsFailed.clear();
        permissionsRefuse.clear();
        permissionsToRequest.clear();
    }

    public Activity getActivity() {
        return activity;
    }
}