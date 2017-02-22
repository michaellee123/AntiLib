# AntiLib
在使用时需要在Application中初始化一些东西，包括AntiImageLoader(网络图片加载),AntiNetworkManager(网络请求)。
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
这里统一处理之后可以自己实现一个Cookie的管理
```
### 网络请求的调用
```
AntiInject.inject(this);
        AntiWatcher.register(this);
        AntiNetwork.builder()
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
                                .create().show(MainActivity.class.getSimpleName());
                    }
                })
                .create()
                .start();
```
以上只是对网络请求的简单介绍，需要详细了解可以查看www.abcd.dog中的文档，也可以看看源码
