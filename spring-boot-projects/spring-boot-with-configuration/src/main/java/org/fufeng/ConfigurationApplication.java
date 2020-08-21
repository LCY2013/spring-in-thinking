/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-21
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: 配置应用上下文启动类
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-21
 */
@SpringBootApplication
@ImportResource(locations = {
        "META-INF/spring/context.xml",
        "META-INF/spring/context-prod.xml",
        "META-INF/spring/context-test.xml"
})
public class ConfigurationApplication implements EnvironmentAware {

    public static void main(String[] args) {
        final SpringApplication springApplication = new SpringApplication(ConfigurationApplication.class);
        springApplication.setAdditionalProfiles("prod");
        // Spring 设置允许覆盖BeanDefinition定义
        // springApplication.setAllowBeanDefinitionOverriding(true);
        springApplication.run(args);
        // SpringApplication.run(ConfigurationApplication.class,args);
    }

    @Override
    public void setEnvironment(Environment environment) {
        // 获取当前Profiles
        Arrays.asList(environment.getActiveProfiles()).forEach(System.out::println);
        if (environment instanceof ConfigurableEnvironment){
            final ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment) environment;
            final MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            final Map<String,Object> resultProperty = new HashMap<>();
            resultProperty.put("server.port",8850);
            final PropertySource<Map<String,Object>> propertySource =
                    new MapPropertySource("application-started-change",resultProperty);
            propertySources.addFirst(propertySource);
        }

    }
}
