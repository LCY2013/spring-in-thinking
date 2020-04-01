package com.lcydream.config;

import com.lcydream.annotation.ServiceImpl;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 *  项目编译后会出现 classes/META-INF/spring.components
 *  这里用来定义预先的一些加载的Bean
 *      com.lcydream.Application=org.springframework.stereotype.Component
 *      com.lcydream.config.AnnotationIndexedConfiguration=org.springframework.stereotype.Component,com.lcydream.config.AnnotationIndexedConfiguration
 */
//@Indexed
@Configuration
public class AnnotationIndexedConfiguration {

    @Bean
    public ClassPathBeanDefinitionScanner
            customClassPathBeanDefinitionScanner(DefaultListableBeanFactory beanFactory){
        ClassPathBeanDefinitionScanner classPathBeanDefinitionScanner =
                new ClassPathBeanDefinitionScanner(beanFactory);
        classPathBeanDefinitionScanner.addIncludeFilter(new AnnotationTypeFilter(ServiceImpl.class));
        return classPathBeanDefinitionScanner;
    }

}
