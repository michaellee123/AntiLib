package dog.abcd.lib.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

/**
 * <b>屏幕操作</b><br>
 * 获取屏幕尺寸，截图，单位换算
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2016-1-28 下午1:47:48
 */
public class AntiScreenUtils {
    private AntiScreenUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException( "cannot be instantiated" );
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( outMetrics );
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService( Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( outMetrics );
        return outMetrics.heightPixels;
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName( "com.android.internal.R$dimen" );
            Object object = clazz.newInstance();
            int height = Integer.parseInt( clazz.getField( "status_bar_height" )
                    .get( object ).toString() );
            statusHeight = context.getResources().getDimensionPixelSize( height );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 获取当前屏幕截图，包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled( true );
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        int width = getScreenWidth( activity );
        int height = getScreenHeight( activity );
        Bitmap bp = null;
        bp = Bitmap.createBitmap( bmp, 0, 0, width, height );
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取当前屏幕截图，不包含状态栏
     *
     * @param activity
     * @return
     */
    public static Bitmap snapShotWithoutStatusBar(Activity activity) {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled( true );
        view.buildDrawingCache();
        Bitmap bmp = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame( frame );
        int statusBarHeight = frame.top;

        int width = getScreenWidth( activity );
        int height = getScreenHeight( activity );
        Bitmap bp = null;
        bp = Bitmap.createBitmap( bmp, 0, statusBarHeight, width, height
                - statusBarHeight );
        view.destroyDrawingCache();
        return bp;

    }

    /**
     * 获取屏幕密度
     *
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * 获取当前手机是否有虚拟按键(NavigationBar)
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static boolean checkDeviceHasNavigationBar(Context context) {

        //通过判断设备是否有返回键、菜单键(不是虚拟键,是手机屏幕外的按键)来确定是否有navigation bar
        boolean hasMenuKey = ViewConfiguration.get( context )
                .hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap
                .deviceHasKey( KeyEvent.KEYCODE_BACK );

        if (!hasMenuKey && !hasBackKey) {
            return true;
        }
        return false;
    }


    /**
     * 获取当前虚拟按键高度（NavigationBar),如果当前没有虚拟按键则返回0
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (!checkDeviceHasNavigationBar( context )) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier( "navigation_bar_height", "dimen", "android" );
        int height = resources.getDimensionPixelSize( resourceId );
        return height;
    }
}
