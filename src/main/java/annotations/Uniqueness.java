package annotations;

/**
 * Created by xudong on 2018/6/21.
 * p625
 */
public @interface Uniqueness {
    Constraints constraints() default @Constraints(unique = true);
}
