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



















