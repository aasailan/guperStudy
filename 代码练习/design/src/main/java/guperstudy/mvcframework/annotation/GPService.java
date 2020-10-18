package guperstudy.mvcframework.annotation;
import java.lang.annotation.*;
/**
 * @author joe
 * @program: design
 * @description: 自定义server注解
 * @date 2020-10-18 09:50:43
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GPService {
    String value() default "";
}
