package dog.abcd.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * <b> 图片加载</b><br>
 * 用于异步加载图片，需要在Application中调用init方法
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 上午10:41:53
 */
public class AntiImageLoader {
    private DisplayImageOptions options;
    private ImageLoader imageLoader;

    private static AntiImageLoader instance;

    public static AntiImageLoader getInstance() {
        if (instance == null) {
            try {
                throw new Exception("please init AntiImageLoader in your application first");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private AntiImageLoader() {
    }

    /**
     * 显示图片
     *
     * @param uri       图片地址
     * @param imageView 图片控件
     */
    public void display(String uri, ImageView imageView) {
        imageLoader.displayImage(uri, imageView, options);
    }

    /**
     * 显示图片
     *
     * @param uri       图片地址
     * @param imageView 图片控件
     * @param options   自定义option
     */
    public void display(String uri, ImageView imageView,
                        DisplayImageOptions options) {
        imageLoader.displayImage(uri, imageView, options);
    }

    /**
     * 显示图片
     *
     * @param uri       图片地址
     * @param imageView 图片控件
     * @param options   自定义option
     * @param listenser 图片加载监听
     */
    public void display(String uri, ImageView imageView,
                        DisplayImageOptions options, ImageLoadingListener listenser) {
        imageLoader.displayImage(uri, imageView, options, listenser);
    }

    /**
     * 需要在Application中初始化
     *
     * @param context        上下文
     * @param loadingImageId 加载过程中显示的图片
     * @param emptyImageId   加载内容为空显示的图片
     * @param failImageId    加载失败显示的图片
     */
    public static void init(Context context, int loadingImageId, int emptyImageId,
                            int failImageId) {
        instance = new AntiImageLoader();
        instance.imageLoader = ImageLoader.getInstance();
        instance.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        instance.options = new DisplayImageOptions.Builder()
                // 加载过程中显示的图片
                .showImageOnLoading(loadingImageId)
                // 加载内容为空显示的图片
                .showImageForEmptyUri(emptyImageId)
                // 加载失败显示的图片
                .showImageOnFail(failImageId)
                // 是否緩存都內存中
                .cacheInMemory(true)
                // 是否緩存到sd卡上
                .cacheOnDisc(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(200)).build();
    }

    /**
     * 需要在Application中初始化
     *
     * @param context 上下文
     * @param options 自定义Option
     */
    public static void init(Context context, DisplayImageOptions options) {
        instance = new AntiImageLoader();
        instance.imageLoader = ImageLoader.getInstance();
        instance.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        instance.options = options;
    }

    /**
     * 需要在Application中初始化
     *
     * @param context 上下文 <br>
     *                以默认Option显示
     */
    public static void init(Context context) {
        instance = new AntiImageLoader();
        instance.imageLoader = ImageLoader.getInstance();
        instance.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        instance.options = new DisplayImageOptions.Builder()
                // 加载过程中显示的图片
                .showImageOnLoading(null)
                // 加载内容为空显示的图片
                .showImageForEmptyUri(null)
                // 加载失败显示的图片
                .showImageOnFail(null)
                // 是否緩存都內存中
                .cacheInMemory(true)
                // 是否緩存到sd卡上
                .cacheOnDisc(true).considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new FadeInBitmapDisplayer(200)).build();
    }

    /**
     * 获取ImageLoader
     *
     * @return ImageLoader
     */
    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    /**
     * 获取显示设置
     *
     * @return 显示设置
     */
    public DisplayImageOptions getDisplayImageOptions() {
        return options;
    }
}
