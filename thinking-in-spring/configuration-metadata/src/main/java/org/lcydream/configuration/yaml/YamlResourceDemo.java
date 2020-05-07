package org.lcydream.configuration.yaml;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @program: spring-in-thinking
 * @description: YAML 资源示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 23:44
 */
public class YamlResourceDemo {

    public static void main(String[] args) {
        //创建唯一个IOC实现
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //创建一个BeanDefinitionReader
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义资源文件位置
        String xmlResourceLocal = "classpath:/META-INF/yaml-source-context.xml";
        //加载xml资源
        beanDefinitionReader.loadBeanDefinitions(xmlResourceLocal);

        //通过依赖查找YamlMapFactoryBean
        final Map yamlMap = beanFactory.getBean("yamlMap", Map.class);
        System.out.println(yamlMap);
    }

}
