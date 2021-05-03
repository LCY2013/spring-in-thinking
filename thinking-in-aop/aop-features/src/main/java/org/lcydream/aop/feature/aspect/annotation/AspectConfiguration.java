package org.lcydream.aop.feature.aspect.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;

import java.util.Random;

/**
 * @author luocy
 * @description 切点 {@link Pointcut}
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
@Order
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
     * 定义前置通知，利用pointcut过滤执行方法
     */
    @Before("doAnyMethod()")
    public void beforeAnyPublicMethod() throws Throwable {
        final Random random = new Random();
        if (random.nextBoolean()) {
            throw new RuntimeException("For purpose .");
        }
        System.out.println("@beforeAnyPublicMethod at any public method.");
    }

    /**
     * 定义Around通知，利用pointcut过滤执行方法
     * <p>
     * around 和 before 同时在时有何区别？
     * around需要主动去操作执行目标方法，before则不需要就会执行目标方法
     * 在同一个优先级下两个同时针对某一个Pointcut时，around优先执行，并且执行了pjp.proceed()后面的其他（before和目标方法操作...）才会生效
     */
    @Around("doAnyMethod()")
    public Object aroundAnyPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@aroundAnyPublicMethod at any public method.");
        return pjp.proceed();
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    @After("doAnyMethod()")
    public void afterAnyPublicMethod() {
        System.out.println("@afterAnyPublicMethod at any public method.");
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    @AfterReturning("doAnyMethod()")
    public void afterReturningAnyPublicMethod() {
        System.out.println("@afterReturningAnyPublicMethod at any public method.");
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    @AfterThrowing("doAnyMethod()")
    public void afterThrowingAnyPublicMethod() {
        System.out.println("@afterThrowingAnyPublicMethod at any public method.");
    }

}
