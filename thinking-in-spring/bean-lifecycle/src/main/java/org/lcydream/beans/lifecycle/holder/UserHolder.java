package org.lcydream.beans.lifecycle.holder;

import org.lcydream.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @program: spring-in-thinking
 * @description: #{description}
 * @author: <a href="https://github.com/lcy2013">MagicLuo</a>
 * @create: 2020-04-05 20:28
 */
public class UserHolder implements BeanNameAware, BeanFactoryAware,
        BeanClassLoaderAware, EnvironmentAware,
        InitializingBean,SmartInitializingSingleton,
        DisposableBean{

    private final User user;

    private Integer number;

    private String description;

    private ClassLoader classLoader;

    private String beanName;

    private BeanFactory beanFactory;

    private Environment environment;

    /**
     * 依赖注解驱动，需要加入注解扫描的BeanPostProcessor(CommonAnnotationBeanPostProcessor)
     */
    @PostConstruct
    private void init(){
        this.description = "user holder v4";
        System.out.println(description);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.description = "user holder v5";
        System.out.println(description);
    }

    public void initMethod(){
        this.description = "user holder v6";
        System.out.println(description);
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.description = "user holder v8";
        System.out.println(description);
    }

    @PreDestroy
    public void preDestroy(){
        this.description = "user holder v10";
        System.out.println(description);
    }

    @Override
    public void destroy() throws Exception {
        this.description = "user holder v11";
        System.out.println(description);
    }

    public void doDestroy(){
        this.description = "user holder v12";
        System.out.println(description);
    }

    public UserHolder(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*
        它们具体执行的位置步骤:
        AbstractAutowireCapableBeanFactory.doCreateBean ->
            AbstractAutowireCapableBeanFactory.populateBean -> 属性赋值(Populate)判断
            AbstractAutowireCapableBeanFactory.initializeBean -> 实例化
                AbstractAutowireCapableBeanFactory.invokeAwareMethods -> 执行aware回调
        AbstractAutowireCapableBeanFactory#invokeAwareMethods 执行顺序
        BeanNameAware -> BeanClassLoaderAware -> BeanFactoryAware
     */

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
    }

    /*
        AbstractApplicationContext.prepareBeanFactory,属于ApplicationContext的范围
        这里不会调用，因为我们InstantiateBeanPostProcessor这里没有使用ApplicationContext，而是直接用的BeanFactory
        ApplicationContextAwareProcessor.postProcessBeforeInitialization
        具体顺序ApplicationContextAwareProcessor.invokeAwareInterfaces
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                ", beanName='" + beanName + '\'' +
                '}';
    }
}
