package com.lcydream;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 验证 @SpringBootApplication 也是@Component注解的派生注解
 * @SpringBootApplication
 *  -> @SpringBootConfiguration
 *      -> @Configuration
 *          -> @Component
 */
@SpringBootApplication
public class DerivedComponentHierarchyBootstrap {

    public static void main(String[] args) {
        //当前引导类
        Class<?> derivedComponentHierarchyBootstrapClass =
                DerivedComponentHierarchyBootstrap.class;
        //运行Spring boot，并返回Spring应用上下文
        ConfigurableApplicationContext applicationContext =
                new SpringApplicationBuilder(derivedComponentHierarchyBootstrapClass)
                .web(WebApplicationType.NONE) //应用诊断为非web应用
                .run();
        //输出当前引导类的实例
        System.out.println("当前引导类 Bean:"+applicationContext.getBean(derivedComponentHierarchyBootstrapClass));
        //关闭应用上下文
        applicationContext.close();
    }

}
