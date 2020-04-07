package org.lcydream.configuration.properties;

import org.lcydream.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: spring-in-thinking
 * @description: 外部化配置示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 23:13
 */
@PropertySource(value = {"classpath:/META-INF/users-config.properties"})
public class PropertySourceDemo {

    /**
     *  这里的${user.name} 存在一个配置优先级问题，java properties配置中默认存在user.name,
     *  即计算机名称用户名称，所以注入的name不是配置文件中的扶风,
     *  这里设置自定义环境中的properties后，就覆盖的java properties与自定义加载的properties文件中的user.name
     *  这里添加在首位，是一个先来优先使用的策略
     * @param id ID
     * @param name 名称
     * @return 用户对象
     */
    @Bean
    private User configUser(@Value("${user.id}") Long id,
                            @Value("${user.name}") String name){
        User user = new User();
        user.setId(id);
        user.setName(name);
        return user;
    }

    public static void main(String[] args) {
        //新建一个ApplicationContext应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();

        //扩展Environment中的PropertySources
        //扩展PropertySources必须在refresh操作前
        Properties properties = new Properties();
        properties.put("user.name","fufeng");
        org.springframework.core.env.PropertySource propertySource =
                new PropertiesPropertySource("first-propertySource",properties);
        applicationContext.getEnvironment().getPropertySources().addFirst(propertySource);

        //注册当前的类作为配置class
        applicationContext.register(PropertySourceDemo.class);
        //重新刷新应用上下文，进行应用启动
        applicationContext.refresh();

        //通过依赖查找获取所有的User对象信息
        final Map<String, User> beansOfType = applicationContext.getBeansOfType(User.class);
        for (Map.Entry entry : beansOfType.entrySet()){
            System.out.printf("bean name : %s , content : %s\n",entry.getKey(),entry.getValue());
        }

        System.out.println(applicationContext.getEnvironment().getPropertySources());

        //关闭应用上下文
        applicationContext.close();
    }

}
