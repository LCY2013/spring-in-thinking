package org.lcydream.aop.feature.aspect.xml;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Random;

/**
 * @author luocy
 * @description 切点 {@link Pointcut}
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
public class AspectXmlConfiguration {

    public void beforeAnyPublicMethod() {
        final Random random = new Random();
        if (random.nextBoolean()) {
            throw new RuntimeException("For purpose xml config.");
        }
        System.out.println("@beforeAnyPublicMethod at any public method.");
    }

    public Object aroundAnyPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@aroundAnyPublicMethod at any public method." + pjp.getSignature());
        return pjp.proceed();
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    public void afterAnyPublicMethod() {
        System.out.println("@afterAnyPublicMethod at any public method.");
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    public void afterReturningAnyPublicMethod() {
        System.out.println("@afterReturningAnyPublicMethod at any public method.");
    }

    /**
     * 定义后置通知，利用pointcut执行过滤方法
     */
    public void afterThrowingAnyPublicMethod() {
        System.out.println("@afterThrowingAnyPublicMethod at any public method.");
    }
}
