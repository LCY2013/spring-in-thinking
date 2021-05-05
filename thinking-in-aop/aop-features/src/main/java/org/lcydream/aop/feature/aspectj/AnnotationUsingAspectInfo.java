/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-02-28
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.aop.feature.aspectj;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.lcydream.aop.feature.aspectj.config.AspectConfiguration;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.aop.framework.adapter.AfterReturningAdviceInterceptor;
import org.springframework.aop.framework.adapter.MethodBeforeAdviceInterceptor;
import org.springframework.aop.framework.adapter.ThrowsAdviceInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 编程方式实现Aspect
 * @create 2021-02-28
 * @since 1.0
 */
public class AnnotationUsingAspectInfo {

    public static void main(String[] args) {
        // 创建一个HashMap缓存，作为被代理的对象
        final Map<String, Object> cache = new HashMap<>();
        // 创建Proxy工程(AspectJ)
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(cache);
        // 增加Aspect配置类到代理工厂
        proxyFactory.addAspect(AspectConfiguration.class);

        // 开启暴露代理对象到上下文
        proxyFactory.setExposeProxy(true);

        /**
         * @see MethodBeforeAdviceInterceptor
         * @see MethodInterceptor
         * @see MethodBeforeAdvice -> {@link AspectJMethodBeforeAdvice}
         * @see BeforeAdvice
         * @see Advice
         * @see ReflectiveAspectJAdvisorFactory#getAdvice(java.lang.reflect.Method, org.springframework.aop.aspectj.AspectJExpressionPointcut, org.springframework.aop.aspectj.annotation.MetadataAwareAspectInstanceFactory, int, java.lang.String)
         */
        // 添加前置通知行为
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, methodArgs, targetObject) -> {
            // 获取上下文中的代理对象,如果启动的时候没有开启@EnableAspectJAutoProxy,这里会为null
            final Object proxy = AopContext.currentProxy();
            if ("put".equals(method.getName()) && methodArgs.length == 2) {
                System.out.printf("(BeforeAdvice) put key [%s] , value [%s] , proxy [%s]\n",
                        methodArgs[0],
                        methodArgs[1],
                        proxy);
            }
        });

        /**
         * @see AfterAdvice
         * @see AfterReturningAdvice -> {@link AfterReturningAdviceInterceptor}
         * @see ThrowsAdvice - > {@link ThrowsAdviceInterceptor}
         */
        // 添加后缀通知行为
        proxyFactory.addAdvice((AfterReturningAdvice)(returnValue,method, methodArgs, targetObject) -> {
            if ("put".equals(method.getName()) && methodArgs.length == 2) {
                System.out.printf("(AfterReturningAdvice) put key [%s] , new value [%s] , old value [%s]\n",
                        methodArgs[0],
                        methodArgs[1],
                        returnValue);
            }
        });

        // 这里通过目标对象直接调用代理动作不会生效
        //cache.put("name", "fufeng");
        //System.out.println(cache.get("name"));

        // 需要通过代理对象去执行该操作
        // 获取代理对象
        final Map<String,Object> proxyMap = proxyFactory.getProxy();
        // 插入数据
        proxyMap.put("name","fufeng");
        System.out.println(proxyMap.get("name"));

        proxyMap.put("name","magic");
        System.out.println(proxyMap.get("name"));
    }

}
