/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-10
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.env;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @program: spring-in-thinking
 * @description: 依赖注入Environment对象
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-10
 * @see Environment
 */
public class InjectionEnvironmentInfo implements EnvironmentAware, ApplicationContextAware {

    // 通过接口回调注入应用上下文
    private ApplicationContext applicationContext;

    // 通过接口回调注入环境变量
    private Environment environment;

    // 通过Autowired 注入context
    @Autowired
    private ApplicationContext context;

    // 通过Autowired 注入environment
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        // 新建应用上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(InjectionEnvironmentInfo.class);

        // 通过应用上下文获取bean
        final InjectionEnvironmentInfo contextBean =
                context.getBean(InjectionEnvironmentInfo.class);
        System.out.println("Environment = "+contextBean.environment);
        System.out.println(contextBean.environment == contextBean.env);
        System.out.println(contextBean.environment == contextBean.applicationContext.getEnvironment());
        System.out.println();
        System.out.println(contextBean.applicationContext == contextBean.context);
        System.out.println(contextBean.context == context);

        // 关闭应用上下文
        context.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
