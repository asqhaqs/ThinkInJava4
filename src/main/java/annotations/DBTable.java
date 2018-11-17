package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by xudong on 2018/6/21.
 * p624
 */
@Target(ElementType.TYPE)  //只应用于类
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    public String name() default "";
}
