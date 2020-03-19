package org.lcydream.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

/**
 * 外部化配置的依赖来源,需要通过{@link org.springframework.beans.factory.annotation.Value}
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/default.properties",encoding = "UTF-8")
public class ExternalConfigurationDependencySource {

    @Value("${age:18}")
    private Integer age;

    /**
     * userName :LuoCY 而不是fufeng，因为这里优先级问题，取到的是计算机名称也就是SystemProperties中的user.name
     * 而不是default中的user.name
     */
    @Value("${user.name}")
    private String userName;

    @Value("${name}")
    private String name;

    @Value("${resource:classpath:/META-INF/default.properties}")
    private Resource resource;

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(ExternalConfigurationDependencySource.class);

        //启动Spring容器
        applicationContext.refresh();

        //获取配置Bean(当前类对象)
        ExternalConfigurationDependencySource dependencySource =
                applicationContext.getBean(ExternalConfigurationDependencySource.class);
        System.out.println("age :"+dependencySource.age);
        System.out.println("resource :"+dependencySource.resource);
        System.out.println("name :"+dependencySource.name);
        System.out.println("userName :"+dependencySource.userName);

        //关闭应用上下文
        applicationContext.close();
    }

}
