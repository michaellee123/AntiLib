package dog.abcd.lib.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.HashMap;
import java.util.Map;

import dog.abcd.lib.utils.AntiLog;

/**
 * <b>运行时权限请求工具</b><br>
 * 首先在在Activity中首先需要重写onRequestPermissionsResult方法，在方法中调用此类中的handlePermission方法即可。<br>
 * 在需要请求权限的时候调用此类中的requestPermission方法即可
 *
 * @author Michael Lee<br>
 * <b> create at </b>2017/2/23 下午 13:04
 */
public class AntiPermissionUtil {

    private Map<Integer, AntiPermission> map = new HashMap<>();
    private static AntiPermissionUtil instance;

    private AntiPermissionUtil() {
    }

    public static AntiPermissionUtil getInstance() {
        if (instance == null) {
            synchronized (AntiPermission.class) {
                if (instance == null) {
                    instance = new AntiPermissionUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 进行权限请求
     *
     * @param activity
     * @param listener
     * @param permissions
     */
    public void requestPermission(Activity activity, AntiPermissionListener listener, String... permissions) {
        if (Build.VERSION.SDK_INT < 23) {
            listener.onPermissionRequestFinish(permissions, new String[]{}, new String[]{});
            return;
        }
        AntiPermission antiPermission = new AntiPermission(activity, listener);
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                antiPermission.addRequestPermission(permission);
            } else {
                antiPermission.addPermissionSuccess(permission);
            }
        }
        if (antiPermission.getRequestCount() == 0) {
            antiPermission.doCallBack();
            return;
        }
        int code = activity.hashCode();
        code = code % 1000 + code / 100000;
        map.put(code, antiPermission);
        String[] permissionNames = antiPermission.getRequestPermissions();
        ActivityCompat.requestPermissions(activity, permissionNames,
                code);
    }

    /**
     * 由AntiPermissionUtil接管权限回调，在Activity中首先需要重写onRequestPermissionsResult方法调用此方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public void handlePermission(int requestCode, String[] permissions, int[] grantResults) {
        if (permissions == null || grantResults == null || permissions.length == 0 || grantResults.length == 0) {
            AntiLog.e(AntiPermissionUtil.class.getSimpleName(), "some arguments is null!");
            return;
        }
        if (!map.containsKey(requestCode)) {
            AntiLog.e(AntiPermissionUtil.class.getSimpleName(), "Permission is not registed!");
            return;
        }
        AntiPermission antiPermissions = map.get(requestCode);
        for (int i = 0; i < permissions.length; i++) {
            boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(antiPermissions.getActivity(), permissions[i]);
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                antiPermissions.addPermissionSuccess(permissions[i]);
            } else {
                if (isTip) {
                    antiPermissions.addPermissionFailed(permissions[i]);
                } else {
                    antiPermissions.addPermissionRefuse(permissions[i]);
                }
            }
        }
        antiPermissions.doCallBack();
        map.remove(antiPermissions);
    }

    /**
     * 跳转到应用详情页面，手动进行授权设置
     *
     * @param activity
     */
    public static void toPermissionSetting(Activity activity) {
        Intent intent = getAppDetailSettingIntent(activity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private static Intent getAppDetailSettingIntent(Activity activity) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
        }
        return localIntent;
    }
}
