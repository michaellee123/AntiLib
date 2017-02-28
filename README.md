# AntiLib

>在使用时需要在Application中初始化一些东西，包括AntiImageLoader(网络图片加载),AntiNetworkManager(网络请求)。

>**可以通过在gradle中使用compile 'dog.abcd:antilib:1.0.3'引用**

# 调用示例
## 网络请求的调用
### 网络网络请求的初始化
```
        AntiNetworkManager.init(this, 0, new IDefaultParams() {
            @Override
            public Map<String, String> getDefaultParams() {
                return null;
            }

            @Override
            public Map<String, String> getDefaultHeaders() {
                return null;
            }
        }, new IDefaultListener() {
            @Override
            public boolean success(AntiNetwork network, NetworkResponse result) {
                return false;
            }

            @Override
            public boolean error(AntiNetwork network, AntiNetworkException error) {
                return false;
            }
        });
```
### 网络请求的调用
```
        AntiNetwork.builder(this)
                .setMethod(AntiNetwork.Method.GET)
                .setTAG(MainActivity.class.getSimpleName())
                .setUrl("http://www.baidu.com")
                .putParam("key","value")
                .putHeader("key","value")
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
                                .setTAG(MainActivity.class.getSimpleName())
                                .create().show();
                    }
                })
                .create()
                .start();
```
## 加载网络图片
### 网络图片加载的初始化
```
        AntiImageLoader.init(this);
        //AntiImageLoader.init(this,加载中的图片,加载图片为空时的图片,加载失败的图片);
        //AntiImageLoader.init(this,自定义图片加载配置);
```
### 加载网络图片
```
        AntiImageLoader.getInstance().display("http://img.weixinyidu.com/151212/c96ee601.jpg", imageView);
```
## 控件注入
在成员变量上给出注解
```
        @AntiInjectView(R.id.text)
        TextView textView;

        @AntiInjectView(R.id.image)
        ImageView imageView;
```
在onCreate中调用注入方法
```
        AntiInject.inject(this);//Activity的注入，默认在contentView中去调用findViewById方法
        AntiInject.inject(this, view);//第一个参数是包括成员变量的类，第二个参数是包括View的父级View
```
## 权限请求
### 发起权限请求并设置回调
```
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
```
### 重写onRequestPermissionsResult方法
```
        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            AntiPermissionUtils.getInstance().handlePermission(requestCode, permissions, grantResults);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
```
## 组件通信
### 接受消息的类
需要实现AntiChangedListener，并在onCreate的时候调用AntiWatcher.register(this)，在onDestroy的时候调用AntiWatcher.unregister(this)方法
```
public class BlankFragment extends Fragment implements AntiChangedListener {

    View view;

    @AntiInjectView(R.id.image)
    ImageView imageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        AntiWatcher.register(this);
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        AntiWatcher.unregister(this);
    }

    @Override
    public void onWatcherChanged(String changedKey) {
        if (changedKey.equals("image")) {
            AntiImageLoader.getInstance().display(AntiWatcher.getString(changedKey), imageView);
        }
    }
}

```
### 发送消息的类
只需要调用AntiWatcher.put***方法即可
```
                AntiWatcher.putString("image", "http://imgsrc.baidu.com/forum/w%3D580/sign=0e56606c48fbfbeddc59367748f1f78e/df67e62a6059252d80d3ec57379b033b5ab5b93f.jpg");
```
# Demo
Demo已经上传到GitHub