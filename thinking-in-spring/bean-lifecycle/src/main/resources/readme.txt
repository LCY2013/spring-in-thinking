Spring Bean生命周期
    1、Spring Bean元信息配置阶段
    2、Spring Bean元信息解析阶段
    3、Spring Bean注册阶段
    4、Spring BeanDefinition合并阶段
    5、Spring Bean Class 加载阶段
    6、Spring Bean 实例化前阶段
    7、Spring Bean 实例化阶段
    8、Spring Bean 实例化后阶段
    9、Spring Bean 属性负值阶段
    10、Spring Aware 接口回调阶段
    11、Spring Bean 初始化前阶段
    12、Spring Bean 初始化阶段
    13、Spring Bean 初始化后阶段
    14、Spring Bean 初始化完成阶段
    15、Spring Bean 销毁前阶段
    16、Spring Bean 销毁阶段
    17、Spring Bean 垃圾回收

Spring Bean元信息配置阶段
    BeanDefinition配置
       面向资源
            XML配置 XmlBeanDefinitionReader
            Properties资源配置 PropertiesBeanDefinitionReader
       面向注解
       面向API

Spring Bean元信息解析阶段
    面向资源BeanDefinition解析
        BeanDefinitionReader
        XML解析器 - BeanDefinitionParser
    面向注解BeanDefinition解析
        AnnotatedBeanDefinitionReader

Spring Bean注册阶段
    BeanDefinitionRegistry#registerBeanDefinition

Spring BeanDefinition合并阶段
    BeanDefinition 合并
        父子 BeanDefinition 合并  RootBeanDefinition,不存在Parent，存在Parent的BeanDefinition(GenericBeanDefinition)
            当前 BeanFactory 查找
            层次 BeanFactory 查找

Spring Bean Class 加载阶段  AbstractBeanFactory#resolveBeanClass  获取ClassLoader可以通过实现ClassLoaderAware
    ClassLoader 类加载
    Java Security 安全控制
    ConfigurableBeanFactory 临时ClassLoader

Spring Bean 实例化前阶段
    非主流生命周期 - Bean 实例化前阶段
        InstantiationAwareBeanPostProcessor#postProcessBeforeInstantiation

Spring Bean 实例化阶段
    实例化方式
        传统实例化方式
            实例化策略 - InstantiationStrategy
            代码位置:
                ->  createBeanInstance()
                    ->  instantiateBean()
                        ->  getInstantiationStrategy() 选择实例化策略
                            ->  instantiate()
        构造依赖注入

Spring Bean 实例化后阶段
    Bean 属性赋值(Populate)判断 doCreationBean
    InstantiationAwareBeanPostProcessor#postProcessAfterInstantiation

Spring Bean 属性赋值前阶段
    Bean属性值元信息
        PropertyValues
    Bean属性赋值前回调
        Spring1.2 - 5.0 : InstantiationAwareBeanPostProcessor#postProcessPropertyValues
        Spring5.1 : InstantiationAwareBeanPostProcessor#postProcessProperties

AbstractAutowireCapableBeanFactory.doCreateBean ->
            AbstractAutowireCapableBeanFactory.populateBean -> 属性赋值(Populate)判断
            AbstractAutowireCapableBeanFactory.initializeBean -> 初始化
                AbstractAutowireCapableBeanFactory.invokeAwareMethods -> 执行aware回调
        AbstractAutowireCapableBeanFactory#invokeAwareMethods 执行顺序
        BeanNameAware -> BeanClassLoaderAware -> BeanFactoryAware

Spring Bean Aware接口回调阶段
    Spring Aware 接口
        BeanNameAware
        BeanClassLoaderAware
        BeanFactoryAware
        EnvironmentAware
        EmbeddedValueResolveAware
        ResourceLoaderAware
        ApplicationEventPublisherAware
        MessageSourceAware
        ApplicationContextAware

Spring Bean 初始化前阶段
    已完成
        Bean 实例化
        Bean 属性赋值
        Bean Aware 接口回调
    方法回调
        BeanPostProcessor#postProcessBeforeInitialization

Spring Bean 初始化阶段
    Bean 初始化(Initialization)
        @PostConstruct 注解标注方法
        实现InitializingBean#afterPropertiesSet方法
        自定义初始化方法<bean class="" init-method=""/>,@Bean(initMethod="")

Spring Bean 初始化后阶段
    方法回调 BeanPostProcessor#postProcessAfterInitialization

Spring Bean 初始化完成阶段
    方法回调
        Spring4.1+: SmartInitializingSingleton#afterSingletonsInstantiated

Spring Bean 销毁前阶段
    方法回调
        DestructionAwareBeanPostProcessor#postProcessBeforeDestruction

Spring Bean 销毁阶段
    Bean 销毁 (Destroy)
        @PreDestroy 标注方法
        实现DisposableBean#destroy
        自定义销毁方法 <bean class="" destroy-method=""/>,@Bean(destroyMethod="")

Spring Bean 垃圾收集
    Bean 垃圾回收(GC)
        关闭Spring容器(应用上下文)
        执行GC
        Spring Bean 覆盖finalize()方法被调用(这里是JDK机制,不是Spring实现)

面试部分
    BeanPostProcessor的使用场景？
    答: 1、BeanPostProcessor提供Spring Bean初始化前和初始化后的生命周期回调，
            分别对应
                BeanPostProcessor#postProcessBeforeInitialization
                BeanPostProcessor#postProcessAfterInitialization
            允许对有需求的Bean进行扩展，或者替换
        2、ApplicationContext相关的Aware回调也是基于BeanPostProcessor实现，
            即ApplicationContextAwareProcessor。

    BeanFactoryPostProcessor与BeanPostProcessor的区别?
    答: BeanFactoryPostProcessor是Spring BeanFactory(ConfigurableListableBeanFactory)的后置处理器，
        用于扩展BeanFactory，或者通过BeanFactory进行依赖查找和依赖注入。
        BeanFactoryPostProcessor必须在Spring ApplicationContext执行，
        BeanFactory无法与其直接交互。
        而BeanPostProcessor则与BeanFactory关联，属于N对1的关系。

    BeanFactory是怎样处理Bean的生命周期的？
    答:  默认实现 DefaultListableBeanFactory
        1、Spring Bean元信息配置阶段    -> xml/properties
        2、Spring Bean元信息解析阶段    ->  BeanDefinitionReader -> BeanDefinitionParser
        3、Spring Bean注册阶段   ->  registerBeanDefinition()
        4、Spring BeanDefinition合并阶段    ->   getMergedBeanDefinition()
        5、Spring Bean Class 加载阶段   ->   resolveBeanClass()
        6、Spring Bean 实例化前阶段    ->  resolveBeforeInstantiation()
        7、Spring Bean 实例化阶段     ->  createBeanInstance()
        8、Spring Bean 实例化后阶段    ->  populateBean()
        9、Spring Bean 属性负值阶段    ->  populateBean()
        10、Spring Aware 接口回调阶段  ->  initializeBean()
        11、Spring Bean 初始化前阶段   ->  initializeBean()
        12、Spring Bean 初始化阶段    ->  initializeBean()
        13、Spring Bean 初始化后阶段   ->  initializeBean()
        14、Spring Bean 初始化完成阶段  ->  preInstantiateSingletons()
        15、Spring Bean 销毁前阶段    ->  destroyBean()
        16、Spring Bean 销毁阶段     ->  destroyBean()
        17、Spring Bean 垃圾回收     ->  jdk回收










