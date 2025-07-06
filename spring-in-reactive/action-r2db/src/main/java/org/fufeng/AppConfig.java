package org.fufeng;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
@ComponentScan(basePackages = {"com.com.org.fufeng.security", "com.com.org.fufeng.reactive", "com.com.org.fufeng.jpa", })
public class AppConfig {
//    @Bean
//    public ThymeleafViewResolver thymeleafViewResolver(ISpringTemplateEngine engine) {
//        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
//        resolver.setTemplateEngine(engine);
//        resolver.setCharacterEncoding("UTF-8");
//        return resolver;
//    }
}
