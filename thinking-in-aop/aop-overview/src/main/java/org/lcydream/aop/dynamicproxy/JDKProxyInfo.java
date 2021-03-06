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
package org.lcydream.aop.dynamicproxy;

import org.lcydream.aop.staticproxy.DefaultEchoService;
import org.lcydream.aop.staticproxy.EchoService;

import java.lang.reflect.Proxy;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description jdk 动态代理
 * @create 2021-02-27
 */
public class JDKProxyInfo {

    public static void main(String[] args) {
        // 获取当前线程中的类加载
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        // instance 是生成的字节码的实例化 生成字节码如下：
        // class Proxy$num extend java.lang.reflect.Proxy implement EchoService{
        //      public Proxy$num(InvocationHandler handler){
        //          super(handler);
        //      }
        // }
        final Object instance = Proxy.newProxyInstance(contextClassLoader,
                new Class[]{EchoService.class},
                (proxy, method, methodArgs) -> {
                    // 先判断method方法是否来之代理的接口,判断EchoService.class是否是方法的后面类的同类或者父类
                    if (EchoService.class.isAssignableFrom(method.getDeclaringClass())){
                        final EchoService echoService = new DefaultEchoService();
                        if (method.getReturnType().isAssignableFrom(void.class)) {
                            echoService.echo(String.valueOf(methodArgs[0]));
                        }else {
                            //return echoService.echo(String.valueOf(methodArgs[0]));
                        }
                    }
                    return null;
                });

        final EchoService echoService = (EchoService) instance;
        echoService.echo("jdk proxy ");
    }

}
