package org.lcydream.aop.feature.aspectj.proxyfactory.interceptor;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author luocy
 * @description 方法拦截器
 * @program customer-service
 * @create 2021-03-28
 * @since 1.0
 */
public class EchoServiceMethodInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.printf("拦截到方法: %s%n",invocation.getMethod());
        return invocation.proceed();
        // return null;
    }
}
