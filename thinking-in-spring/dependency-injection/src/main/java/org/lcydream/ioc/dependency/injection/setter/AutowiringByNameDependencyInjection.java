package org.lcydream.ioc.dependency.injection.setter;

import org.lcydream.ioc.dependency.injection.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 通过名称自动注入
 */
public class AutowiringByNameDependencyInjection {

    public static void main(String[] args) {
        //创建一个BeanFactory
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //创建一个xml BeanDefinition读取器
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义读取配置文件地址
        String xmlPath = "classpath:/META-INF/autowiring-byname-dependency-setter-injection.xml";
        //读取文件
        beanDefinitionReader.loadBeanDefinitions(xmlPath);

        UserHolder userHolder = beanFactory.getBean(UserHolder.class);
        System.out.println(userHolder);
    }

}
