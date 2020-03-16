package org.lcydream.ioc.dependency.injection.setter;

import org.lcydream.ioc.dependency.injection.UserHolder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * 基于XMl资源的Setter注入示例
 */
public class XmlDependencyInjection {

    public static void main(String[] args) {
        //创建一个没有依赖关系的BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory =
                new DefaultListableBeanFactory();
        //创建XML资源BeanDefinition解析器
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        //定义资源路径
        String xmlPath = "classpath:/META-INF/dependency-setter-injection.xml";
        //加载BeanDefinition的资源
        xmlBeanDefinitionReader.loadBeanDefinitions(xmlPath);

        //获取Bean信息
        UserHolder userHolder = defaultListableBeanFactory.getBean(UserHolder.class);
        System.out.println(userHolder.getUser());
    }

}

