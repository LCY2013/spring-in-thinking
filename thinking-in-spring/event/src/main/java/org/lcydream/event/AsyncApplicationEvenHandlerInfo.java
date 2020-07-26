/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-26
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

import org.lcydream.event.customer.CustomerEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;
import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

/**
 * @program: spring-in-thinking
 * @description: {@link ApplicationEventMulticaster} spring事件广播器，也是最终的事件发布器
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-26
 * @see SimpleApplicationEventMulticaster
 */
public class AsyncApplicationEvenHandlerInfo {

    public static void main(String[] args) {
        // 创建一个AnnotationApplicationContext
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //applicationContext.addApplicationListener(new CustomerEventListener());
        applicationContext.addApplicationListener((ApplicationListener<CustomerEvent>) event -> {
            System.out.printf("线程[%s]监听到事件[%s]\n",
                    Thread.currentThread().getName(),event);
            throw new RuntimeException("模拟异常");
        });
        // 启动应用上下文
        applicationContext.refresh();

        // 获取最终的发布器 SimpleApplicationEventMulticaster
        final ApplicationEventMulticaster eventMulticaster =
                applicationContext.getBean(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, ApplicationEventMulticaster.class);
        // 判断发布器是不是 SimpleApplicationEventMulticaster
        if (eventMulticaster instanceof SimpleApplicationEventMulticaster) {
            // 强转实现
            SimpleApplicationEventMulticaster multicaster = (SimpleApplicationEventMulticaster) eventMulticaster;
            // 定义线程池 TaskExecutor
            final ExecutorService executorService = newFixedThreadPool(1,
                    new CustomizableThreadFactory("task-events-executor-"));
            // 设置事件执行的线程池
            multicaster.setTaskExecutor(executorService);
            // 设置事件异常处理器
            multicaster.setErrorHandler(e ->
                    System.out.println("spring 事件异常信息:" + e.getMessage())
            );

            // 监听上下文关闭事件
            multicaster.addApplicationListener((ApplicationListener<ContextClosedEvent>) event -> {
                // 防止事件多重传播
                if (!executorService.isShutdown()) {
                    executorService.shutdown();
                }
            });
        }

        // 注册一个事件监听器
        //applicationContext.addApplicationListener(new CustomerEventListener());
        /*applicationContext.addApplicationListener(event -> {
            System.out.printf("线程[%s]监听到事件[%s]\n",
                    Thread.currentThread().getName(),event);
            throw new RuntimeException("模拟异常");
        });*/
        /*applicationContext.addApplicationListener(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                System.out.printf("线程[%s]监听到事件[%s]\n",
                        Thread.currentThread().getName(),event);
                throw new RuntimeException("模拟异常");
            }
        });*/

        // 发布事件
        applicationContext.publishEvent(new CustomerEvent("magic"));

        // 关闭应用上下文
        applicationContext.close();
    }

}
