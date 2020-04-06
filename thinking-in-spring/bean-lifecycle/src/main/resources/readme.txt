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











