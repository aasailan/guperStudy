package guperstudy.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author joe
 * @program: design
 * @description: 自定义Controller注解
 * @date 2020-10-18 09:53:51
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPController {
    String value() default "";
}
