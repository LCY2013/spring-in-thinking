/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-15
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

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @program: spring-in-thinking
 * @description: {@link ApplicationListener} Spring 接口事件
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-15
 * @see EventListener
 * @see ApplicationListener
 */
@EnableAsync
public class ApplicationListenerInfo {

    public static void main(String[] args) {
        // 创建一个Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();

        //注册组件
        applicationContext.register(ApplicationListenerInfo.class);

        //方式一: 添加一个Spring 监听器，基于接口实现
        applicationContext.addApplicationListener((event) ->
                print("Spring Event :" + event));

        //方式二: 基于@org.springframework.context.event.EventListener 注解

        // 刷新应用上下文
        applicationContext.refresh();
        // 调用启动应用上下文 -> 其实就是一个Spring事件的发布
        applicationContext.start();
        //关闭spring应用上下文
        applicationContext.close();
    }

    // 监听 Spring 所有的事件
//    @EventListener
//    public void onListener(ApplicationEvent applicationEvent){
//        System.out.println("@EventListener :"+applicationEvent);
//    }

    @EventListener
    @Order(1)
    public void onListener(ContextRefreshedEvent contextRefreshedEvent){
        print("@EventListener ContextRefreshedEvent - 1");
    }

    @EventListener
    @Async
    //@Order(4) 异步情况下@Order不生效
    public void onListenerAsync(ContextRefreshedEvent contextRefreshedEvent){
        print("@EventListener ContextRefreshedEvent Async - 4");
    }

    @EventListener
    @Order(2)
    public void onListener(ContextStartedEvent contextStartedEvent){
        print("@EventListener ContextStartedEvent - 2");
    }

    @EventListener
    @Order(3)
    public void onListener(ContextClosedEvent contextClosedEvent){
        print("@EventListener ContextClosedEvent - 3");
    }

    // 打印工具类
    private static void print(Object obj){
        System.out.printf("[Thread - %s] - %s\n",Thread.currentThread().getName(),obj);
    }

}
