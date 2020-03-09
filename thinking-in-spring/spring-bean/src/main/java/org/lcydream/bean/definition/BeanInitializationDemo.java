package org.lcydream.bean.definition;

import org.lcydream.bean.factory.UserFactory;
import org.lcydream.bean.factory.cometure.DefaultUserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 初始化 Bean
 */
@Configuration  //configuration
public class BeanInitializationDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注入容器的配置类信息(Configuration)
        applicationContext.register(BeanInitializationDemo.class);
        //启动容器
        applicationContext.refresh();
        //非延迟加载是在IOC初始化后就进行初始化完成，而延迟话是IOC容器初始化完成后也需要按需初始化
        System.out.println("IOC初始化完成");
        //获取容器中的BeanFactory
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        System.out.println(userFactory);
        System.out.println("容器开始销毁...");
        //停止容器
        applicationContext.close();
        System.out.println("容器销毁完成...");
    }

    @Bean(initMethod = "initAnnotation",destroyMethod = "annotationDestroy")
    @Lazy(value = false)
    public UserFactory getBeanFactory(){
        return new DefaultUserFactory();
    }

    /* 一、Lazy=true执行顺序
        IOC初始化完成
        @PostConstruct :UserFactory init
        after properties : UserFactory init
        init by annotation : UserFactory init
        org.lcydream.bean.factory.cometure.DefaultUserFactory@47faa49c

       二、Lazy=false执行顺序
        @PostConstruct :UserFactory init
        after properties : UserFactory init
        init by annotation : UserFactory init
        IOC初始化完成
        org.lcydream.bean.factory.cometure.DefaultUserFactory@3232a28a
     */

}
