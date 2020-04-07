package org.lcydream.configuration.xml;

import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @program: spring-in-thinking
 * @description: Spring XML 元素扩展示例
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-07 22:01
 */
public class SpringXMLExtensibleAuthoringDemo {

    public static void main(String[] args) {
        //定义IOC容器的唯一实现
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        //定义xml的BeanDefinitionReader
        XmlBeanDefinitionReader beanDefinitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义XML 资源位置
        String xmlResourceLocal = "classpath:/META-INF/users-context.xml";
        //加载XML配置文件
        beanDefinitionReader.loadBeanDefinitions(xmlResourceLocal);

        //依赖查找
        final User user = beanFactory.getBean(User.class);
        System.out.println(user);
    }

}
