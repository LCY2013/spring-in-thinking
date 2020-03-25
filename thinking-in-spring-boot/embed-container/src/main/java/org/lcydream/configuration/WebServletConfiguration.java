package org.lcydream.configuration;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.servlet.function.ServerResponse.ok;

/**
 *  配置类{@link Configuration}
 *  @SpringBootApplication 说明不是一个限定标注于启动类上
 *  @SpringBootApplication 等同于@EnableAutoConfiguration+@ComponentScan+@Configuration
 *  @EnableAutoConfiguration 单独的该注解也能激活配置的Bean元素
 *
 *  @Configuration 标注的Bean是一个完全模式(Full)，也就是被CGLIB代理过
 *  而@Component 标注的Bean是一个轻量模式(lite)，也就是普通Spring Bean
 */
@Configuration
//@SpringBootApplication
//@EnableAutoConfiguration
public class WebServletConfiguration {

    /**
     *  这里通过注解注入一个{@link WebServerApplicationContext} 去获取WebServer类型
     *  Spring boot 应用启动后回调
     * @param context {@link WebServerApplicationContext}
     * @return {@link ApplicationRunner}
     */
    @Bean
    public ApplicationRunner runner(WebServerApplicationContext context){
        return args ->
                System.out.println("当前WebServer 实现类:"
                        +context.getWebServer().getClass().getName())
                ;
    }

    /**
     *  这里通过Spring 事件回调通知
     * @param event {@link WebServerInitializedEvent}
     */
    @EventListener(WebServerInitializedEvent.class)
    public void onWebServerReady(WebServerInitializedEvent event){
        System.out.println("当前事件监听的WebServer事件:"
                +event.getWebServer().getClass().getName());
    }

    @Bean
    public RouterFunction<ServerResponse> webServletReactive(){
        return RouterFunctions.route(RequestPredicates.GET("/web/servlet/reactive"), serverRequest ->
                ok().body(Mono.just("web servlet reactive"))
        );
    }

    @Bean
    public ApplicationRunner beanFactoryRunner(BeanFactory beanFactory){
        return args -> {
            System.out.println("当前 webServletReactive Bean 实现类: "
                + beanFactory.getBean("webServletReactive").getClass().getName());
            System.out.println("当前 WebServletConfiguration Bean 实现类: "
                    + beanFactory.getBean(WebServletConfiguration.class).getClass().getName());
        };
    }
}
