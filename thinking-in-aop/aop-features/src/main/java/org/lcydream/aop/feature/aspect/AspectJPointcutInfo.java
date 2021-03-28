/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-cloud-alibaba-projects
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2021-02-28
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.lcydream.aop.feature.aspect;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @program spring-cloud-alibaba-projects
 * @description AspectJ 示例
 * @create 2021-02-28
 */
@Configuration // Configuration Class
@EnableAspectJAutoProxy // 激活 AspectJ 注解自动代理
public class AspectJPointcutInfo {

    public static void main(String[] args) {
        final AnnotationConfigApplicationContext configApplicationContext =
                new AnnotationConfigApplicationContext();
        // 注册主类和切点规则
        configApplicationContext.register(AspectJPointcutInfo.class, AspectConfiguration.class);
        configApplicationContext.refresh();

        final AspectJPointcutInfo aspectJInfo = configApplicationContext.getBean(AspectJPointcutInfo.class);

        aspectJInfo.exec();

        configApplicationContext.close();
    }

    public void exec() {
        System.out.println("exec");
    }

}
