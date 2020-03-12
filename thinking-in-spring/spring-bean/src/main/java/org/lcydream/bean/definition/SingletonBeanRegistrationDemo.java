package org.lcydream.bean.definition;

import org.lcydream.bean.factory.UserFactory;
import org.lcydream.bean.factory.cometure.DefaultUserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 单体Bean注册示例
 */
public class SingletonBeanRegistrationDemo {

    public static void main(String[] args) {
        //创建BeanFactory容器
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //生成一个单体实例对象
        UserFactory userFactory = new DefaultUserFactory();
        final ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        //将Bean示例注册到Spring IOC中
        beanFactory.registerSingleton("userFactory",userFactory);
        //启动容器
        applicationContext.refresh();

        //依赖查找UserFactory
        UserFactory userFactoryByIOC = beanFactory.getBean("userFactory", UserFactory.class);
        System.out.println("userFactoryByIOC == userFactory ? "+(userFactoryByIOC==userFactory));

        //停止容器
        applicationContext.close();
    }
}
