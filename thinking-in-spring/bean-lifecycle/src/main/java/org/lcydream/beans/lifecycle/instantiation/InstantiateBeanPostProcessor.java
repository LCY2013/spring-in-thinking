package org.lcydream.beans.lifecycle.instantiation;

import org.lcydream.domain.SuperUser;
import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.util.ObjectUtils;

/**
 * @program: spring-in-thinking
 * @description: Spring Bean 实例化前中后阶段
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-05 19:40
 */
public class InstantiateBeanPostProcessor{

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //添加BeanProcessor
        beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());

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
        System.out.println(beanFactory.getBean("user", User.class));
        System.out.println(beanFactory.getBean("superUser",User.class));
    }

    static class InstantiateBeanPostProcess implements InstantiationAwareBeanPostProcessor{

        /**
         * 这里可以做一些代理类替换原生类
         * @param beanClass 目标类字节码
         * @param beanName bean名称
         * @return
         * @throws BeansException
         */
        @Override
        public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
            if(ObjectUtils.nullSafeEquals("superUser",beanName) &&
                    SuperUser.class.equals(beanClass)){
                //把配置文件中的SuperUser替换掉
                return new SuperUser();
            }
            //为null的时候就是用默认配置文件中的
            return null;
        }
    }

}
