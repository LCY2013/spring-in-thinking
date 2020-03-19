Spring BeanDefinition作为依赖来源
    元数据: BeanDefinition
    注册: BeanDefinitionRegistry#registerBeanDefinition()
    类型: 延迟和非延迟
    顺序: Bean生命周期顺序按照注册顺序
    获取: 依赖查找,依赖注入
单例对象作为依赖来源
    要素:
        来源: 外部JAVA对象(不一定是POJO),可以是new 对象
        注册: SingletonBeanRegistry#registerSingleton()
    限制:
        无生命周期管理
        无法定义BeanDefinition中的信息,如:无法实现延迟初始化Bean等定义
非Spring容器管理Bean对象作为依赖来源
    要素:
        注册: ConfigurableListableBeanFactory#registerResolvableDependency()
    限制:
        无生命周期管理
        无法定义BeanDefinition中的信息,如:无法实现延迟初始化Bean等定义
        无法通过依赖查找
外部化配置作为依赖来源
    要素:
        类型: 非常规Spring对象依赖来源
    限制:
        无生命周期管理
        无法定义BeanDefinition中的信息,如:无法实现延迟初始化Bean等定义
        无法通过依赖查找
依赖查找和依赖注入来源是否相同？
    不同
        依赖查找的来源是BeanDefinition定义的Bean和SingletonBeanRegistry注册的单例Bean对象
        依赖注入的来源除了BeanDefinition定义的Bean和SingletonBeanRegistry注册的单例Bean对象,还有
            registerResolvableDependency()注册的非Spring管理的Bean,以及@Value注解的外部化配置注入
单例对象是否可以在Spring容器启动后注册？
    可以的，主要有两种
        1、通过单例对象注册
        2、通过BeanDefinition注册
    不同点:
        BeanDefinition会被ConfigurableListableBeanFactory#freezeConfiguration()方法影响，
        而冻结注册，单例对象就不受影响
Spring依赖注入来源?
    1、通过BeanDefinition注册的对象
    2、通过单例对象直接注册的对象
    3、通过Resolvable Dependency的非Spring容器管理的Bean
    4、外部化配置,通过@Value注入













