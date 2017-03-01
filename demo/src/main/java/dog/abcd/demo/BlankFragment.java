package dog.abcd.demo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.NetworkResponse;

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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment implements AntiChangedListener {

    View view;

    @AntiInjectView(R.id.text)
    TextView textView;

    @AntiInjectView(R.id.image)
    ImageView imageView;

    @AntiInjectView(R.id.button)
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        //注入控件
        AntiInject.inject(this, view);
        //注册消息通知
        AntiWatcher.register(this);
        init();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销消息通知
        AntiWatcher.unregister(this);
    }

    public void init() {
        //加载网络图片
        AntiImageLoader.getInstance().display("http://img.weixinyidu.com/151212/c96ee601.jpg", imageView);
        //发起网络请求
        AntiNetwork.builder(getContext())
                .setMethod(AntiNetwork.Method.GET)
                .setTAG(MainActivity.class.getSimpleName())
                .setUrl("http://www.baidu.com")
                .putParam("key", "value")
                .putHeader("key", "value")
                .setListener(new AntiNetworkListener() {
                    @Override
                    public void success(AntiNetwork network, NetworkResponse result) {
                        textView.setText(AntiNetworkConvert.convertResponceToString(result));
                    }

                    @Override
                    public void error(AntiNetwork network, AntiNetworkException error) {
                        AstiAlert.builder(network.getContext())
                                .setTitle("错误信息")
                                .setMessage(error.getMessage())
                                .setTAG(BlankFragment.class.getSimpleName())
                                .create().show();
                    }
                })
                .create()
                .start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WatcherDemoActivity.class));
            }
        });
    }

    //接受消息通知
    @Override
    public void onWatcherChanged(String changedKey) {
        if (changedKey.equals("image")) {
            AntiImageLoader.getInstance().display(AntiWatcher.getString(changedKey), imageView);
            textView.setText(AntiWatcher.getString(changedKey));
        }
    }
}
