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
package org.lcydream.aop.filter;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description 基于Spring AOP的判断模式
 * @create 2021-02-27
 */
public class TargetFilterInfo {

    public static void main(String[] args) throws ClassNotFoundException {
        // 定义某一个全限定类名称
        String className = "org.lcydream.aop.staticproxy.EchoService";
        // 获取类加载器
        final ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        // 加载上述的类
        final Class<?> targetClass = contextClassLoader.loadClass(className);
        // 通过Spring工具类查询对应方法
        final Method methodEcho = ReflectionUtils.findMethod(targetClass, "echo", String.class);
        // 输出对应方法信息
        System.out.println(methodEcho);

        // 过滤指定方法
        ReflectionUtils.doWithMethods(targetClass,
                (method) -> System.out.printf("筛选出参数只有一个且为String类型，异常只有一个且为NullPointerException的方法是: %s\n",method),
                (method) -> {
                    // 获取该方法的所有参数列表信息
                    final Class<?>[] parameterTypes = method.getParameterTypes();
                    // 获取该方法的所有异常信息
                    final Class<?>[] exceptionTypes = method.getExceptionTypes();
                    // 筛选出参数只有一个且为String类型，异常只有一个且为NullPointerException
                    return parameterTypes.length == 1
                            && parameterTypes[0].isAssignableFrom(String.class)
                            && exceptionTypes.length == 1
                            && exceptionTypes[0].isAssignableFrom(NullPointerException.class);
                });
    }

}
