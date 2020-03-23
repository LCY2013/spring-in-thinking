package org.lcydream.beans.lifecycle.annotation;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotatedBeanDefinitionReader;

/**
 * Annotation BeanDefinition 解析
 */
public class AnnotatedBeanDefinitionParsing {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //基于 Java注解的AnnotatedBeanDefinitionReader 实现
        AnnotatedBeanDefinitionReader definitionReader =
                new AnnotatedBeanDefinitionReader(beanFactory);
        //读取前的BeanDefinition数量
        final int startCount = beanFactory.getBeanDefinitionCount();
        //注册当前类(非@Component class)
        definitionReader.register(AnnotatedBeanDefinitionParsing.class);
        //读取后的BeanDefinition数量
        final int endCount = beanFactory.getBeanDefinitionCount();
        System.out.println("已经加载的自定义BeanDefinition数量:"+(endCount-startCount));
        //普通的Class作为Component注入到Spring IOC容器后，通常Bean名称为annotatedBeanDefinitionParsing
        //Bean名称生成策略来自于BeanNameGenerator#generateBeanName
        AnnotatedBeanDefinitionParsing annotatedBeanDefinitionParsing =
                beanFactory.getBean("annotatedBeanDefinitionParsing", AnnotatedBeanDefinitionParsing.class);
        System.out.println(annotatedBeanDefinitionParsing);
    }

}
