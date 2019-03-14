package dog.abcd.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dog.abcd.lib.utils.AntiToast;
import dog.abcd.lib.watcher.AntiWatcher;

/**
 * 向上个页面发送通知
 */
public class WatcherDemoActivity extends AppCompatActivity {

    String[] images = new String[]{
            "https://a-ssl.duitang.com/uploads/item/201608/02/20160802212747_Lx5Yd.thumb.700_0.jpeg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=d8630275fefaaf5184e381b7bc5594ed/d7dd9c4bd11373f0947ac5cca70f4bfbfaed04bd.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=b6966cbdb78f8c54e3d3c5270a282dee/756eb3025aafa40f523c648ea864034f79f0190c.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=982a18c2ce11728b302d8c2af8fdc3b3/0ce533087bf40ad12bf350b0542c11dfa8eccee8.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=3adac14461d0f703e6b295d438fb5148/e343d61190ef76c617266c789e16fdfaae51670f.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=82ee7b9ad42a60595210e1121835342d/869428d8bc3eb135e01b20e8a51ea8d3fc1f4427.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=cbb6e457940a304e5222a0f2e1c9a7c3/8d4a821c8701a18bb17f821c9d2f07082938fe6f.jpg",
            "http://imgsrc.baidu.com/forum/w%3D580/sign=0e56606c48fbfbeddc59367748f1f78e/df67e62a6059252d80d3ec57379b033b5ab5b93f.jpg"
    };

    static int i = 0;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watcher_demo);
        button = findViewById(R.id.change);
        i++;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AntiWatcher.putString("image", images[i % (images.length - 1)]);
                AntiToast.show(WatcherDemoActivity.this, "返回上个页面查看结果");
            }
        });
    }
}
