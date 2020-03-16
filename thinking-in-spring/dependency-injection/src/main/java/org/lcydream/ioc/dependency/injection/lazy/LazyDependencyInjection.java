package org.lcydream.ioc.dependency.injection.lazy;

import org.lcydream.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collection;

/**
 * 延迟注入
 *   {@link org.springframework.beans.factory.ObjectProvider}
 *   {@link org.springframework.beans.factory.ObjectFactory}
 */
public class LazyDependencyInjection {

    @Autowired
    private User user; //实时注入

    @Autowired
    private ObjectProvider<User> objectProvider; //延时注入

    @Autowired
    private ObjectFactory<Collection<User>> objectFactory; //延时注入

    @Autowired
    private ObjectFactory<User> userObjectFactory; //延时注入

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(LazyDependencyInjection.class);
        //创建BeanDefinition构造器
        XmlBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(applicationContext);
        //定义xml资源文件路径
        String xmlPath = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(xmlPath);
        //启动Spring容器
        applicationContext.refresh();

        LazyDependencyInjection lazyDependencyInjection =
                applicationContext.getBean(LazyDependencyInjection.class);

        //期望 super user
        System.out.println("user :"+lazyDependencyInjection.user);
        System.out.println("-------------------------------");
        //期望 super user
        System.out.println("objectProvider :"+lazyDependencyInjection.objectProvider.getObject());
        System.out.println("-------------------------------");
        //期望 super user , user
        lazyDependencyInjection.objectProvider.stream().forEach(System.out::println);
        System.out.println("-------------------------------");
        //期望 super user , user
        lazyDependencyInjection.objectFactory.getObject().stream().forEach(System.out::println);
        System.out.println("-------------------------------");
        //期望 super user
        System.out.println(lazyDependencyInjection.userObjectFactory.getObject());

        //关闭应用上下文
        applicationContext.close();
    }

}
