Spring Environment 抽象
    1、理解Spring Environment抽象
    2、Spring Environment接口使用场景
    3、Environment 占位符处理
    4、理解条件配置Spring Profiles
    5、Spring 4 重构@Profile
    6、依赖注入Environment
    7、依赖查找Environment
    8、依赖查找@Value
    9、Spring 类型转换在Environment中的使用
    10、Spring 类型转换在@Value中的使用
    11、Spring配置属性源PropertySource
    12、Spring内建的配置属性源
    13、基于注解扩展Spring配置数据源
    14、基于API扩展Spring配置数据源

理解Spring Environment抽象
    统一的Spring配置属性管理
        Spring Framework 3.1开始引入Environment抽象，它统一了Spring配置属性的存储，包括占位符处理和类型转换，
      不仅完整的替换了PropertyPlaceHolderConfigurer，而且支持丰富的配置属性源(PropertySource)
    条件化Spring Bean 装配管理
        通过Environment Profiles信息，帮助Spring容器提供条件化地装配Bean

Spring Environment接口使用场景
    用于属性占位符处理
    用于转换Spring配置属性类型
    用于存储Spring配置属性源(PropertySource)
    用于Profiles状态维护

Environment 占位符处理
    Spring 3.1 前占位符处理
        组件: org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
        接口: org.springframework.util.StringValueResolver
    Spring 3.1 后占位符处理
        组件: org.springframework.context.support.PropertySourcesPlaceholderConfigurer
        接口: org.springframework.beans.factory.config.EmbeddedValueResolver(Spring 4.3)

理解条件配置Spring Profiles
    Spring 3.1 条件配置
        API : org.springframework.core.env.ConfigurableEnvironment
            修改 : setActiveProfiles(String... profiles)、addActiveProfile(String profile)、setDefaultProfiles(String... profiles)
            获取 : getActiveProfiles()、getDefaultProfiles()
            匹配 : acceptsProfiles(String... profiles)、acceptsProfiles(Profiles profiles)
        注解 : org.springframework.context.annotation.Profile

Spring 4 重构 @Profile
    基于 Spring 4 org.springframework.context.annotation.Condition 接口实现
        org.springframework.context.annotation.ProfileCondition

依赖注入Environment
    直接依赖注入
        通过org.springframework.context.EnvironmentAware接口回调
        通过@Autowired注入Environment
    间接依赖注入
        通过org.springframework.context.ApplicationContextAware接口回调
        通过@Autowired注入ApplicationCOntextAware

依赖查找Environment
    直接依赖查找
        通过org.springframework.context.ConfigurableApplicationContext.ENVIRONMENT_BEAN_NAME
    间接依赖查找
        通过org.springframework.context.ConfigurableApplicationContext.getEnvironment

依赖注入@Value
    通过注入@Value
        实现 -
            org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor

Spring 类型转换在Environment中的运用
    Environment底层实现
        底层实现 - org.springframework.core.env.PropertySourcesPropertyResolver
            核心方法 - convertValueIfNecessary(Object value, @Nullable Class<T> targetType)
        底层服务 - org.springframework.core.convert.ConversionService
            默认实现 - org.springframework.core.convert.support.DefaultConversionService

Spring 类型转换在@Value中的运用
    @Value底层实现
        底层实现 - org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor
            org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency
        底层服务 - org.springframework.beans.TypeConverter
            默认实现 : org.springframework.beans.TypeConverterDelegate
                java.beans.PropertyEditor
                org.springframework.core.convert.ConversionService

Spring 配置属性源 PropertySource
    API
        单配置属性源 - org.springframework.core.env.PropertySource
        多配置属性源 = org.springframework.core.env.PropertySources
    注解
        单配置属性源 - @org.springframework.context.annotation.PropertySource
        多配置属性源 - @org.springframework.context.annotation.PropertySources
    关联
        存储对象 - org.springframework.core.env.MutablePropertySources
        关联方法 - org.springframework.core.env.ConfigurableEnvironment#getPropertySources()

Spring 内建的配置属性源
    内建PropertySource
    PropertySource类型                                         说明
org.springframework.core.env.CommandLinePropertySource        命令行配置属性源
org.springframework.jndi.JndiPropertySource                   jndi配置属性源
org.springframework.core.env.PropertiesPropertySource         Properties配置属性源
org.springframework.web.context.support.ServletConfigPropertySource     Servlet配置属性源
org.springframework.core.env.SystemEnvironmentPropertySource    系统环境变量配置属性源

基于注解扩展Spring属性配置源
    @org.springframework.context.annotation.PropertySource实现原理
        入口 - org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass()
            org.springframework.context.annotation.ConfigurationClassParser#processPropertySource()
    4.3新增语义
        配置属性字符编码 - encoding
        org.springframework.core.io.support.PropertySourceFactory
    适配对象 - org.springframework.core.env.CompositePropertySource

基于API扩展Spring配置属性源
    Spring应用上下文启动前装配PropertySource
    Spring应用上下文启动后装配PropertySource

Spring 4.1 测试配置属性源 - @org.springframework.test.context.TestPropertySource

简单介绍Spring Environment接口?
    核心接口 - org.springframework.core.env.Environment
    父接口 - org.springframework.core.env.PropertyResolver
    可配置接口 - org.springframework.core.env.ConfigurableEnvironment
    职责:
        管理Spring配置属性源
        管理Profiles

如何控制PropertySource优先级?


Environment 完整生命周期？
























