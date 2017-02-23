package dog.abcd.lib.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import dog.abcd.lib.utils.AntiLog;

/**
 * <b>运行时权限请求工具</b><br>
 * 首先在在Activity中首先需要重写onRequestPermissionsResult方法，在方法中调用此类中的handlePermission方法即可。<br/>
 * 在需要请求权限的时候调用此类中的requestPermission方法即可
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/2/23 下午 13:04
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/2/23 下午 13:04
 */
public class AntiPermissionUtils {

    private Map<Integer, Map<String, AntiPermissionListener>> map = new HashMap<>();

    private Map<Integer, Activity> activitys = new HashMap<>();

    private static AntiPermissionUtils instance;

    private AntiPermissionUtils() {

    }

    public static AntiPermissionUtils getInstance() {
        if (instance == null) {
            synchronized (AntiPermission.class) {
                if (instance == null) {
                    instance = new AntiPermissionUtils();
                }
            }
        }
        return instance;
    }

    public void requestPermission(Activity activity, AntiPermission... antiPermissions) {

        if (Build.VERSION.SDK_INT < 23) {
            for (AntiPermission antiPermission : antiPermissions) {
                antiPermission.listener.success();
            }
        }

        Map<String, AntiPermissionListener> sa = new HashMap<>();
        for (AntiPermission antiPermission : antiPermissions) {
            if (ContextCompat.checkSelfPermission(activity, antiPermission.permissionName) != PackageManager.PERMISSION_GRANTED) {
                sa.put(antiPermission.permissionName, antiPermission.listener);
            } else {
                antiPermission.listener.success();
            }
        }
        if (sa.size() == 0) {
            return;
        }
        int code = activitys.hashCode();
        code = code % 1000 + code / 100000;
        activitys.put(code, activity);
        map.put(code, sa);

        String[] permissionNames = new String[sa.size()];
        Iterator iterator = sa.keySet().iterator();
        int index = 0;
        while (iterator.hasNext() && index < permissionNames.length) {
            permissionNames[index] = (String) iterator.next();
            index++;
        }
        ActivityCompat.requestPermissions(activity, permissionNames,
                code);
    }

    public void handlePermission(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || permissions.length == 0 || grantResults.length == 0) {
            AntiLog.e(AntiPermissionUtils.class.getSimpleName(), "some arguments is null!");
            return;
        }
        if (!map.containsKey(requestCode)) {
            AntiLog.e(AntiPermissionUtils.class.getSimpleName(), "Permission is not registed!");
            return;
        }
        Map<String, AntiPermissionListener> antiPermissions = map.get(requestCode);
        Activity activity = activitys.get(requestCode);
        for (int i = 0; i < permissions.length; i++) {
            boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(activity, permissions[i]);
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                antiPermissions.get(permissions[i]).success();
            } else {
                if (isTip) {
                    antiPermissions.get(permissions[i]).failed();
                } else {
                    antiPermissions.get(permissions[i]).refuse();
                }
            }
        }
        map.remove(antiPermissions);
        activitys.remove(activity);
    }


}
