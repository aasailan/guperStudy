package guperstudy.mvcframework.annotation;
import java.lang.annotation.*;

/**
 * @author joe
 * @program: design
 * @description: 自定义RequestParam注解
 * @date 2020-10-18 10:09:25
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPRequestParam {
    String value() default "";
}
