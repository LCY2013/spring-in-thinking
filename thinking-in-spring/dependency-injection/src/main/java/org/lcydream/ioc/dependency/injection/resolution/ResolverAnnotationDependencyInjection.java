package org.lcydream.ioc.dependency.injection.resolution;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.resolution.annotation.CustomAutowired;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Lazy;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * 依赖处理示例
 */
public class ResolverAnnotationDependencyInjection {

    @Autowired
    @Lazy
    private User lazyUser;

    @Inject
    private User injectedUser;

    @Autowired
    private User user; //依赖查找处理
                        // DependencyDescriptor ->
                        // 必须(required=true) @Autowired
                        // 实时注入(eager=true) @Lazy
                        // 通过类型(User.class)
                        // 字段名称(user)
    @Autowired  //集合类型 BeanName -> User
    private Map<String,User> userMap;

    @Autowired //单类型
    private Optional<User> optionalUser;

    @CustomAutowired  //自定义注解
    private Optional<User> userOptional;

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(ResolverAnnotationDependencyInjection.class);
        //创建BeanDefinition构造器
        XmlBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(applicationContext);
        //定义xml资源文件路径
        String xmlPath = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(xmlPath);
        //启动Spring容器
        applicationContext.refresh();

        ResolverAnnotationDependencyInjection lazyDependencyInjection =
                applicationContext.getBean(ResolverAnnotationDependencyInjection.class);

        //期望 super user
        System.out.println("user :"+lazyDependencyInjection.user);
        System.out.println("injectedUser :"+lazyDependencyInjection.injectedUser);
        System.out.println("optionalUser :"+lazyDependencyInjection.optionalUser.get());
        System.out.println("userOptional :"+lazyDependencyInjection.userOptional.get());

        //关闭应用上下文
        applicationContext.close();
    }

}
