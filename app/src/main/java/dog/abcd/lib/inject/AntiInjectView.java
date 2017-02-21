package dog.abcd.lib.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <b>Title</b><br>
 * Description
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
