package xyz.imlent.wfe.log.annotation;

import java.lang.annotation.*;

/**
 * @author wfee
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

    /**
     * 日志描述
     *
     * @return {String}
     */
    String value() default "";
}
