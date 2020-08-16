Spring应用上下文生命周期
    1、Spring应用上下文启动准备阶段
    2、BeanFactory创建阶段
    3、BeanFactory准备阶段
    4、BeanFactory后置处理阶段
    5、BeanFactory注册BeanPostProcessor阶段
    6、初始化内建Bean: MessageSource
    7、初始化内建Bean: Spring事件广播器
    8、Spring应用上下文刷新阶段
    9、Spring事件监听器注册阶段
    10、BeanFactory初始化完成阶段
    11、Spring应用上下文刷新完成阶段
    12、Spring应用上下文启动阶段
    13、Spring应用上下文停止阶段
    14、Spring应用上下文关闭阶段

Spring 应用上下文启动准备阶段
    org.springframework.context.support.AbstractApplicationContext.prepareRefresh()方法
        启动时间 - startupDate
        状态标识 - closed.set(false) , active.set(true)
        初始化PropertySources - initPropertySources()
        校验Environment中必要属性 - getEnvironment().validateRequiredProperties()
        初始化事件监听器集合 - this.earlyApplicationListeners = new LinkedHashSet<>(this.applicationListeners);
        初始化Spring早期事件集合
        this.earlyApplicationEvents = new LinkedHashSet<>();

BeanFactory 创建阶段
    AbstractApplicationContext.obtainFreshBeanFactory()方法
        刷新Spring应用上下文底层容器BeanFactory - refreshBeanFactory()
            销毁或者关闭BeanFactory，如果已经存在的话
            创建BeanFactory - createBeanFactory()
            设置BeanFactory ID - beanFactory.setSerializationId(getId())
            设置"是否允许BeanDefinition重复定义" - customizeBeanFactory(beanFactory)
            设置"是否允许循环引用(依赖)" - customizeBeanFactory(beanFactory)
            加载BeanDefinition - loadBeanDefinitions(beanFactory)
            关联新建BeanFactory到Spring应用上下文
        返回Spring应用上下文底层BeanFactory - getBeanFactory()

BeanFactory 准备阶段
    AbstractApplicationContext.prepareBeanFactory()方法
        关联ClassLoader
        设置Bean表达式处理器
        添加PropertyEditorRegistrar实现 - ResourceEditorRegistrar
        添加Aware回调接口BeanPostProcessor实现 - ApplicationContextAwareProcessor
        忽略Aware回调接口作为依赖注入接口
        注册ResolvableDependency对象 - BeanFactory、ResourceLoader、ApplicationEventPublisher以及ApplicationContext
        注册ApplicationListenerDetector对象
        注册LoadTimeWeaverAwareProcessor对象
        注册单例对象 - Environment、Java System Properties以及OS环境变量

BeanFactory后置处理阶段
    AbstractApplicationContext.postProcessBeanFactory()方法
        抽象方法，由子类实现
    AbstractApplicationContext.invokeBeanFactoryPostProcessors()方法
        调用BeanFactoryPostProcessor 或者 BeanDefinitionRegistryPostProcessor 后置处理方法
        注册LoadTimeWeaverAwareProcessor对象

BeanFactory注册BeanPostProcessor阶段
    AbstractApplicationContext.registerBeanPostProcessors()方法
        注册PriorityOrdered类型的BeanPostProcessor Beans
        注册internal类型的BeanPostProcessor Beans
        注册ordered类型的BeanPostProcessor Beans
        注册nonOrdered类型的BeanPostProcessor Beans
        注册普通BeanPostProcessor Beans
        注册MergedBeanDefinition类型的BeanPostProcessor Beans
        注册ApplicationListenerDetector对象

初始化内建Bean: MessageSource
    AbstractApplicationContext.initMessageSource()方法
        messageSource内建依赖

初始化内建Bean: Spring事件广播器
    AbstractApplicationContext.initApplicationEventMulticaster()方法
        ApplicationEventPublisher实现

Spring 应用上下文刷新阶段
    AbstractApplicationContext.onRefresh() 方法
        子类覆盖该方法
            org.springframework.web.context.support.AbstractRefreshableWebApplicationContext.onRefresh
            org.springframework.web.context.support.GenericWebApplicationContext.onRefresh
            org.springframework.boot.web.reactive.context.ReactiveWebServerApplicationContext.onRefresh
            org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.onRefresh
            org.springframework.web.context.support.StaticWebApplicationContext.onRefresh














