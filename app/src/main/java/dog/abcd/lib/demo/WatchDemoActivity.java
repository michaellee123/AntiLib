package dog.abcd.lib.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import dog.abcd.lib.R;
import dog.abcd.lib.inject.AntiInject;
import dog.abcd.lib.inject.AntiInjectView;
import dog.abcd.lib.utils.AntiToast;
import dog.abcd.lib.watcher.AntiWatcher;

public class WatchDemoActivity extends AppCompatActivity {

    @AntiInjectView(R.id.btn_wda_change)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_demo);
        AntiInject.inject(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AntiWatcher.putString("demo", "so easy!");
                AntiToast.show(WatchDemoActivity.this, "返回上个页面查看效果");
            }
        });
    }
}
