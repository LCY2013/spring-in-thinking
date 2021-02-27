Spring 数据绑定
    1. Spring 数据绑定使用场景
    2. Spring 数据绑定组件
    3. Spring 数据绑定元数据
    4. Spring 数据绑定控制参数
    5. Spring 底层 Java Beans 替换实现
    6. BeanWrapper 的使用场景
    7. 课外资料
    8. DataBinder 数据校验

Spring 数据绑定使用场景
    • SpringBeanDefinition到Bean实例创建
    • Spring数据绑定(DataBinder)
    • SpringWeb参数绑定(WebDataBinder)

Spring 数据绑定组件
    • 标准组件
        • org.springframework.validation.DataBinder
    • Web组件
        • org.springframework.web.bind.WebDataBinder
        • org.springframework.web.bind.ServletRequestDataBinder
        • org.springframework.web.bind.support.WebRequestDataBinder
        • org.springframework.web.bind.support.WebExchangeDataBinder(since 5.0)
    DataBinder核心属性
        属性                                          说明
        target                                      关联目标 Bean
        objectName                                  目标 Bean名称
        bindingResult                               属性绑定结果
        typeConverter                               类型转换器
        conversionService                           类型转换服务
        messageCodesResolver                        校验错误文案 Code 处理器
        validators                                  关联的 Bean Validator 实例集合
    • DataBinder绑定方法
        • bind(PropertyValues):将 PropertyValues Key-Value 内容映射到关联 Bean(target)中的属性上
            • 假设 PropertyValues 中包含“name = 哥”的键值对，同时 Bean 对象 User 中存在
               name 属性，当 bind 方法执行时，User 对象中的 name 属性值将被绑定为“哥”。

Spring 数据绑定元数据
    • DataBinder元数据-PropertyValues
    特征                                      说明
    数据来源                        BeanDefinition，主要来源 XML 资源配置 BeanDefinition
    数据结构                        由一个或多个 PropertyValue 组成
    成员结构                        PropertyValue 包含属性名称，以及属性值(包括原始值、类型转换后的值)
    常见实现                        MutablePropertyValues
    Web 扩展实现                    ServletConfigPropertyValues、ServletRequestParameterPropertyValues
    相关生命周期                     InstantiationAwareBeanPostProcessor#postProcessProperties

Spring 数据绑定控制参数
    • DataBinder绑定特殊场景分析
        • 当 PropertyValues 中包含名称 x 的 PropertyValue，目标对象 B 不存在 x 属性，当 bind 方法执行时，会发生什么?
        • 当 PropertyValues 中包含名称 x 的 PropertyValue，目标对象 B 中存在 x 属性，当 bind 方法执行时， 如何避免 B 属性 x 不被绑定?
        • 当 PropertyValues 中包含名称 x.y 的 PropertyValue，目标对象 B 中存在 x 属性(嵌套 y 属性)，当 bind 方法执行时，会发生什么?
    • DataBinder绑定控制参数
    参数名称                                            说明
    ignoreUnknownFields                         是否忽略未知字段，默认值:true
    ignoreInvalidFields                         是否忽略非法字段，默认值:false
    autoGrowNestedPaths                        是否自动增加嵌套路径，默认值:true
    allowedFields                               绑定字段白名单
    disallowedFields                            绑定字段黑名单
    requiredFields                              必须绑定字段

BeanWrapper 的使用场景
    • BeanWrapper
    • Spring 底层 JavaBeans 基础设施的中心化接口
    • 通常不会直接使用，间接用于 BeanFactory 和 DataBinder
    • 提供标准 JavaBeans 分析和操作，能够单独或批量存储 Java Bean 的属性(properties)
    • 支持嵌套属性路径(nested path)
    • 实现类 org.springframework.beans.BeanWrapperImpl

Spring 底层 Java Beans 替换实现
    • JavaBeans核心实现-java.beans.BeanInfo
        • 属性(Property)
            • java.beans.PropertyEditor • 方法(Method)
        • 事件(Event)
        • 表达式(Expression)
    • Spring替代实现-org.springframework.beans.BeanWrapper
        • 属性(Property)
            • java.beans.PropertyEditor
        • 嵌套属性路径(nested path)

• 标准JavaBeans是如何操作属性的?
    API                                             说明
    java.beans.Introspector                 JavaBeans 内省 API
    java.beans.BeanInfo                     JavaBeans 元信息 API
    java.beans.BeanDescriptor               JavaBeans 信息描述符
    java.beans.PropertyDescriptor           JavaBeans 属性描述符
    java.beans.MethodDescriptor             JavaBeans 方法描述符
    java.beans.EventSetDescriptor           JavaBeans 事件集合描述符

DataBinder 数据校验
    • DataBinder与BeanWrapper
        • bind 方法生成 BeanPropertyBindingResult
        • BeanPropertyBindingResult 关联 BeanWrapper

Spring 数据绑定 API 是什么
    答:org.springframework.validation.DataBinder

BeanWrapper 与 JavaBeans 之间关系是
    答:Spring 底层 JavaBeans 基础设施的中心化接口

DataBinder 是怎么完成属性类型转换的


























