Spring 配置元信息
    1、Spring 配置元信息
    2、Spring Bean 配置元信息
    3、Spring Bean 属性元信息
    4、Spring 容器配置元信息
    5、基于XML文件装配Spring Bean配置元信息
    6、基于Properties文件装配Spring Bean配置元信息
    7、基于Java注解装配Spring Bean配置元信息
    8、Spring Bean 配置元信息底层实现
    9、基于XML文件装配Spring IOC容器配置元信息
    10、基于Properties文件装配Spring IOC容器配置元信息
    11、基于Extensible XML authoring 扩展Spring XML元素
    12、Extensible XML authoring 扩展原理
    13、基于Properties文件装载外部化配置
    14、基于yaml文件装载外部化配置

Spring 配置元信息
    配置元信息
        Spring Bean 配置元信息 - BeanDefinition
        Spring Bean 属性元信息 - PropertyValues
        Spring容器配置元信息
        Spring 外部化配置元信息 - PropertySource
        Spring Profile 元信息 - @Profile

Spring Bean 配置元信息
    Bean 配置元信息 - BeanDefinition
        1、GenericBeanDefinition : 通用配置元信息
        2、RootBeanDefinition : 特点(无parent BeanDefinition)，
                所有BeanDefinition合并后都会变成RootBeanDefinition
        3、AnnotatedBeanDefinition : 通过注解标注的BeanDefinition

Spring Bean 属性元信息
    Bean 属性元信息 - PropertyValues
        可修改实现 : MutablePropertyValues
        元素成员 : PropertyValue
    Bean 属性上下文存储 - AttributeAccessor
    Bean 元信息元素 - BeanMetadataElement

Spring 容器配置元信息
    Spring XML 配置元信息 - Beans 元素相关   (BeanDefinitionParserDelegate)
    beans元素属性                   默认值         使用场景
    profile                        null          Spring Profile配置值
    default-lazy-init              default       当outter beans "default-lazy-init"属性存在就继承改值,否则为"false"
    default-merge                  default       当outter beans "default-merge"属性存在就继承改值,否则为"false"
    default-autowire               default       当outter beans "default-autowire"属性存在就继承改值,否则为"false"
    default-autowire-candidates    null          默认Spring Beans 名称pattern
    default-init-method            null          默认Spring Beans 自定义初始化方法
    default-destroy                null          默认Spring Beans 自定义销毁方法

    Spring XML 配置元信息 - 应用上下文相关  (ContextNamespaceHandler)  - 配置规约(spring.handlers)
    XML元素                               使用场景
    <context:annotation-config/>          激活Spring 注解驱动
    <context:annotation-scan/>            Spring @Component 以及自定义注解扫描
    <context:load-time-weaver/>           激活SpringLoadTimeWeaver
    <context:mbean-export/>               暴露Spring Beans 作为JMX Beans
    <context:mbean-server/>               将当前平台作为MBeanServer
    <context:property-placeholder/>       加载外部化配置资源作为Spring属性配置
    <context:property-override/>          利用外部化配置资源覆盖Spring属性值

    基于XML资源装载Spring Bean配置元信息
        Spring Bean 配置元信息 (XmlBeanDefinitionReader)
        XML元素                   使用场景
        <beans:beans/>            单XML资源下的多个Spring Beans配置
        <beans:bean/>             单个Spring Bean定义(BeanDefinition)配置
        <beans:alias/>            为Spring Bean定义(BeanDefinition)定义别名
        <beans:import/>           加载外部Spring XML配置资源

    基于Properties 资源装载 Spring Bean配置元信息
        Spring Bean 配置元信息   (PropertiesBeanDefinitionReader)
        Properties属性名称              使用场景
        (class)                        Bean类全称限定名
        (abstract)                     是否是抽象类的BeanDefinition
        (parent)                       指定parent BeanDefinition名称
        (lazy-init)                    是否延迟初始化
        (ref)                          引用其他Bean的名称
        (scope)                        设置Bean的scope属性
        ${n}                            n表示第n+1个构造器参数

    基于Java注解装载Spring Bean配置元信息
        Spring注解            场景说明                        起始版本
      Spring 模式注解
        @Repository          数据仓储模式注解                   2.0
        @Component           通用组件模式注解                   2.5
        @Service             服务模式注解                     2.5
        @Controller          Web控制器模式注解                 2.5
        @Configuration       配置类模式注解                    3.0
      Spring Bean 依赖注入注解
        @Autowired           Bean依赖注入,主持所有的依赖查找      2.5
        @Qualifier           细粒度的@Autowired依赖查找         2.5
      JSR规约(Java Specification Requests)
        @Resource            类似@Autowired                   2.5   JSR-250 Annotation
        @Inject              类似@Autowired                   2.5   JSR-330 Annotation
      Spring Bean 条件注解
        @Profile             配置化条件装配                     3.1
        @Conditional         变成条件装配                       4.0   ConditionEvaluator
      Spring Bean 生命周期回调注解
        @PostConstruct       替换< init-method=""/>            2.5
                             和InitializingBean
        @PreDestroy          替换< destroy-method=""/>         2.5
                             和DisposableBean

