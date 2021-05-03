package org.lcydream.aop.feature.aspectj.advice;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author luocy
 * @description ThrowsAdvice 处理器
 * @create 2021-05-03
 * @since 1.0
 */
public class ThrowsAdviceHandler implements ThrowsAdvice {

    /**
     * 异常后处理
     * @param e 异常信息
     * @see ThrowsAdviceInterceptor#ThrowsAdviceInterceptor(java.lang.Object)
     */
    // 模糊匹配异常，如果存在多个afterThrowing方法，则后面的方法覆盖前面的方法
    public void afterThrowing(Exception e) {
    // 建议使用一种afterThrowing方法，或者根据异常精准匹配
    //public void afterThrowing(RuntimeException e) {
        System.out.printf("Exception: %s\n", e);
    }

    /**
     * 异常信息后的处理
     * @param method 目标方法
     * @param args 目标方法的目标参数
     * @param target 目标对象
     * @param e 异常信息
     */
    public void afterThrowing(Method method, Object[] args, Object target, Exception e) {
        System.out.printf("Method: %s , Args: %s , Target: %s , Exception: %s\n",
                method,
                Arrays.asList(args),
                target,
                e);
    }

}
