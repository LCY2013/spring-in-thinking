package org.lcydream.configuration.properties;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;

/**
 * @program: spring-in-thinking
 * @description: properties 资源读取示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 16:17
 */
public class PropertiesBeanDefinitionDemo {

    public static void main(String[] args) {
        //创建Spring唯一的底层实现IOC容器
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //创建一个propertiesBeanDefinitionReader
        PropertiesBeanDefinitionReader beanDefinitionReader =
                new PropertiesBeanDefinitionReader(beanFactory);
        //定义资源文件位置
        String localResource = "META-INF/users-config.properties";
        //定义Resource
        ClassPathResource pathResource = new ClassPathResource(localResource);
        EncodedResource encodedResource = new EncodedResource(pathResource,"UTF-8");
        //加载资源文件,默认加载的字符编码是ISO-8859-1
        int loadBeanDefinitions = beanDefinitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("已经加载的BeanDefinition数量:"+loadBeanDefinitions);
        //开启依赖查找，查询User对象,这里这个Bean名称就和properties中字段前缀一致
        final User user = beanFactory.getBean("myuser", User.class);
        System.out.println("User : "+user);
    }

}
