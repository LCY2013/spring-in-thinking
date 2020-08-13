/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-13
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: {@link PropertySources} 切换环境中PropertySource中顺序
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-13
 */
public class EnvironmentPropertySourceChangeInfo {

    /*
        PropertySource("first-map") {"user.name":"fufeng"}
        PropertySource("java os environment") {"user.name":"userName"}
     */
    @Value("${user.name}")
    private String userName;

    public static void main(String[] args) {
        // 创建应用上下文
        final AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 注册主配置类信息
        context.register(EnvironmentPropertySourceChangeInfo.class);

        // 启动前修改
        // 获取环境变量
        final ConfigurableEnvironment environment = context.getEnvironment();
        // 创建一个map的值信息
        Map<String,Object> map = new HashMap<>();
        map.put("user.name","fufeng");
        // 获取
        final MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new MapPropertySource("first-map",map));

        // 启动容器
        context.refresh();

        // 启动后修改
        map.put("user.name","magic");
        /*
        fufeng

        ProperSource[first-map] - (user.name)[magic]
        ProperSource[systemProperties] - (user.name)[LuoCY]
        ProperSource[systemEnvironment] - (user.name)[null]

        说明这里的值被修改了，但是在容器启动后以及初始化的字段变量没有任何改变，说明不会修改以及生效的属性字段
        但是apollo可以做到这点，可以通过过滤@value注解，保留它的BeanDefinition的字段Field注入
         */

        final EnvironmentPropertySourceChangeInfo info =
                context.getBean(EnvironmentPropertySourceChangeInfo.class);
        System.out.println(info.userName);
        System.out.println();
        /*
        ProperSource[first-map] - (user.name)[fufeng]
        ProperSource[systemProperties] - (user.name)[LuoCY]
        ProperSource[systemEnvironment] - (user.name)[null]
         */
        for (PropertySource<?> ps : propertySources){
            System.out.printf("ProperSource[%s] - (user.name)[%s]\n",ps.getName(),ps.getProperty("user.name"));
        }

        // 关闭容器
        context.close();
    }

}
