package org.lcydream.ioc.dependency.injection.resolution;

import org.lcydream.domain.User;
import org.lcydream.ioc.dependency.injection.resolution.annotation.CustomAutowired;
import org.lcydream.ioc.dependency.injection.resolution.annotation.CustomInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.*;

import static java.util.Arrays.asList;
import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

/**
 * 基于自定义扩展的注解实现依赖处理示例
 */
public class CustomAnnotationDependencyInjection {

    @CustomInject
    private Optional<User> injectUser;

    @Autowired
    private Collection<AutowiredAnnotationBeanPostProcessor> beanPostProcessors;

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

    /**
     * 注入注解Bean的处理器，static主要提升类加载等级让方法可以优先被调用，
     * 不加static的话方法就必须要等类实例化后才能调用
     * @return AutowiredAnnotationBeanPostProcessor
     */
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 10)
    public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor =
                new AutowiredAnnotationBeanPostProcessor();
        //注册自定义注解类型
        beanPostProcessor.setAutowiredAnnotationType(CustomInject.class);
        //设置BeanPostProcessor的排序级别,Ordered.LOWEST_PRECEDENCE这个是级别最低也就是最后执行
        //beanPostProcessor.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        /*Set<Class<? extends Annotation>> annotations =
                new LinkedHashSet<>(asList(CustomInject.class,Autowired.class,CustomAutowired.class));
        beanPostProcessor.setAutowiredAnnotationTypes(annotations);*/
        return beanPostProcessor;
    }

//    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
//    public static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
//        AutowiredAnnotationBeanPostProcessor beanPostProcessor =
//                new AutowiredAnnotationBeanPostProcessor();
//        //注册自定义注解类型
//        //beanPostProcessor.setAutowiredAnnotationType(CustomInject.class);
//        //设置BeanPostProcessor的排序级别,Ordered.LOWEST_PRECEDENCE这个是级别最低也就是最后执行
//        //beanPostProcessor.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
//        Set<Class<? extends Annotation>> annotations =
//                new LinkedHashSet<>(asList(CustomInject.class,Autowired.class,CustomAutowired.class,Inject.class));
//        beanPostProcessor.setAutowiredAnnotationTypes(annotations);
//        return beanPostProcessor;
//    }

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(CustomAnnotationDependencyInjection.class);
        //创建BeanDefinition构造器
        XmlBeanDefinitionReader reader =
                new XmlBeanDefinitionReader(applicationContext);
        //定义xml资源文件路径
        String xmlPath = "classpath:/META-INF/dependency-lookup-context.xml";
        reader.loadBeanDefinitions(xmlPath);
        //启动Spring容器
        applicationContext.refresh();

        CustomAnnotationDependencyInjection lazyDependencyInjection =
                applicationContext.getBean(CustomAnnotationDependencyInjection.class);

        //期望 super user
        System.out.println("user :"+lazyDependencyInjection.user);
        System.out.println("injectedUser :"+lazyDependencyInjection.injectedUser);
        System.out.println("optionalUser :"+lazyDependencyInjection.optionalUser);
        System.out.println("userOptional :"+lazyDependencyInjection.userOptional);
        System.out.println("injectUser :"+lazyDependencyInjection.injectUser);
        System.out.println("beanPostProcessors :"+lazyDependencyInjection.beanPostProcessors);

        //关闭应用上下文
        applicationContext.close();
    }

}
