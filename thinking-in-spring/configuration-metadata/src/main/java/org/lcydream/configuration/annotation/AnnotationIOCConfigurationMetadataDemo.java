package org.lcydream.configuration.annotation;

import org.lcydream.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: 基于注解的IOC配置元信息示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 20:28
 */
//将当前类作为Configuration class
@ImportResource(value = {"classpath:/META-INF/dependency-lookup-context.xml"})
@Import(value = {User.class})
@PropertySource(value = {"classpath:/META-INF/users-config.properties"})  //java8+ 支持@Repeatable,注解可重复标注于同一个类对象
@PropertySource(value = {"classpath:/META-INF/users-config.properties"})
//@PropertySources(value = {
//        @PropertySource(value = {"classpath:/META-INF/users-config.properties"}),
//        @PropertySource(value = {"classpath:/META-INF/users-config.properties"})
//})
public class AnnotationIOCConfigurationMetadataDemo {

    @Value("${user.id}")
    Long id;

    /**
     *  这里的${user.name} 存在一个配置优先级问题，java properties配置中默认存在user.name,
     *  即计算机名称用户名称，所以注入的name不是配置文件中的扶风
     * @param id ID
     * @param name 名称
     * @return 用户对象
     */
    @Bean
    private User configUser(@Value("${user.id}") Long id,
                                @Value("${user.name}") String name){
        User user = new User();
        user.setId(id);
        user.setName(name+"3");
        return user;
    }

    public static void main(String[] args) {
        //新建一个ApplicationContext应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册当前的类作为配置class
        applicationContext.register(AnnotationIOCConfigurationMetadataDemo.class);
        //重新刷新应用上下文，进行应用启动
        applicationContext.refresh();

        //通过依赖查找获取所有的User对象信息
        final Map<String, User> beansOfType = applicationContext.getBeansOfType(User.class);
        for (Map.Entry entry : beansOfType.entrySet()){
            System.out.printf("bean name : %s , content : %s\n",entry.getKey(),entry.getValue());
        }
        System.out.println(applicationContext.getBean(AnnotationIOCConfigurationMetadataDemo.class).id);

        //关闭应用上下文
        applicationContext.close();
    }

}
