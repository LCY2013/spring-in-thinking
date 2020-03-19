package org.lcydream.ioc.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

/**
 * 依赖查找和依赖注入区别
 *   Spring内建单例对象 -> {@link AbstractApplicationContext#prepareBeanFactory(ConfigurableListableBeanFactory)}
 *      // Tell the internal bean factory to use the context's class loader etc.
 * 		beanFactory.setBeanClassLoader(getClassLoader());
 * 		beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
 * 		beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));
 *
 * 		// Configure the bean factory with context callbacks.
 * 		beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
 * 		beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
 * 		beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
 * 		beanFactory.ignoreDependencyInterface(ResourceLoaderAware.class);
 * 		beanFactory.ignoreDependencyInterface(ApplicationEventPublisherAware.class);
 * 		beanFactory.ignoreDependencyInterface(MessageSourceAware.class);
 * 		beanFactory.ignoreDependencyInterface(ApplicationContextAware.class);
 *      游离对象注入:
 *          // BeanFactory interface not registered as resolvable type in a plain factory.
 * 		    // MessageSource registered (and found for autowiring) as a bean.
 *          beanFactory.registerResolvableDependency(BeanFactory.class, beanFactory);
 * 		    beanFactory.registerResolvableDependency(ResourceLoader.class, this);
 * 		    beanFactory.registerResolvableDependency(ApplicationEventPublisher.class, this);
 * 		    beanFactory.registerResolvableDependency(ApplicationContext.class, this);
 * 		    这几个对象不能通过依赖查找，但是可以依赖注入
 * 	Spring内建依赖  ->  {@link AnnotationConfigUtils#registerAnnotationConfigProcessors(BeanDefinitionRegistry,Object)}
 *       内建依赖
 *          CONFIGURATION_BEAN_NAME_GENERATOR
 *          CONFIGURATION_ANNOTATION_PROCESSOR_BEAN_NAME
 *          REQUIRED_ANNOTATION_PROCESSOR_BEAN_NAME
 *          COMMON_ANNOTATION_PROCESSOR_BEAN_NAME
 *          PERSISTENCE_ANNOTATION_PROCESSOR_BEAN_NAME
 *          PERSISTENCE_ANNOTATION_PROCESSOR_CLASS_NAME
 *          EVENT_LISTENER_PROCESSOR_BEAN_NAME
 *          EVENT_LISTENER_FACTORY_BEAN_NAME
 *
 *  对比依赖注入和依赖查找？
 *      结论:
 *         相同点:
 *              通过Spring BeanDefinition注册的都可以被二者拿到
 *              Spring API提供Bean实例注册，也可以被二者拿到
 *         不同点:
 *              有一些Spring容器内建的对象只能通过依赖注入获取到，而不能通过依赖查询获取的
 *              比如: BeanFactory,ResourceLoader,ApplicationEventPublisher,ApplicationContext
 *              这种对象叫做非Spring容器管理对象，我们也可以通过API进行这些对象的自定义注入
 *              API -> {@link ConfigurableListableBeanFactory#registerResolvableDependency(Class, Object)}
 *
 */
public class AnnotationDependencySource {

    /**
     * @Autowired 在{@link AnnotationDependencySource#postProcessProperties()} 时执行，
     * 所以它在Setter前，也在@PostConstruct前
     */
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init(){
        System.out.println("BeanFactory == ApplicationContext ? "+(beanFactory == applicationContext));
        System.out.println("BeanFactory == ApplicationContext.getAutowireCapableBeanFactory() ? "+(beanFactory == applicationContext.getAutowireCapableBeanFactory()));
        System.out.println("ResourceLoader == ApplicationContext ? "+(resourceLoader == applicationContext));
        System.out.println("ApplicationEventPublisher == ApplicationContext ? "+(applicationEventPublisher == applicationContext));
    }

    /**
     * org.springframework.beans.factory.NoSuchBeanDefinitionException:
     *  No qualifying bean of type 'org.springframework.beans.factory.BeanFactory' available
     *  说明这里的几个对象不能够通过依赖查找获取到，而可以通过依赖注入获取
     */
    @PostConstruct
    public void lookup(){
        System.out.println(getBean(BeanFactory.class));
        System.out.println(getBean(ResourceLoader.class));
        System.out.println(getBean(ApplicationEventPublisher.class));
        System.out.println(getBean(ApplicationContext.class));
    }

    public <T> T getBean(Class<T> clazz){
        try {
            return beanFactory.getBean(clazz);
        }catch (NoSuchBeanDefinitionException e){
            System.err.println(clazz.getSimpleName() + "不支持BeanFactory查找");
        }
        return null;
    }

    public static void main(String[] args) {
        //创建应用上下文
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext();
        //注册配置Bean
        applicationContext.register(AnnotationDependencySource.class);
        //启动Spring容器
        applicationContext.refresh();

        AnnotationDependencySource lazyDependencyInjection =
                applicationContext.getBean(AnnotationDependencySource.class);


        //关闭应用上下文
        applicationContext.close();
    }

}
