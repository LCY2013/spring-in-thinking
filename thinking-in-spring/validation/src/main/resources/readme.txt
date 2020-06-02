Spring 校验
    1. Spring 校验使用场景
    2. Validator 接口设计
    3. Errors 接口设计
    4. Errors 文案来源
    5. 自定义 Validator
    6. Validator 的救赎

Spring 校验使用场景
    • Spring常规校验(Validator)
    • Spring数据绑定(DataBinder)
    • SpringWeb参数绑定(WebDataBinder)
    • SpringWebMVC/SpringWebFlux处理方法参数校验

Validator 接口设计
    • 接口职责
        • Spring 内部校验器接口，通过编程的方式校验目标对象
    • 核心方法
        • supports(Class):校验目标类能否校验
        • validate(Object,Errors):校验目标对象，并将校验失败的内容输出至 Errors 对象 • 配套组件
    • 错误收集器:org.springframework.validation.Errors
        • Validator 工具类:org.springframework.validation.ValidationUtils
    • 接口职责
        • 数据绑定和校验错误收集接口，与 Java Bean 和其属性有强关联性
    • 核心方法
        • reject 方法(重载):收集错误文案
        • rejectValue 方法(重载):收集对象字段中的错误文案
    • 配套组件
        • Java Bean 错误描述:org.springframework.validation.ObjectError
        • Java Bean 属性错误描述:org.springframework.validation.FieldError

Errors 文案来源
    • Errors文案生成步骤
        • 选择 Errors 实现(如:org.springframework.validation.BeanPropertyBindingResult)
        • 调用 reject 或 rejectValue 方法
        • 获取 Errors 对象中 ObjectError 或 FieldError
        • 将 ObjectError 或 FieldError 中的 code 和 args，关联 MessageSource 实现(如: ResourceBundleMessageSource)

自定义 Validator
    • 实现org.springframework.validation.Validator接口
        • 实现 supports 方法
        • 实现 validate 方法
            • 通过 Errors 对象收集错误
                • ObjectError:对象(Bean)错误:
                • FieldError:对象(Bean)属性(Property)错误
            • 通过 ObjectError 和 FieldError 关联 MessageSource 实现获取最终文案




















