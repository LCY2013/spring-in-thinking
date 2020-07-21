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
package org.lcydream.event.customer;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @program: spring-in-thinking
 * @description: {@link ApplicationEvent} 自定义事件
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-07-21
 */
public class CustomerEventInfo {

    public static void main(String[] args) {
        // 1、创建一个应用上下文
        GenericApplicationContext applicationContext = new GenericApplicationContext();
        // 2、注册一个ApplicationListener
        applicationContext.addApplicationListener(new CustomerEventListener());

        // 3、启动上下文
        applicationContext.refresh();

        // 4、发送事件
        applicationContext.publishEvent(new CustomerEvent("fufeng"));

        // 5、关闭应用上下文
        applicationContext.close();
    }

}
