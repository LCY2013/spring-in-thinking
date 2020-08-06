/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: spring-in-thinking
 * @Author : <a href="https://github.com/lcy2013">MagicLuo</a>
 * @date : 2020-08-06
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.boot.custom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: spring-in-thinking
 * @description: 自定义web容器
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-06
 */
@SpringBootApplication
public class CustomWebContainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomWebContainerApplication.class);
    }

    @Controller
    public static class WebController{

        @RequestMapping("/message")
        @ResponseBody
        public String message(){
            return "hello,fufeng!";
        }

        @RequestMapping("/errors")
        @ResponseBody
        public String errot(){
            return "error!";
        }

    }

    @Bean
    public static ConfigurableServletWebServerFactory configurableServletWebServerFactory(){
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.setPort(9000);
        //factory.setSessionTimeout(10, TimeUnit.MINUTES);
        factory.addContextCustomizers(context -> context.setPath("/custom"));
        /*factory.addConnectorCustomizers(connector -> {
            connector.setPort(8848);
            connector.();
        });*/
        // default 实现
        factory.setProtocol("HTTP/1.1");
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
        return factory;
    }

    /*@Component
    public class CustomizationBean implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

        @Override
        public void customize(ConfigurableServletWebServerFactory server) {
            server.setPort(9000);
        }

    }*/

}
