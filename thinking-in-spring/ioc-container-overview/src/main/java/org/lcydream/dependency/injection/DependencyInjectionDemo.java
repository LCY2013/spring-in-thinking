package org.lcydream.dependency.injection;

import org.lcydream.domain.User;
import org.lcydream.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

/**
 *  依赖注入示例
 */
public class DependencyInjectionDemo {

    public static void main(String[] args) {
        //spring context上下文
        //spring xml
//        BeanFactory beanFactory =
//                new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        //依赖来源一: 自定义Bean
        UserRepository userRepository = applicationContext
                        .getBean("userRepository", UserRepository.class);
        //System.out.println(userRepository.getUsers());

        System.out.println(applicationContext == userRepository.getBeanFactory());
        System.out.println(applicationContext);

        //依赖来源二: 依赖注入(内建依赖)
        //??? UserRepository里面BeanFactory注入来源？ 它是一个DefaultListableBeanFactory，如何而来？
        System.out.println(userRepository.getBeanFactory());

        ObjectFactory<ApplicationContext> objectFactory = userRepository.getObjectFactory();
        System.out.println(objectFactory.getObject()==applicationContext);

        //依赖查找,(错误)
        //NoSuchBeanDefinitionException: No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
        //说明BeanFactory不能通过依赖查找得到，而userRepository又是如何注入的呢？
        //System.out.println(beanFactory.getBean(BeanFactory.class));

        //依赖来源三: 容器内建Bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取 EnvironmentBean 类型的Bean:"+environment);

        compareApplicationAndBeanFactory(userRepository,applicationContext);
    }

    public static void whoIsIocContainer(UserRepository userRepository,BeanFactory beanFactory){
        //等式不成立，因为他们是ApplicationContext与BeanFactory，而ApplicationContext本身也是BeanFactory的实现
        //ClassPathXmlApplicationContext  <-  AbstractRefreshableApplicationContext(private DefaultListableBeanFactory beanFactory;)  <- applicationContext   <- BeanFactory
        System.out.println(beanFactory == userRepository.getBeanFactory());
        //官方介绍https://docs.spring.io/spring/docs/5.2.4.RELEASE/spring-framework-reference/core.html#beans
    }

    /**
     * The org.springframework.beans and org.springframework.context packages are the basis for Spring Framework’s IoC container.
     * The BeanFactory interface provides an advanced configuration mechanism capable of managing any type of object.
     * ApplicationContext is a sub-interface of BeanFactory. It adds:
     *  1、Easier integration with Spring’s AOP features
     *  2、 Message resource handling (for use in internationalization)
     *  3、 Event publication
     *  4、 Application-layer specific contexts such as the WebApplicationContext for use in web applications.
     * @param userRepository
     * @param applicationContext
     */
    public static void compareApplicationAndBeanFactory(UserRepository userRepository,ClassPathXmlApplicationContext applicationContext){
        //true 说明applicationContext组装的BeanFactory的能力，BeanFactory是IOC的底层，而ApplicationContext是BeanFactory上的高级特性
        System.out.println(userRepository.getBeanFactory() == applicationContext.getBeanFactory());
    }

}
