package dog.abcd.lib.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>寻找控件</b><br>
 * 对控件注解后在onCreate中调用AntiInject类中的Inject方法进行注入
 *
 * @author Michael Lee<br>
 *         <b> create at </b>2017/1/18 下午 13:43
 * @Company RZQC
 * @Mender Michael Lee<br>
 * <b> change at </b>2017/1/18 下午 13:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AntiInjectView {
    int value();
}
