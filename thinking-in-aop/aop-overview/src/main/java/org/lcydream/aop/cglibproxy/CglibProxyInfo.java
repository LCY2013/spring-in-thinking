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
package org.lcydream.aop.cglibproxy;

import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;
import org.lcydream.aop.utils.DateUtil;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.util.Date;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description Cglib 动态代理示例
 * @create 2021-02-28
 */
public class CglibProxyInfo {

    public static void main(String[] args) {
        // 定义Spring内联的Cglib相关数据
        Enhancer enhancer = new Enhancer();
        // 设置父类信息
        enhancer.setSuperclass(DefaultEchoService.class);
        // 设置接口相关信息
        enhancer.setInterfaces(new Class[]{EchoService.class});
        // 设置方法回调
        enhancer.setCallback((MethodInterceptor)(source, method, methodArgs, methodProxy)->{
            log(source.getClass());
            // source 是cglib代理后的子类，如下就是错误用法，会形成一个无限递归
            // final Object invokeSuper = method.invoke(source,methodArgs);
            // 正确使用如下
            final Object invokeSuper = methodProxy.invokeSuper(source, methodArgs);
            log(source.getClass());
            return invokeSuper;
        });

        final EchoService echoService = (EchoService)enhancer.create();
        echoService.echo("hello cglib");
    }

    /**
     *  打印执行方法的名称
     * @param methodClass 方法
     */
    private static void log(Class<?> methodClass){
        System.out.printf("[%s] star [%s]\n", DateUtil.yyyy_MM_dd_HH_mm_ss_sss(new Date()),methodClass.getName());
    }

}
