/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-19
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

/**
 * @program: spring-in-thinking
 * @description: web security 配置信息
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-19
 * @see WebSecurityConfigurerAdapter
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // CSRF
        http.csrf().csrfTokenRepository(new CookieCsrfTokenRepository())
                .requireCsrfProtectionMatcher(
                        request -> "GET".toLowerCase().equals(request.getMethod()));

        // CSP
        http.headers().contentSecurityPolicy("script-src http://code.jquery.com/");

        // X-Frame-Options header
        // 相同域名是允许的
        // http.headers().frameOptions().sameOrigin();

        // 实现白名单
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(
                request -> "127.0.0.1"));

        // XSS header
        http.headers().xssProtection().block(false);

        // 授权
        http.authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                .formLogin().usernameParameter("magic") // 用户名称
                .passwordParameter("123456")  // 密码
                .loginProcessingUrl("/loginAction") // 登陆Action uri
                .loginPage("/login") // 登陆页 uri
                .failureForwardUrl("/error") // 登陆失败uri
                .permitAll()
                .and().logout().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 通过内存构建一个认证信息
        auth.inMemoryAuthentication()
                .withUser("fufeng").password("123456").roles("ADMIN").and()
                .withUser("lcy").password("123456").roles("USER");
    }
}
