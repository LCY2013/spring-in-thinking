package org.lcydream.aop.feature.aspect.xml;

import org.aspectj.lang.ProceedingJoinPoint;
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
public class AspectXmlConfiguration {

    public void beforeAnyPublicMethod() {
        System.out.println("@beforeAnyPublicMethod at any public method.");
    }

    public Object aroundAnyPublicMethod(ProceedingJoinPoint pjp) throws Throwable {
        System.out.println("@aroundAnyPublicMethod at any public method." + pjp.getSignature());
        return pjp.proceed();
    }
}
