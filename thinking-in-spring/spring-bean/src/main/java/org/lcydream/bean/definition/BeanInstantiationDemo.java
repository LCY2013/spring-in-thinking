package org.lcydream.bean.definition;

import org.lcydream.domain.User;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean实例化示例
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        //spring应用上下文
        ClassPathXmlApplicationContext xmlApplicationContext =
                new ClassPathXmlApplicationContext("classpath:/META-INF/bean-creation-context.xml");
        //获取定义的Bean实例
        User user = xmlApplicationContext.getBean("user-by-static-method", User.class);
        System.out.println(user);
        User userInstance = xmlApplicationContext.getBean("user-by-instance-method", User.class);
        System.out.println(userInstance);
        User userFactoryBean = xmlApplicationContext.getBean("user-by-factory-bean", User.class);
        System.out.println(userFactoryBean);
        System.out.println("两个实例化的对象是否相等:"+(user == userInstance));
        System.out.println("两个实例化的对象是否相等:"+(user == userFactoryBean));
        System.out.println("两个实例化的对象是否相等:"+(userInstance == userFactoryBean));
    }

}
