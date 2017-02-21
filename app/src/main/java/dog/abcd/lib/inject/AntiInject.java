package dog.abcd.lib.inject;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * <b>控件注入</b><br>
 * 先使用@AntiInjectView注解成员变量，再在需要初始化的地方调用inject方法进行注入
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/18 下午 13:51
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/1/18 下午 13:51
 */
public class AntiInject {
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    private AntiInject() {
    }

    /**
     * 开始注入
     *
     * @param activity
     */
    public static void inject(Activity activity) {
        injectViews(activity);
    }

    /**
     * 注入所有的控件
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 遍历所有成员变量
        for (Field field : fields) {

            AntiInjectView viewInjectAnnotation = field
                    .getAnnotation(AntiInjectView.class);
            if (viewInjectAnnotation != null) {
                int viewId = viewInjectAnnotation.value();
                if (viewId != -1) {
                    // 初始化View
                    try {
                        Method method = clazz.getMethod(METHOD_FIND_VIEW_BY_ID,
                                int.class);
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, resView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

        }

    }

    /**
     * 寻找控件
     *
     * @param view 父级控件
     * @param id   id
     * @param <T>
     * @return
     */
    public <T extends View> T $(View view, int id) {
        return (T) view.findViewById(id);
    }

}
