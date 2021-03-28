package org.lcydream.aop.feature.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author luocy
 * @description 切点 {@link Pointcut}
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
@Aspect
public class AspectConfiguration {

    /**
     * 切点定义，过滤所有public 方法，这里只作为过滤依据不作为其他用途
     * 通常该方法为私有，方法内部为空
     */
    @Pointcut("execution(public * *(..))")
    private void doAnyMethod() {
        System.out.println("@Pointcut at any public method.");
    }

    /**
     *  定义前置通知，利用pointcut过滤执行方法
     */
    @Before("doAnyMethod()")
    public void beforeAnyPublicMethod() {
        System.out.println("@beforeAnyPublicMethod at any public method.");
    }

}
