package dog.abcd.demo;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import dog.abcd.lib.utils.AntiLog;
import dog.abcd.lib.permission.AntiPermissionUtils;
import dog.abcd.lib.permission.AntiPermission;
import dog.abcd.lib.permission.AntiPermissionListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求权限
        AntiPermissionUtils.getInstance().requestPermission(this,
                new AntiPermission(Manifest.permission.SEND_SMS, new AntiPermissionListener() {
                    @Override
                    public void success() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "允许短信");
                    }

                    @Override
                    public void failed() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不允许短信");
                    }

                    @Override
                    public void refuse() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不再提示短信");
                    }
                })
                ,
                new AntiPermission(Manifest.permission.CAMERA, new AntiPermissionListener() {
                    @Override
                    public void success() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "允许拍照");
                    }

                    @Override
                    public void failed() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不允许拍照");
                    }

                    @Override
                    public void refuse() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不再提示拍照");
                    }
                })
                ,
                new AntiPermission(Manifest.permission.CALL_PHONE, new AntiPermissionListener() {
                    @Override
                    public void success() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "允许拨号");
                    }

                    @Override
                    public void failed() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不允许拨号");
                    }

                    @Override
                    public void refuse() {
                        AntiLog.e(MainActivity.class.getSimpleName(), "不再提示拨号");
                    }
                }));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        AntiPermissionUtils.getInstance().handlePermission(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
