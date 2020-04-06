package org.lcydream.beans.lifecycle.destruction;

import org.lcydream.beans.lifecycle.holder.UserHolder;
import org.lcydream.beans.lifecycle.instantiation.InstantiateBeanPostProcess;
import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;

/**
 * @program: spring-in-thinking
 * @description: Bean 整个生命周期演示demo
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-06 15:26
 */
public class BeanLifecycleDemo {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();

        //BeanPostProcessor是一个CopyOnWriteArrayList，说明它是有顺序的
        //自定义DestructionAwareBeanPostProcess在前，那么就会先执行他的实现，在执行CommonAnnotationBeanPostProcessor

        //方法一: 向BeanFactory容器中添加BeanPostProcessor实现
        //  beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //添加实例化的BeanProcessor
        beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //添加Bean销毁的BeanProcessor
        beanFactory.addBeanPostProcessor(new DestructionAwareBeanPostProcess());
        //添加注解驱动的BeanProcessor,解决@PostConstruct,@PreDestroy注解回调问题
        beanFactory.addBeanPostProcessor(new CommonAnnotationBeanPostProcessor());

        //实例化 XML 资源读取 BeanDefinitionReader
        XmlBeanDefinitionReader definitionReader =
                new XmlBeanDefinitionReader(beanFactory);
        //定义配置文件的位置
        String[] xmlPath = {"META-INF/dependency-lookup-context.xml","META-INF/bean-constructor-dependency-injection.xml"};
        //加载Property资源
        int loadBeanDefinitions = definitionReader.loadBeanDefinitions(xmlPath);
        System.out.println("已经加载的BeanDefinition数量:"+loadBeanDefinitions);

        //需要显示调用，SmartInitializingSingleton接口回调才能生效
        //这个接口需要ApplicationContext的环境调用，具体在
        // -> AbstractApplicationContext#Refresh
        //      -> finishBeanFactoryInitialization
        //          -> preInstantiateSingletons
        // 在BeanFactory中只能通过显示调用
        beanFactory.preInstantiateSingletons();

        //通过Bean id和类型进行依赖查找
        System.out.println(beanFactory.getBean("user", User.class));
        System.out.println(beanFactory.getBean("superUser",User.class));

        //构造器注入按类型注入，resolveDependency
        final UserHolder userHolder = beanFactory.getBean("userHolder", UserHolder.class);
        System.out.println(userHolder);

        //显示销毁Bean(? 通过beanFactory.getBean("userHolder", UserHolder.class)，还是可以获取到同样的Bean？销毁有没有效果？)
        beanFactory.destroyBean("userHolder",userHolder);

        System.out.println(userHolder);

    }

}
