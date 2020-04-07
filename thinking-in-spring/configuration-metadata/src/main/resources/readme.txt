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














