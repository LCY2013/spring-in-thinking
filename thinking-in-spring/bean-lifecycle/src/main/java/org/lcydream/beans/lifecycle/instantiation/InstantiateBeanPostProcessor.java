package org.lcydream.beans.lifecycle.instantiation;

import org.lcydream.beans.lifecycle.holder.UserHolder;
import org.lcydream.domain.User;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: spring-in-thinking
 * @description: Spring Bean 实例化前中后阶段
 *      对比结果:
 *        BeanFactory阶段回调:
 *          BeanNameAware
 *          BeanClassLoaderAware
 *          BeanFactoryAware
 *        Application上下文阶段:
 *          EnvironmentAware
 *          EmbeddedValueResolveAware
 *          ResourceLoaderAware
 *          ApplicationEventPublisherAware
 *          MessageSourceAware
 *          ApplicationContextAware
 *
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-05 19:40
 */
public class InstantiateBeanPostProcessor{

    public static void main(String[] args) {
        executeBeanFactory();
        System.out.println("------------------");
        executeApplicationContext();
    }

    private static void executeBeanFactory(){
        DefaultListableBeanFactory beanFactory =
                new DefaultListableBeanFactory();
        //方法一: 向BeanFactory容器中添加BeanPostProcessor实现
        //  beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //添加BeanProcessor
        //beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());

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

    private static void executeApplicationContext(){
        //定义配置文件的位置
        String[] xmlPath = {"META-INF/dependency-lookup-context.xml","META-INF/bean-constructor-dependency-injection.xml"};
        //创建xmlApplicationContext的上下文
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext(xmlPath);

        //方法一: 向BeanFactory容器中添加BeanPostProcessor实现
        //  beanFactory.addBeanPostProcessor(new InstantiateBeanPostProcess());
        //方法二: 直接配置文件中注册BeanPostProcessor实现Bean,这里BeanFactory直接使用不了，需要ApplicationContext环境
        //<bean class="org.lcydream.beans.lifecycle.instantiation.InstantiateBeanPostProcess"/>

        //这里等于 new ClassPathXmlApplicationContext(xmlPath); 构造器创建
        //这里也可以设置配置文件
        //applicationContext.setConfigLocations(xmlPath);
        //启动application容器
        //applicationContext.refresh();

        //通过Bean id和类型进行依赖查找
        System.out.println(applicationContext.getBean("user", User.class));
        System.out.println(applicationContext.getBean("superUser",User.class));
        //构造器注入按类型注入，resolveDependency
        System.out.println(applicationContext.getBean("userHolder", UserHolder.class));

        //关闭application容器
        applicationContext.close();
    }

}
