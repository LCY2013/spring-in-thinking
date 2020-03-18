层次性依赖查找
    层次性依赖查找接口-HierarchicalBeanFactory
        双亲BeanFactory: getParentBeanFactory()
        层次性查找
            根据Bean名称查找
                基于containsLocalBean方法实现
            根据Bean类型查找实例列表
                单一类型: BeanFactoryUtils#beanOfType
                集合类型: BeanFactoryUtils#beanOfTypeIncludingAncestors
            根据Java注解查找名称列表
                BeanFactoryUtils#beanNamesForAnnotationIncludingAncestors
延迟依赖查找
    Bean延迟依赖查找接口
        org.springframework.beans.factory.ObjectFactory

        org.springframework.beans.factory.ObjectProvider
            Spring5对java8特性扩展
                ObjectProvider#getIfAvailable
                ObjectProvider#ifAvailable
                ObjectProvider#iterator
                ObjectProvider#getIfUnique
                ObjectProvider#ifUnique
            Stream流式
                ObjectProvider#stream
                ObjectProvider#orderedStream

Spring内建依赖
    org.springframework.context.annotation.AnnotationConfigUtils
        org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor ...
Spring内建单例对象
    org.springframework.context.support.AbstractApplicationContext
        org.springframework.context.MessageSource ....
Spring非spring管理对象
    org.springframework.context.support.AbstractApplicationContext#prepareBeanFactory()


Spring关于类异常 org.springframework.beans.BeansException及其子类
    1、org.springframework.beans.factory.NoSuchBeanDefinitionException
        demo: org.lcydream.dependency.lookup.TypeSafeDependencyLookup
    2、org.springframework.beans.factory.NoUniqueBeanDefinitionException
        demo: org.lcydream.dependency.lookup.NoUniqueBeanDefinitionExceptionDemo
    3、org.springframework.beans.BeanInstantiationException
        demo: org.lcydream.dependency.lookup.BeanInstantiationExceptionDemo
    4、org.springframework.beans.factory.BeanCreationException
        demo: org.lcydream.dependency.lookup.BeanCreationExceptionDemo
    5、org.springframework.beans.factory.BeanDefinitionStoreException
        demo: org.lcydream.dependency.lookup.BeanDefinitionStoreExceptionDemo

ObjectFactory与BeanFactory区别?
    两者都有依赖查找的能力
    不过ObjectFactory关注的是某一个或者一种类型的Bean依赖查找，
    它本身没有依赖查找功能，具体能力也是由BeanFactory提供。
    (
        org.springframework.beans.factory.config.ObjectFactoryCreatingFactoryBean
        org.springframework.beans.factory.config.ServiceLocatorFactoryBean
    )
    BeanFactory作为IOC容器的底层实现，提供了包括单一类型查找，集合类型查找以及层次性多种依赖查找方式。

BeanFactory#getBean()是否是线程安全？
    是线程安全的操作，操作过程会增加互斥锁,其核心存储singletonObjects 是一个线程安全的ConcurrentHashMap
    具体见代码org.springframework.beans.factory.support.DefaultSingletonBeanRegistry

Spring依赖查找与注入的来源上的区别？






