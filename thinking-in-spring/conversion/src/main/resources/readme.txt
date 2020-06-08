Spring 类型转换
    1. Spring 类型转换的实现
    2. 使用场景
    3. 基于 JavaBeans 接口的类型转换
    4. Spring 內建 PropertyEditor 扩展
    5. 自定义 PropertyEditor 扩展
    6. Spring PropertyEditor 的设计缺陷
    7. Spring 3.0 通用类型转换接口
    8. Spring 內建类型转换器
    9. Converter 接口的局限性
    10. GenericConverter 接口
    11. 优化 GenericConverter 接口 12. 扩展 Spring 类型转换器
    13. 统一类型转换服务
    14. ConversionService 作为依赖

Spring 类型转换的实现
    • 基于JavaBeans接口的类型转换实现
        • 基于 java.beans.PropertyEditor 接口扩展
    • Spring3.0+通用类型转换实现

使用场景
    场景                  基于 JavaBeans 接口的类型转换实现       Spring 3.0+ 通用类型转换实现
    数据绑定                     YES                                 YES
    BeanWrapper                 YES                                 YES
    Bean 属性类型装换             YES                                 YES
    外部化属性类型转换              NO                                  YES

基于 JavaBeans 接口的类型转换
    • 核心职责
        • 将 String 类型的内容转化为目标类型的对象
    • 扩展原理
        • Spring 框架将文本内容传递到 PropertyEditor 实现的 setAsText(String) 方法
        • PropertyEditor#setAsText(String) 方法实现将 String 类型转化为目标类型的对象
        • 将目标类型的对象传入 PropertyEditor#setValue(Object) 方法
        • PropertyEditor#setValue(Object) 方法实现需要临时存储传入对象
        • Spring 框架将通过 PropertyEditor#getValue() 获取类型转换后的对象






