Spring Bean 配置元信息底层实现
    Spring BeanDefinition 解析与注册
        实现场景                实现类                     起始版本
        XML资源           XmlBeanDefinitionReader         1.0
        Properties资源    PropertiesBeanDefinitionReader  1.0
        Java 注解         AnnotatedBeanDefinitionReader   3.0

    Spring XML 资源BeanDefinition解析与注册
        核心API   -   XmlBeanDefinitionReader
            资源  - Resource
            底层  - BeanDefinitionDocumentReader
                    XML解析  -  Java DOM Level 3 API
                    BeanDefinition解析 - BeanDefinitionParserDelegate
                    BeanDefinition注册 - BeanDefinitionRegistry

    Spring Properties   资源BeanDefinition解析与注册
        核心API   -   PropertiesBeanDefinitionReader
            资源
                字节流 - Resource
                字符流 - EncodeResource
            底层
                存储 - java.util.Properties
                BeanDefinition解析    -   API内部实现
                BeanDefinition注册    -   BeanDefinitionRegistry
    Spring Java 注解 BeanDefinition解析与注册
        核心API   -   AnnotatedBeanDefinitionReader
            资源
                类对象 - java.lang.Class
            底层
                条件评估 - ConditionEvaluator
                Bean范围解析 - ScopeMetadataResolver
                BeanDefinition解析 - 内部API实现
                BeanDefinition处理 - AnnotationConfigUtils.processCommonDefinitionAnnotations
                BeanDefinition注册 - BeanDefinitionRegistry

基于XML资源装载Spring IOC容器配置元信息
    Spring IOC 容器相关XML配置
        命名空间        所属模块            Schema资源URL
        beans        spring-beans       https://www.springframework.org/schema/beans/spring-beans.xsd
        context      spring-context     https://www.springframework.org/schema/context/spring-context.xsd
        aop          spring-aop         https://www.springframework.org/schema/aop/spring-aop.xsd
        tx           spring-tx          https://www.springframework.org/schema/tx/spring-tx.xsd
        util         spring-beans       https://www.springframework.org/schema/util/spring-util.xsd
        tool         spring-beans       https://www.springframework.org/schema/tool/spring-tool.xsd

基于Java注解装载Spring IOC容器配置元信息
    Spring IOC 容器装配注解
        Spring注解            场景说明                                起始版本
        @ImportResource      替换XML配置中的<import/>                 3.0
        @Import              导入Configuration class                 3.0
        @ComponentScan       扫描指定package下标注Spring模式注解的类     3.1
    Spring IOC 配置属性注解
        Spring注解            场景说明                                起始版本
        @PropertySource      配置属性抽象PropertySource注解            3.1
        @PropertySources     @PropertySource集合注解                  4.0

基于Extensible XML authoring 扩展 Spring XML元素
    Spring XML扩展
        编写XML Schema文件: 定义XML结构
        自定义NamespaceHandler实现: 命名空间绑定
        自定义BeanDefinitionParser实现: XML元素于BeanDefinition解析
        注册XML扩展: 命名空间于XML Schema映射

    Extensible XML authoring 原理
        AbstractApplicationContext#obtainFreshBeanFactory
            -> AbstractRefreshableApplicationContext#refreshBeanFactory
                -> AbstractXmlApplicationContext#loadBeanDefinitions
                    -> ...
                        -> XmlBeanDefinitionReader#doLoadBeanDefinitions
                            -> ...
                                -> BeanDefinitionParserDelegate.parseCustomElement
        核心流程
            BeanDefinitionParserDelegate.parseCustomElement
                获取namespace
                通过namespace得到NamespaceHandler
                构造ParserContext
                解析元素 获得BeanDefinition

基于Properties资源装载外部化配置
    注解驱动
        @PropertySource
        @PropertySources
    API编程
        PropertySource
        PropertySources

基于YAML资源装载外部化配置
    API编程
        org.springframework.beans.factory.config.YamlProcessor
            -> org.springframework.beans.factory.config.YamlMapFactoryBean
            -> org.springframework.beans.factory.config.YamlPropertiesFactoryBean

面试
    Spring内建的XML Schema常用规约有那些？
    答:  beans        spring-beans       https://www.springframework.org/schema/beans/spring-beans.xsd
        context      spring-context     https://www.springframework.org/schema/context/spring-context.xsd
        aop          spring-aop         https://www.springframework.org/schema/aop/spring-aop.xsd
        tx           spring-tx          https://www.springframework.org/schema/tx/spring-tx.xsd
        util         spring-beans       https://www.springframework.org/schema/util/spring-util.xsd
        tool         spring-beans       https://www.springframework.org/schema/tool/spring-tool.xsd

    Spring 配置元信息具体有哪些内容?
    答:  Bean配置元信息 -> 通过媒介(XML,properties 等)，解析成BeanDefinition
        IOC容器配置元信息  -> 通过媒介(XML,properties 等),控制IOC容器行为，如：注解驱动，AOP等。
        外部化配置 -> 通过资源抽象(properties，yaml 等)，控制propertySource
        spring profile -> 通过外部化配置，提供条件分支流程

    Extensible XML authoring缺点?
    答: 高复杂度: 开发人员需要熟悉XML Schema规约，还需要熟悉Spring.handlers,Spring.schemas以及spring 其他API
        嵌套元素支持弱: 通常需要使用方法递归或者嵌套解析的方法处理嵌套子元素
        XML处理性能差:  Spring XMl基于DOM Level 3 API实现，该API便于理解，但是性能差
        XML框架移植性差: 很难适配高性能和便利性的XML框架，如JAXB














