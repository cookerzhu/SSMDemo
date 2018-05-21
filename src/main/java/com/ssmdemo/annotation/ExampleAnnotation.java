package com.ssmdemo.annotation;

import java.lang.annotation.*;

/**
 * example annotation
 * Created by zhuguangchuan on 2018/5/21.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExampleAnnotation {
    /**
     * 值
     * @return
     */
    String value() default "";

    /**
     * 条件
     * @return
     */
    String[] conditions() default {};
}
