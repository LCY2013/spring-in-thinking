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
package org.fufeng.cloud.property.locator;

import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: thinking-in-spring-boot
 * @description: 自定义属性源定义器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-24
 */
@Configuration
public class CustomPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {
        /*return new MapPropertySource("override spring application name",
                Collections.singletonMap("spring.application.name", "worked as intended"));*/
        if (environment instanceof ConfigurableEnvironment){
            // 从ApplicationContext中获取Environment
            ConfigurableEnvironment configurableEnvironment = (ConfigurableEnvironment)environment;
            // 通过Environment获取多值PropertySource
            final MutablePropertySources propertySources = configurableEnvironment.getPropertySources();
            // 设置自定义的PropertySource在第一位
            propertySources.addFirst(createPropertySource());
        }
        return null;
    }

    private PropertySource<Map<String,Object>> createPropertySource(){
        final Map<String,Object> source = new HashMap<>();
        source.put("spring.application.name","lcy");
        return new MapPropertySource("lcy",source);
    }
}
