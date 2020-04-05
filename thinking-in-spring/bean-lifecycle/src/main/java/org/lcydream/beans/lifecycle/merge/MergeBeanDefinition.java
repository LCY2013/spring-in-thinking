package org.lcydream.beans.lifecycle.merge;

import org.lcydream.beans.lifecycle.annotation.AnnotatedBeanDefinitionParsing;
import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.PropertiesBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;

/**
 *  合并BeanDefinition
 *
 */
public class MergeBeanDefinition {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //实例化 XML 资源读取 BeanDefinitionReader
        XmlBeanDefinitionReader definitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义配置文件的位置
        String xmlPath = "META-INF/dependency-lookup-context.xml";
        //指定编码格式的Resource
        Resource resource = new ClassPathResource(xmlPath);
        EncodedResource encodedResource = new EncodedResource(resource , "UTF-8");
        //加载Property资源
        int loadBeanDefinitions = definitionReader.loadBeanDefinitions(encodedResource);
        System.out.println("已经加载的BeanDefinition数量:"+loadBeanDefinitions);
        //通过Bean id和类型进行依赖查找
        System.out.println(beanFactory.getBean("user",User.class));
        System.out.println(beanFactory.getBean("superUser",User.class));
    }

}
