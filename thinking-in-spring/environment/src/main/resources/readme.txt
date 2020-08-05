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


















