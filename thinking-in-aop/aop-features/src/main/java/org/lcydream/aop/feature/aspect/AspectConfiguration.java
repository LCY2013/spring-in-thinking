package org.lcydream.aop.feature.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
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

    /**
     *  定义Around通知，利用pointcut过滤执行方法
     *
     * around 和 before 同时在时有何区别？
     *  around需要主动去操作执行目标方法，before则不需要就会执行目标方法
     *  两个同时针对某一个Pointcut时，around优先执行，并且执行了pjp.proceed()后面的其他（before和目标方法操作...）才会生效
     */
    @Around("doAnyMethod()")
    public Object aroundAnyPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@aroundAnyPublicMethod at any public method.");
        return pjp.proceed();
    }

}
