依赖注入的模式和类型
    setter <property name="user" ref="userBean"></property>
    构造器 <constructor-arg name="user" reg="userBean"/>
    字段 @Autowired User user
    方法 @Autowired public void user(User user){this.user = user;}
    接口回调 class userBean implements BeanFactoryAware{}

接口回调注入
    Aware系列接口回调
        BeanFactoryAware                获取IOC容器 - BeanFactory
        ApplicationContextAware         获取Spring应用上下文 - ApplicationContext 对象
        EnvironmentAware                获取Environment对象
        ResourceLoaderAware             获取资源加载器 对象 - ResourceLoader
        BeanClassLoaderAware            获取加载当前Bean Class的 ClassLoader
        BeanNameAware                   获取当前Bean的名称
        MessageSourceAware              获取MessageSource对象,用于Spring国际化
        ApplicationEventPublisherAware  获取ApplicationEventPublisher对象，用于Spring事件
        EmbeddedValueResolverAware      获取StringValueResolver对象，用于占位符处理

延迟依赖注入
    使用API ObjectFactory 延迟注入
        单一类型
        集合类型
    使用API ObjectProvider 延迟注入 (类型安全) getIfAvailable()
        单一类型
        集合类型

依赖处理过程
    入口 - org.springframework.beans.factory.support.R#resolveDependency()
    依赖描述符 - org.springframework.beans.factory.config.DependencyDescriptor
    自动绑定候选对象处理器 - org.springframework.beans.factory.support.AutowireCandidateResolver








