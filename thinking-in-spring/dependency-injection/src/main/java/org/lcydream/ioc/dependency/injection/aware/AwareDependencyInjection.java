package org.lcydream.ioc.dependency.injection.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 通过{@link org.springframework.beans.factory.Aware}  接口实现注入
 */
public class AwareDependencyInjection implements BeanFactoryAware, ApplicationContextAware {

    private static BeanFactory beanFactory;

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        //创建一个Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置类
        applicationContext.register(AwareDependencyInjection.class);
        //启动应用上下文
        applicationContext.refresh();

        System.out.println(applicationContext == AwareDependencyInjection.applicationContext);
        System.out.println(applicationContext.getBeanFactory() == AwareDependencyInjection.beanFactory);
        //关闭应用上下文
        applicationContext.close();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        AwareDependencyInjection.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AwareDependencyInjection.applicationContext = applicationContext;
    }
}
