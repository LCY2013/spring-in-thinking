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

import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description aop 拦截器相关
 * @create 2021-02-27
 */
public class AopInterceptorInfo {

    public static void main(String[] args) {
        // 获取当前线程中的类加载
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        final ProxyHandler proxyHandler = new ProxyHandler(new DefaultEchoService());
        proxyHandler.addBeforeInterceptor((proxy, method, beforeArgs) -> {
            if (EchoService.class.isAssignableFrom(method.getDeclaringClass())) {
                ProxyHandler.log("before interceptor");
                return true;
            }
            return false;
        });

        proxyHandler.addAfterInterceptor((proxy, method, afterArgs, returnResult) -> {
            ProxyHandler.log("after interceptor " + returnResult);
            return returnResult;
        });

        proxyHandler.addExceptionInterceptor((proxy, method, exceptionArgs, throwable) -> {
            ProxyHandler.log("exception interceptor " + throwable);
            return null;
        });
        final Object instance = Proxy.newProxyInstance(contextClassLoader,
                new Class[]{EchoService.class},
                proxyHandler);

        final EchoService echoService = (EchoService) instance;
        echoService.print("jdk proxy interceptor");
    }

}
