package org.lcydream.aop.feature.pointcut;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.lcydream.aop.staticproxy.EchoService;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author luocy
 * @description echo service 拦截点
 * @program customer-service
 * @create 2021-05-03
 * @since 1.0
 */
public class EchoServiceMethodPointcut implements Pointcut {

    /**
     *  单例
     */
    public final static EchoServiceMethodPointcut INSTANCE = new EchoServiceMethodPointcut();

    /**
     *  私有化构造函数
     */
    private EchoServiceMethodPointcut() {

    }

    /**
     * 类过滤器
     *
     * @return {@link ClassFilter}
     */
    @Override
    public ClassFilter getClassFilter() {
        // 过滤出EchoService 接口或者子接口、子类
        return EchoService.class::isAssignableFrom;
    }

    /**
     * 方法匹配
     *
     * @return {@link MethodMatcher}
     */
    @Override
    public MethodMatcher getMethodMatcher() {

        return new MethodMatcher() {
            /**
             *
             * @param method 方法名称
             * @param targetClass 目标类型
             * @return {@code true} {@code false}
             * @see MethodBeforeAdviceInterceptor
             * @see MethodInterceptor
             * @see BeforeAdvice
             * @see Advice
             */
            @Override
            public boolean matches(Method method, Class<?> targetClass) {
                // 匹配方法名称为 cli 的方法
                return Objects.equals(method.getName(), "cil") &&
                        method.getParameters().length == 1 &&
                        Objects.equals(String.class, method.getParameterTypes()[0]);
            }

            @Override
            public boolean isRuntime() {
                return false;
            }

            @Override
            public boolean matches(Method method, Class<?> targetClass, Object... args) {
                return false;
            }
        };
    }

}
