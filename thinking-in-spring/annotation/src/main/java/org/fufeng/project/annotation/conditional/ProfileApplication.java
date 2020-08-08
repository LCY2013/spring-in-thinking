/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-02
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.project.annotation.conditional;

import org.springframework.context.annotation.*;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @program: spring-in-thinking
 * @description: {@link Profile} 条件配置
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-02
 */
@Configuration
public class ProfileApplication {

    public static void main(String[] args) {
        // 定义上下文
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 注册一个主配置类
        context.register(ProfileApplication.class);

        // 获取环境抽象Environment
        final ConfigurableEnvironment environment = context.getEnvironment();
        // 设置一个默认的偶数激活Profile (默认兜底方案)
        environment.setDefaultProfiles("even");
        // org.springframework.core.env.AbstractEnvironment.doGetActiveProfiles
        // springboot 也可以使用--spring.profiles.active=odd
        // spring 也可以使用-Dspring.profiles.active=odd
        // 设置一个激活的Profile
        //environment.setActiveProfiles("odd");

        // 刷新启动容器上下文
        context.refresh();

        // 获取自定义注解的元素
        System.out.println(context.getBean("number",Integer.class));

        // 关闭应用上下文
        context.close();
    }

    // 获取基数Integer类型的值
    @Bean("number")
    //@Profile("odd")
    @Conditional(OddProfileCondition.class)
    public Integer odd(){
        return 1;
    }

    // 获取基数Integer类型的值
    @Bean("number")
    @Profile("even")
    public Integer even(){
        return 2;
    }


}
