/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-02-27
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.aop.interceptor;

import org.lcydream.aop.utils.DateUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 代理器处理类
 * @create 2021-02-27
 */
public class ProxyHandler implements InvocationHandler {

    /**
     *  处理前置拦截
     */
    private List<AopBeforeInterceptor> aopBeforeInterceptors = new ArrayList<>();

    /**
     *  处理后置拦截
     */
    private List<AopAfterInterceptor> aopAfterInterceptors = new ArrayList<>();

    /**
     *  处理异常拦截
     */
    private List<AopExceptionInterceptor> aopExceptionInterceptors = new ArrayList<>();

    /**
     *  处理环绕通知
     */
    private List<AopAroundInterceptor> aopAroundInterceptors = new ArrayList<>();

    /**
     *  被代理的类
     */
    private Object target;

    public ProxyHandler(Object target) {
        this.target = target;
    }

    public void addBeforeInterceptor(AopBeforeInterceptor aopBeforeInterceptor){
        aopBeforeInterceptors.add(aopBeforeInterceptor);
    }

    public void addAfterInterceptor(AopAfterInterceptor aopAfterInterceptor){
        aopAfterInterceptors.add(aopAfterInterceptor);
    }

    public void addExceptionInterceptor(AopExceptionInterceptor aopExceptionInterceptor){
        aopExceptionInterceptors.add(aopExceptionInterceptor);
    }

    public void addAroundInterceptor(AopAroundInterceptor aopAroundInterceptor){
        aopAroundInterceptors.add(aopAroundInterceptor);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 执行前置处理
        final boolean invokeBefore = invokeBefore(proxy, method, args);
        if (!invokeBefore) {
            log(String.format("[%s] invoke before return",method.getName()));
        }
        // 方法处理结果
        Object retVal = null;
        try {
            // 执行方法
            retVal = method.invoke(target, args);
            // 执行后置处理
            retVal = invokeAfter(proxy, method, args, retVal);
        }catch (Exception e){
            // 处理异常
            retVal = invokeException(proxy, method, args, ((InvocationTargetException) e).getTargetException());
        }
        return retVal;
    }

    private Object invokeException(Object proxy, Method method, Object[] args, Throwable e) {
        Object retVal = null;
        // 优先执行异常拦截
        for (AopExceptionInterceptor exceptionInterceptor : aopExceptionInterceptors){
            retVal = exceptionInterceptor.exception(proxy,method,args,e);
        }
        for (AopExceptionInterceptor exceptionInterceptor : aopAroundInterceptors){
            retVal = exceptionInterceptor.exception(proxy,method,args,e);
        }
        return retVal;
    }

    /**
     *  处理后置处理
     * @return 处理后的结果
     */
    private Object invokeAfter(Object proxy, Method method, Object[] args, Object retVal) {
        // 优先执行后置拦截
        for (AopAfterInterceptor aopAfterInterceptor : aopAfterInterceptors){
            retVal = aopAfterInterceptor.after(proxy, method, args, retVal);
        }
        for (AopAfterInterceptor aopAfterInterceptor : aopAroundInterceptors){
            retVal = aopAfterInterceptor.after(proxy, method, args, retVal);
        }
        return retVal;
    }

    /**
     *  执行前置处理
     * @return 终断 {@code false} , 继续 {@code true}
     */
    private boolean invokeBefore(Object proxy, Method method, Object[] args) {
        // 优先执行前置拦截
        for (AopBeforeInterceptor beforeInterceptor : aopBeforeInterceptors){
            if (!beforeInterceptor.before(proxy, method, args)) {
                return false;
            }
        }
        for (AopBeforeInterceptor beforeInterceptor : aopAroundInterceptors){
            if (!beforeInterceptor.before(proxy, method, args)) {
                return false;
            }
        }
        return true;
    }

    public static void log(String message){
        System.out.printf("[%s]-[%s] : %s\n",Thread.currentThread(), DateUtil.yyyy_MM_dd_HH_mm_ss_sss(new Date()),message);
    }
}
