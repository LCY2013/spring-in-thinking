/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2020 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-24
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.cloud.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @program: thinking-in-spring-boot
 * @description: spring event / listener 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-24
 */
public class SpringEventListenerInfo {

    public static void main(String[] args) {
        // 新建一个应用上下文文
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 注册驱动主类
        context.register(SpringEventListenerInfo.class);

        // 第一种方式 注册监听器
        // context.register(CustomApplicationListener.class);

        // 第二种方式 注册监听器
        context.addApplicationListener(new CustomApplicationListener());

        // 启动容器信息
        context.refresh();

        // 发布事件
        context.publishEvent(new CustomApplicationEvent("hello fufeng"));
        context.publishEvent(new CustomApplicationEvent(1));
        context.publishEvent(new CustomApplicationEvent(1D));

        // 关闭容器
        context.close();
    }

    /**
     * static 提升字节码加载顺序
     */
    static class CustomApplicationEvent extends ApplicationEvent {
        public CustomApplicationEvent(Object source) {
            super(source);
        }
    }

    /**
     * 自定义监听自己自定义事件的监听器
     */
    static class CustomApplicationListener implements ApplicationListener<CustomApplicationEvent> {

        public void onApplicationEvent(CustomApplicationEvent event) {
            System.out.printf("Thread-[%s], listed event -> %s\n",Thread.currentThread().getName(),event);
        }

    }
}
