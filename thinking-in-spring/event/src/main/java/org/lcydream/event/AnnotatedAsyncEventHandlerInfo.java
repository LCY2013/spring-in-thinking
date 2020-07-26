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
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * @program: spring-in-thinking
 * @description: {@link Async} 通过注解实现事件异步接收
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-26
 * @see Async
 * @see EnableAsync
 * @see EventListener
 */
@EnableAsync  // 开启Spring异步特性
public class AnnotatedAsyncEventHandlerInfo {

    public static void main(String[] args) {
        // 创建一个Spring上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        // 注册主配置类
        applicationContext.register(AnnotatedAsyncEventHandlerInfo.class);

        // 启动上下文
        applicationContext.refresh();

        // 发送自定义事件
        applicationContext.publishEvent(new CustomerEvent("magic"));

        // 关闭上下文
        applicationContext.close();
    }

    @Async
    @EventListener
    public void recoverEvent(CustomerEvent customerEvent){
        System.out.printf("Thread[%s],recover event[%s]\n",
                Thread.currentThread().getName(),customerEvent);
    }

    /**
     *  没有这个方法时:
     *      Thread[SimpleAsyncTaskExecutor-1],recover event[org.lcydream.event.customer.CustomerEvent[source=magic]]
     *  有这个方法时:
     *      Thread[task-events-executor-1],recover event[org.lcydream.event.customer.CustomerEvent[source=magic]]
     * @return 并发执行线程池
     */
    @Bean
    public ExecutorService taskExecutor(){
        return newFixedThreadPool(1,
                new CustomizableThreadFactory("task-events-executor-"));
    }
}
