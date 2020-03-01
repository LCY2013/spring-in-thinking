package org.lcydream.dependency.lookup;

import org.lcydream.annotation.Super;
import org.lcydream.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 *  依赖查找示例
 *      1、通过名称方式查找
 *      2、通过类查找
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        //spring context上下文
        //spring xml
        ClassPathXmlApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-lookup-context.xml");
        //实时查找
        lookUpRealTime(classPathXmlApplicationContext);
        //延时查找
        lookUpLazy(classPathXmlApplicationContext);
        //实时按类型查找
        lookUpByType(classPathXmlApplicationContext);
        //按Bean类型进行集合查找
        lookUpCollectionType(classPathXmlApplicationContext);
        //通过注解查找
        lookUpByAnnotation(classPathXmlApplicationContext);
    }

    /**
     * 通过注解查询
     * @param beanFactory 工厂
     */
    private static void lookUpByAnnotation(BeanFactory beanFactory) {
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = (Map)listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("通过注解查询一个集合数据:"+beansOfType);
        }
    }

    /**
     * 按集合类型查找
     *  {@link ListableBeanFactory}
     * @param beanFactory 工厂
     */
    private static void lookUpCollectionType(BeanFactory beanFactory) {
        //org.springframework.beans.factory.ListableBeanFactory
        if(beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> beansOfType = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("通过类型查询一个集合数据:"+beansOfType);
        }
    }

    /**
     *  通过类型查找
     * @param beanFactory 工厂
     */
    private static void lookUpByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println(user.hashCode()+"通过类型实时查找:"+user);
    }

    /**
     * 实时查询Bean，打印Bean信息
     * @param beanFactory 工厂
     */
    private static void lookUpRealTime(BeanFactory beanFactory){
        //Object user = classPathXmlApplicationContext.getBean("user");
        User user = (User) beanFactory.getBean("user");
        System.out.println(user.hashCode()+"通过名称实时查找:"+user);
    }

    /**
     * 延时查询Bean，打印Bean信息
     * @param beanFactory 工厂
     */
    private static void lookUpLazy(BeanFactory beanFactory){
        //这里不会生成新的Bean，而FactoryBean时会按需生成不同Bean
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean(
                "objectFactoryCreatingFactoryBean");
        User user = objectFactory.getObject();
        System.out.println(user.hashCode()+"延迟查找:"+user);
    }
}
