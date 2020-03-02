Spring IOC 配置元信息
    一、Bean自定义配置
        1、基于XML
        2、基于Properties
        3、基于Java Annotation
        4、基于Java API
    二、IOC容器配置
        1、基于XML
        2、基于Java Annotation
        3、基于Java API
    三、外部化配置
        1、基于Java注解

Spring应用上下文
    ApplicationContext除了提供IOC容器的功能还有：
        1、面向切面(AOP)
        2、配置元信息(Configuration metadata)
        3、资源管理(Resources)
        4、事件(Event)
        5、国际化(i18n)
        6、注解(Annotations)
        7、Environments抽象(Environment Abstraction)

Spring IOC 容器生命周期
    一、启动

    二、运行

    三、停止

Spring IOC 容器启动做了那些工作
    IOC配置元信息的定位、加载、解析，IOC容器生命周期，Spring事件发布，国际化、、、

FactoryBean和BeanFactory区别
    BeanFactory是一个IOC容器的底层实现，可以看成是IOC容器   牛奶工厂
    FactoryBean是一个用来简化创建Bean实例的工具，具体它有三个方法getObject(),isSingleton(),getObjectType()  牛奶经销商



