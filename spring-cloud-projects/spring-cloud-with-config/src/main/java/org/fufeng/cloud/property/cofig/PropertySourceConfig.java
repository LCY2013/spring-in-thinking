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
package org.fufeng.cloud.property.cofig;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: thinking-in-spring-boot
 * @description: Bootstrap 配置Bean
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-24
 */
@Configuration
public class PropertySourceConfig implements
        ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        // 从ApplicationContext中获取Environment
        final ConfigurableEnvironment environment = applicationContext.getEnvironment();
        // 通过Environment获取多值PropertySource
        final MutablePropertySources propertySources = environment.getPropertySources();
        // 设置自定义的PropertySource在第一位
        propertySources.addFirst(createPropertySource());

    }

    // 这里优先级大于 PropertySourceConfig > CustomPropertySourceLocator
    private PropertySource<Map<String,Object>> createPropertySource(){
        final Map<String,Object> source = new HashMap<>();
        source.put("spring.application.name","fufeng");
        return new MapPropertySource("fufeng",source);
    }

}
