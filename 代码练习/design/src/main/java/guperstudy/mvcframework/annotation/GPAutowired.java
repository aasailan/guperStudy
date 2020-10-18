package guperstudy.mvcframework.annotation;

import java.lang.annotation.*;

/**
 * @author joe
 * @program: design
 * @description: 自定义Autowired注解
 * @date 2020-10-18 09:52:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPAutowired {
    String value() default "";
}
