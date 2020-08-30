/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-30
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.context.lifucycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.LiveBeansView;

import java.io.IOException;

import static org.springframework.context.support.LiveBeansView.MBEAN_DOMAIN_PROPERTY_NAME;

/**
 * @program: thinking-in-spring-boot
 * @description: {@link LiveBeansView} 示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-30
 * @see LiveBeansView
 * @since spring3.2
 */
public class LiveBeansViewInfo {

    public static void main(String[] args) throws IOException {
        System.setProperty(MBEAN_DOMAIN_PROPERTY_NAME,"spring-liveBeansView");
        // 创建上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(LiveBeansViewInfo.class);

        // 休眠通过jconsole查看jmx MBean信息
        System.in.read();

        // 关闭应用上下文
        context.close();
    }

    //[ { "context": "org.springframework.context.annotation.AnnotationConfigApplicationContext@31dc339b", "parent": null, "beans": [ { "bean": "liveBeansViewInfo", "aliases": [], "scope": "singleton", "type": "org.fufeng.context.lifucycle.LiveBeansViewInfo", "resource": "null", "dependencies": [] }] }]

}
