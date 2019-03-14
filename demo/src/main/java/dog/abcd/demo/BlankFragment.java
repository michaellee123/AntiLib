package dog.abcd.demo;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import dog.abcd.lib.alert.AstiAlert;
import dog.abcd.lib.network.AntiNetwork;
import dog.abcd.lib.network.AntiNetworkConvert;
import dog.abcd.lib.network.AntiNetworkException;
import dog.abcd.lib.network.AntiNetworkListener;
import dog.abcd.lib.utils.AntiImageLoader;
import dog.abcd.lib.watcher.AntiChangedListener;
import dog.abcd.lib.watcher.AntiWatcher;

public class BlankFragment extends Fragment implements AntiChangedListener {

    View view;

    TextView textView;

    ImageView imageView;

    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        textView = view.findViewById(R.id.text);
        imageView = view.findViewById(R.id.image);
        button = view.findViewById(R.id.button);
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
        getBaiduNetwork().create().start((new AntiNetworkListener() {
            @Override
            public void success(AntiNetwork network, NetworkResponse result) {
                textView.setText(AntiNetworkConvert.convertResponseToString(result));
            }

            @Override
            public void error(AntiNetwork network, AntiNetworkException error) {
                AstiAlert.builder(network.getContext())
                        .setTitle("错误信息")
                        .setMessage(error.getMessage())
                        .setTAG(BlankFragment.class.getSimpleName())
                        .create().show();
            }
        }));


        //发起网络请求
        new AsyncTask<String, NetworkResponse, NetworkResponse>() {
            @Override
            protected NetworkResponse doInBackground(String... strings) {
                try {
                    NetworkResponse response = getBaiduNetwork().create().perform();
                    AntiNetworkConvert.convertResponseToString(response);
                    return response;
                } catch (VolleyError volleyError) {
                    volleyError.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(NetworkResponse networkResponse) {
                Log.e("network", networkResponse.toString());
            }
        }.execute();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WatcherDemoActivity.class));
            }
        });
    }

    private AntiNetwork.Builder getBaiduNetwork() {
        return AntiNetwork.builder(getContext())
                .setMethod(AntiNetwork.Method.GET)
                .setTAG(MainActivity.class.getSimpleName())
                .setUrl("https://www.baidu.com")
                .putParam("key", "value")
                .putHeader("key", "value");
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
