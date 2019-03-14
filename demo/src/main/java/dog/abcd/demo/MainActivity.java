package dog.abcd.demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import dog.abcd.lib.permission.AntiPermissionListener;
import dog.abcd.lib.permission.AntiPermissionUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求权限
        AntiPermissionUtil.getInstance().requestPermission(this, new AntiPermissionListener() {
            @Override
            public void onPermissionRequestFinish(String[] success, String[] failed, String[] refuse) {
                if (success.length == 2) {
                    //都允许了
                }
            }
        }, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        AntiPermissionUtil.getInstance().handlePermission(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
