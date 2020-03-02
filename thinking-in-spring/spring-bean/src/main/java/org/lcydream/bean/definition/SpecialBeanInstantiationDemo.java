package org.lcydream.bean.definition;

import org.lcydream.bean.factory.UserFactory;
import org.lcydream.bean.factory.cometure.DefaultUserFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 特殊方式的Bean实例化示例
 *      1、{@link java.util.ServiceLoader},{@link ServiceLoader.LazyClassPathLookupIterator}
 */
public class SpecialBeanInstantiationDemo {

    public static void main(String[] args) {
        //spring应用上下文
        ClassPathXmlApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-special-context.xml");
        ServiceLoader<UserFactory> userFactoryServiceloader =
                xmlApplicationContext.getBean("userFactoryServiceloader", ServiceLoader.class);
        doServiceLoader(userFactoryServiceloader);

        //java提供的依赖查找
        doServiceLoader();

        //通过AutowireCapableBeanFactory来创建
        AutowireCapableBeanFactory autowireCapableBeanFactory =
                xmlApplicationContext.getAutowireCapableBeanFactory();
        DefaultUserFactory defaultUserFactory = autowireCapableBeanFactory.createBean(DefaultUserFactory.class);
        System.out.println(defaultUserFactory.createUser());
    }

    public static void doServiceLoader(){
        //获取UserFactory定义
        ServiceLoader<UserFactory> serviceLoader =
                ServiceLoader.load(UserFactory.class,Thread.currentThread().getContextClassLoader());
        doServiceLoader(serviceLoader);
    }

    public static void doServiceLoader(ServiceLoader<UserFactory> serviceLoader){
        Iterator<UserFactory> iterator = serviceLoader.iterator();
        while (iterator.hasNext()){
            UserFactory userFactory = iterator.next();
            System.out.println(userFactory.createUser());
        }
    }

}
