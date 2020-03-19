package org.lcydream.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

/**
 *  注册非Spring容器管理Bean示例
 */
public class RegisterResolvableDependencySource {

    @Autowired
    private String name;

    @PostConstruct
    public void init(){
        System.out.println(name);
    }

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(RegisterResolvableDependencySource.class);

        //添加BeanFactoryPostProcessors,让其注入流程在BeanFactoryInitialization之前,具体流程applicationContext.refresh()
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class,"fufeng");
        });
        //这里会覆盖前面的fufeng
        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class,"fufeng1226");
        });

        //启动Spring容器
        applicationContext.refresh();

        //获取 ConfigurableListableBeanFactory#registerResolvableDependency()
        /*ConfigurableListableBeanFactory configurableListableBeanFactory =
                applicationContext.getBeanFactory();
        configurableListableBeanFactory
                .registerResolvableDependency(String.class,"fufeng");
        这里执行会出现NoSuchBeanDefinitionException，因为容器启动流程以及走完( applicationContext.refresh())
        所以需要通过applicationContext.addBeanFactoryPostProcessor()前置其加入非Spring容器管理的时机
        */

        //测试依赖查找,结果->NoSuchBeanDefinitionException
        //applicationContext.getBean(String.class);

        //关闭应用上下文
        applicationContext.close();
    }

}
