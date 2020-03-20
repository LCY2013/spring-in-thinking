package org.lcydream.beans.scope.config;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import org.lcydream.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.context.request.AbstractRequestAttributesScope;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring mvc 配置类
 */
@EnableWebMvc
@Configuration
public class WebConfig {

    /**
     * 定义User作用域范围为Request,每次Http请求都会返回一个新的User
     * @see {@link RequestScope}
     * @see {@link Scope (WebApplicationContext#SCOPE_REQUEST)}
     *
     * 定义User作用域范围为Session,每次不同Session请求都会返回一个新的User
     * @see {@link SessionScope}
     * @see {@link Scope (WebApplicationContext#SCOPE_SESSION)}
     *
     * 定义User作用域范围为Application,每次不同Apllication都会返回一个新的User
     * @see {@link ApplicationScope}
     * @see {@link Scope (WebApplicationContext#SCOPE_APPLICATION)}
     *
     * @see AbstractRequestAttributesScope
     *
     * @return 返回一个新的User
     */
    @Bean
    //@RequestScope
    //@SessionScope
    @ApplicationScope
    public User createUser(){
        User user = new User();
        user.setId(System.nanoTime());
        user.setName("罗");
        return user;
    }
}
