/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-07-21
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

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @program: spring-in-thinking
 * @description: 事件在层次性上下文中问题
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-21
 */
public class HierarchicalApplicationEventInfo {

    public static void main(String[] args) {
        // 创建一个父上下文
        AnnotationConfigApplicationContext parentContext = new AnnotationConfigApplicationContext();
        // 设置父上下文的ID
        parentContext.setId("parentContext");
        // 设置父上下文事件监听器
        parentContext.addApplicationListener(new CustomEventListener());

        // 创建一个子上下文
        AnnotationConfigApplicationContext childrenContext = new AnnotationConfigApplicationContext();
        // 设置子上下文ID
        childrenContext.setId("childrenContext");
        // 设置上下文关系
        childrenContext.setParent(parentContext);
        // 设置子上下文事件监听器
        childrenContext.addApplicationListener(new CustomEventListener());

        // 启动父子容器
        parentContext.refresh();
        childrenContext.refresh();

        // 关闭父子容器
        childrenContext.close();
        parentContext.close();
    }

    private static class CustomEventListener implements ApplicationListener<ApplicationContextEvent>{

        // 去重
        private static final Set<ApplicationContextEvent> events = new LinkedHashSet<>();

        @Override
        public void onApplicationEvent(ApplicationContextEvent event) {
            // 通过Set去掉以及触发过的事件
            if(events.add(event)) {
                System.out.printf("ApplicationContext ID[%s], Event type[%s]\n",
                        event.getApplicationContext().getId(), event.getClass().getSimpleName());
            }
        }
    }
}
