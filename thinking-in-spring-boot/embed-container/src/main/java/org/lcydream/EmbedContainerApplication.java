package org.lcydream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

@SpringBootApplication //等同于@EnableAutoConfiguration+@ComponentScan+@Configuration
public class EmbedContainerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbedContainerApplication.class);
    }

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

}
