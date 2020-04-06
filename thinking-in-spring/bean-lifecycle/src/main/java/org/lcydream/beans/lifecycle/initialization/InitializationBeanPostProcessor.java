package org.lcydream.beans.lifecycle.initialization;

import org.lcydream.beans.lifecycle.holder.UserHolder;
import org.lcydream.beans.lifecycle.instantiation.InstantiateBeanPostProcess;
import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * @program: spring-in-thinking
 * @description: Spring Bean 初始化前中后阶段
 *
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-05 19:40
 */
public class InitializationBeanPostProcessor {

    public static void main(String[] args) {
        executeBeanFactory();
    }

    private static void executeBeanFactory(){
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //方法一: 向BeanFactory容器中添加BeanPostProcessor实现
        //  beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //添加实例化的BeanProcessor
        beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //添加注解驱动的BeanProcessor,解决@PostConstruct注解回调问题
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        //实例化 XML 资源读取 BeanDefinitionReader
        XmlBeanDefinitionReader definitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义配置文件的位置
        String[] xmlPath = {"META-INF/dependency-lookup-context.xml","META-INF/bean-constructor-dependency-injection.xml"};
        //加载Property资源
        int loadBeanDefinitions = definitionReader.loadBeanDefinitions(xmlPath);
        System.out.println("已经加载的BeanDefinition数量:"+loadBeanDefinitions);
        //通过Bean id和类型进行依赖查找
        System.out.println(beanFactory.getBean("user", User.class));
        System.out.println(beanFactory.getBean("superUser",User.class));
        //构造器注入按类型注入，resolveDependency
        System.out.println(beanFactory.getBean("userHolder", UserHolder.class));
    }


}
