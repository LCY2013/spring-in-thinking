package org.lcydream.beans.lifecycle.properties;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 * Bean 元信息配置示例
 */
public class BeanMetadataConfiguration {

    public static void main(String[] args) {
        //定义BeanFactory容器的唯一实现
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //实例化 Properties 资源读取 BeanDefinitionReader
        PropertiesBeanDefinitionReader definitionReader =
                new PropertiesBeanDefinitionReader(beanFactory);
        //定义配置文件的位置
        String propertiesPath = "META-INF/user.properties";
        //指定编码格式的Resource
        Resource resource = new ClassPathResource(propertiesPath);
        EncodedResource encodedResource = new EncodedResource(resource , "UTF-8");
        //加载Property资源
        int loadBeanDefinitions = definitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("已经加载的BeanDefinition数量:"+loadBeanDefinitions);
        //通过Bean id和类型进行依赖查找
        System.out.println(beanFactory.getBean("user", User.class));
    }

}
