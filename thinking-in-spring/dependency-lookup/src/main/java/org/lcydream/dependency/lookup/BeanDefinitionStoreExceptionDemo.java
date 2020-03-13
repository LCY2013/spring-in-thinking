package org.lcydream.dependency.lookup;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 * {@link BeanDefinitionStoreException} 异常示例
 *  出现情况Bean元信息非法，xml配置无法解析时发送
 */
public class BeanDefinitionStoreExceptionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean信息
        annotationConfigApplicationContext.register(BeanDefinitionStoreExceptionDemo.class);

        ConfigurableListableBeanFactory beanFactory =
                annotationConfigApplicationContext.getBeanFactory();

        if(beanFactory instanceof DefaultListableBeanFactory) {
            DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory)beanFactory;
            XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
            xmlBeanDefinitionReader.loadBeanDefinitions("classpath:/META-INF/bean.xml");
        }

        //启动应用上下文
        annotationConfigApplicationContext.refresh();

        //关闭应用上下文
        annotationConfigApplicationContext.close();
    }

}
