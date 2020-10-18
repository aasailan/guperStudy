package guperstudy.mvcframework.annotation;
import java.lang.annotation.*;
/**
 * @author joe
 * @program: design
 * @description: 自定义RequestMapping注解
 * @date 2020-10-18 09:55:36
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestMapping {
    String value() default "";
}
