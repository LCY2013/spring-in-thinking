package org.lcydream.configuration.yaml;

import org.lcydream.configuration.annotation.AnnotationIOCConfigurationMetadataDemo;
import org.lcydream.domain.User;
import org.lcydream.enums.City;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: 基于Java注解的YAML 外部化资源示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 23:44
 */
@PropertySource(
        name="yamlUserResource",
        value = "classpath:/META-INF/user.yaml",
        factory = YamlPropertiesSourceFactory.class
)
public class YamlResourceAnnotatedDemo {

    /**
     *  这里的${user.name} 存在一个配置优先级问题，java properties配置中默认存在user.name,
     *  即计算机名称用户名称，所以注入的name不是配置文件中的扶风
     * @param id ID
     * @param name 名称
     * @return 用户对象
     */
    @Bean
    private User configUser(@Value("${user.id}") Long id,
                            @Value("${user.name}") String name,
                            @Value("${user.city}")City city){
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setCity(city);
        return user;
    }

    public static void main(String[] args) {
        //新建一个ApplicationContext应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册当前的类作为配置class
        applicationContext.register(YamlResourceAnnotatedDemo.class);
        //重新刷新应用上下文，进行应用启动
        applicationContext.refresh();

        //通过依赖查找YamlMapFactoryBean
        final User user = applicationContext.getBean("configUser", User.class);
        System.out.println(user);

        //关闭应用上下文
        applicationContext.close();
    }

}
