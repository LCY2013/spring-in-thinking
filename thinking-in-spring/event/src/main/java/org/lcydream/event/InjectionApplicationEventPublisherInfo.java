/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-22
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.event;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * @program: spring-in-thinking
 * @description: {@link ApplicationEventPublisher} spring事件发布器注入顺序示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-22
 * @see ApplicationEventPublisher
 * @see ApplicationEvent
 */
public class InjectionApplicationEventPublisherInfo
        implements ApplicationEventPublisherAware, ApplicationContextAware {

    // 注册顺序 1或者2 取决与JDK字段读取顺序
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    // 注册顺序 1或者2 取决于JDK字段读取顺序
    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        // 3、输出顺序
        applicationEventPublisher.publishEvent(new ApplicationEvent("3 -> ApplicationEventPublisher") {});
        // 4、输出顺序
        applicationContext.publishEvent(new ApplicationEvent("4 -> ApplicationContext") {});
    }

    public static void main(String[] args) {
        // 构建Spring应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        // 注册启动主配置类
        applicationContext.register(InjectionApplicationEventPublisherInfo.class);
        // 注册一个事件监听器
        applicationContext.addApplicationListener(System.out::println);
        // 启动Spring上下文
        applicationContext.refresh();

        // 关闭Spring应用上下文
        applicationContext.close();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // 2、输出顺序
        // 注册顺序 4   具体见org.springframework.context.support.ApplicationContextAwareProcessor.invokeAwareInterfaces
        applicationContext.publishEvent(new ApplicationEvent("2 -> ApplicationContext") {});
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        // 1、输出顺序
        // 注册顺序 3   具体见org.springframework.context.support.ApplicationContextAwareProcessor.invokeAwareInterfaces
        applicationEventPublisher.publishEvent(new ApplicationEvent("1 -> ApplicationEventPublisher") {});
    }

    /*
    结果如下:
        org.lcydream.event.InjectionApplicationEventPublisherInfo$4[source=1 -> ApplicationEventPublisher]
        org.lcydream.event.InjectionApplicationEventPublisherInfo$3[source=2 -> ApplicationContext]
        org.lcydream.event.InjectionApplicationEventPublisherInfo$1[source=3 -> ApplicationEventPublisher]
        org.lcydream.event.InjectionApplicationEventPublisherInfo$2[source=4 -> ApplicationContext]
        ...
     */
}
