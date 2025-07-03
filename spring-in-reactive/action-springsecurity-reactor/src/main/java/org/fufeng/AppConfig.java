package org.fufeng;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@EnableAsync
@ComponentScan(basePackages = {"org.fufeng.security", "org.fufeng.async", "org.fufeng.jpa", })
public class AppConfig {
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver(ISpringTemplateEngine engine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(engine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }
}
