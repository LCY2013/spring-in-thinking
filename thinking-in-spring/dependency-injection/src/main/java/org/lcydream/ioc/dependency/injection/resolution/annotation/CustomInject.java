package org.lcydream.ioc.dependency.injection.resolution.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解实现注入
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomInject {

    /**
     * Declares whether the annotated dependency is required.
     * <p>Defaults to {@code true}.
     */
    boolean required() default true;

}
