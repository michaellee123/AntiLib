package dog.abcd.lib.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;

import dog.abcd.lib.R;
import dog.abcd.lib.alert.AstiAlert;
import dog.abcd.lib.inject.AntiInject;
import dog.abcd.lib.inject.AntiInjectView;
import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkConvert;
import dog.abcd.lib.network.AntiNetworkException;
import dog.abcd.lib.network.AntiNetworkListener;
import dog.abcd.lib.utils.AntiImageLoader;
import dog.abcd.lib.watcher.AntiChangedListener;
import dog.abcd.lib.watcher.AntiWatcher;

public class MainActivity extends AppCompatActivity implements AntiChangedListener {

    @AntiInjectView(R.id.tv_am_hello)
    TextView textView;

    @AntiInjectView(R.id.iv_am_hello)
    ImageView imageView;

    @AntiInjectView(R.id.btn_ma_hello)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AntiInject.inject(this);
        AntiWatcher.register(this);
        AntiNetwork.builder()
                .setMethod(AntiNetwork.Method.GET)
                .setTAG(MainActivity.class.getSimpleName())
                .setUrl("http://www.baidu.com")
                .setListener(new AntiNetworkListener() {
                    @Override
                    public void success(AntiNetwork network, NetworkResponse result) {
                        textView.setText(AntiNetworkConvert.convertResponceToString(result));
                    }

                    @Override
                    public void error(AntiNetwork network, AntiNetworkException error) {
                        AstiAlert.builder(MainActivity.this)
                                .setTitle("错误信息")
                                .setMessage(error.getMessage())
                                .create().show(MainActivity.class.getSimpleName());
                    }
                })
                .create()
                .start();
        AntiImageLoader.getInstance().display("http://www.ljzfin.com/uploads/1/image/public/201507/20150730135549_938o8holhs.jpg", imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WatchDemoActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AntiWatcher.unregister(this);
    }

    @Override
    public void onWatcherChanged(String changedKey) {
        if (changedKey.equals("demo")) {
            button.setText(AntiWatcher.getString(changedKey));
        }
    }
}
