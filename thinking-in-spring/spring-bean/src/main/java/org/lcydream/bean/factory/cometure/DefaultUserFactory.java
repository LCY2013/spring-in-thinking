package org.lcydream.bean.factory.cometure;

import org.lcydream.bean.factory.UserFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * {@link UserFactory}默认实现
 */
public class DefaultUserFactory implements UserFactory, InitializingBean, DisposableBean {

    //优先级一
    //基于 @PostConstruct 注解
    @PostConstruct
    public void init(){
        System.out.println("@PostConstruct :UserFactory init");
    }

    //优先级三
    public void initAnnotation(){
        System.out.println("init by annotation : UserFactory init");
    }

    //优先级二
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean : UserFactory init");
    }

    @PreDestroy
    public void preDestroy(){
        System.out.println("@PreDestroy :UserFactory destroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean :UserFactory destroy");
    }

    public void annotationDestroy(){
        System.out.println("annotationDestroy :UserFactory destroy");
    }
}
