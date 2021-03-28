package org.lcydream.aop.feature.aspect.api;

import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author luocy
 * @description 利用API的方式实现Pointcut匹配
 * @program customer-service
 * @create 2021-03-28
 * @see StaticMethodMatcherPointcut
 * @since 1.0
 */
public class EchoServicePointcut extends StaticMethodMatcherPointcut {

    private String methodName;

    private Class targetClass;

    public EchoServicePointcut(String methodName, Class targetClass) {
        this.methodName = methodName;
        this.targetClass = targetClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return Objects.equals(method.getName(), this.methodName)
                && this.targetClass.isAssignableFrom(targetClass);
    }
}
