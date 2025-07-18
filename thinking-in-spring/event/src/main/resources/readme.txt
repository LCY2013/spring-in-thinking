Spring 事件
    1. Java 事件/监听器编程模型
    2. 面向接口的事件/监听器设计模式
    3. 面向注解的事件/监听器设计模式
    4. Spring 标准事件-ApplicationEvent
    5. 基于接口的 Spring 事件监听器
    6. 基于注解的 Spring 事件监听器
    7. 注册 Spring ApplicationListener
    8. Spring 事件发布器
    9. Spring 层次性上下文事件传播
    10. Spring 内建事件
    11. Spring 4.2 Payload 事件
    12. 自定义 Spring 事件
    13. 依赖注入 ApplicationEventPublisher
    14. 依赖查找 ApplicationEventMulticaster
    15. ApplicationEventPublisher 底层实现
    16. 同步和异步 Spring 事件广播
    17. Spring 4.1 事件异常处理
    18. Spring 事件/监听器实现原理

Java 事件/监听器编程模型
    • 设计模式-观察者模式扩展
        • 可观者对象(消息发送者) - java.util.Observable
        • 观察者 - java.util.Observer
    • 标准化接口
        • 事件对象 - java.util.EventObject
        • 事件监听器 - java.util.EventListener

面向接口的事件/监听器设计模式
    • 事件/监听器场景举例
    Java 技术规范        事件接口                               监听器接口
    JavaBeans       java.beans.PropertyChangeEvent          java.beans.PropertyChangeListener
    Java AWT        java.awt.event.MouseEvent               java.awt.event.MouseListener
    Java Swing      javax.swing.event.MenuEvent             javax.swing.event.MenuListener
    Java Preference java.util.prefs.PreferenceChangeEvent   java.util.prefs.PreferenceChangeListener

面向注解的事件/监听器设计模式
    • 事件/监听器注解场景举例
    Java 技术规范                   事件注解                    监听器注解
    Servlet 3.0+                                 @javax.servlet.annotation.WebListener
    JPA 1.0+        @javax.persistence.PostPersist
    Java Common     @PostConstruct
    EJB 3.0+        @javax.ejb.PrePassivate
    JSF 2.0+        @javax.faces.event.ListenerFor

Spring 标准事件 - ApplicationEvent
    • Java标准事件java.util.EventObject扩展
        • 扩展特性:事件发生事件戳
    • Spring应用上下文ApplicationEvent扩展-ApplicationContextEvent
        • Spring 应用上下文(ApplicationContext)作为事件源
        • 具体实现:
            • org.springframework.context.event.ContextClosedEvent
            • org.springframework.context.event.ContextRefreshedEvent
            • org.springframework.context.event.ContextStartedEvent
            • org.springframework.context.event.ContextStoppedEvent

基于接口的 Spring 事件监听器
    • Java标准事件监听器java.util.EventListener扩展
        • 扩展接口 - org.springframework.context.ApplicationListener
        • 设计特点:单一类型事件处理
        • 处理方法:onApplicationEvent(ApplicationEvent)
        • 事件类型:org.springframework.context.ApplicationEvent

基于注解的 Spring 事件监听器
    • Spring注解-@org.springframework.context.event.EventListener
    特性                      说明
    设计特点            支持多 ApplicationEvent 类型，无需接口约束
    注解目标                    方法
    是否支持异步执行              支持
    是否支持泛型类型事件           支持
    是指支持顺序控制          支持，配合 @Order 注解控制

注册 Spring ApplicationListener
    • 方法一:ApplicationListener作为SpringBean注册
    • 方法二:通过ConfigurableApplicationContextAPI注册

Spring 事件发布器
    • 方法一:通过ApplicationEventPublisher发布Spring事件
        • 获取 ApplicationEventPublisher
            • 依赖注入
    • 方法二:通过ApplicationEventMulticaster发布Spring事件
        • 获取 ApplicationEventMulticaster
            • 依赖注入
            • 依赖查找

Spring 层次性上下文事件传播
    • 发生说明
        • 当 Spring 应用出现多层次 Spring 应用上下文(ApplicationContext)时，
            如 Spring WebMVC、Spring Boot 或 Spring Cloud 场景下，
            由子 ApplicationContext 发起Spring 事件可能会传递到其
            Parent ApplicationContext(直到 Root)的过程
    • 如何避免
        • 定位 Spring 事件源(ApplicationContext)进行过滤处理

Spring 内建事件
    • ApplicationContextEvent派生事件
        • ContextRefreshedEvent :Spring 应用上下文就绪事件
        • ContextStartedEvent :Spring 应用上下文启动事件
        • ContextStoppedEvent :Spring 应用上下文停止事件
        • ContextClosedEvent :Spring 应用上下文关闭事件

Spring 4.2 Payload 事件
    • SpringPayload事件-org.springframework.context.PayloadApplicationEvent
        • 使用场景:简化 Spring 事件发送，关注事件源主体
        • 发送方法
            • ApplicationEventPublisher#publishEvent(java.lang.Object)

自定义 Spring 事件
    • 扩展org.springframework.context.ApplicationEvent
    • 实现org.springframework.context.ApplicationListener
    • 注册org.springframework.context.ApplicationListener

依赖注入 ApplicationEventPublisher
    • 通过ApplicationEventPublisherAware回调接口
    • 通过ApplicationContextAware回调接口
    • 通过@Autowired 注入上面两个接口的参数值

依赖查找 ApplicationEventMulticaster
    • 查找条件
        • Bean名称:"applicationEventMulticaster"
        • Bean类型:org.springframework.context.event.ApplicationEventMulticaster

ApplicationEventPublisher 底层实现
    • 底层实现
        • 接口:org.springframework.context.event.ApplicationEventMulticaster
        • 抽象类:org.springframework.context.event.AbstractApplicationEventMulticaster
        • 实现:org.springframework.context.event.SimpleApplicationEventMulticaster

同步和异步Spring事件广播
    • 基于实现类 - org.springframework.context.event.SimpleApplicationEventMulticaster
        • 模式切换: org.springframework.context.event.SimpleApplicationEventMulticaster.setTaskExecutor(Executor taskExecutor)方法
            • 默认模式: 同步
            • 异步模式: 如java.util.concurrent.ThreadPoolExecutor
        • 设计缺陷: 非基于接口契约编程
    • 基于注解 - @org.springframework.context.event.EventListener
        • 模式切换
            • 默认模式: 同步
            • 异步模式: 标注@org.springframework.scheduling.annotation.Async
        • 实现限制: 无法直接实现同步/异步动态切换

Spring 4.1事件异常处理
    • Spring 3.0 错误处理接口 - org.springframework.util.ErrorHandler
        • 使用场景
            • Spring 事件 (Events)
                • SimpleApplicationEventMulticaster Spring 4.1 开始支持
            • Spring 本地调度 (Scheduling)
                • org.springframework.scheduling.concurrent.ConcurrentTaskExecutor
                • org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

Spring 事件/监听器实现原理
    • 核心类 - org.springframework.context.event.SimpleApplicationEventMulticaster
        • 设计模式 : 观察者模式扩展
            • 被观察者 - org.springframework.context.ApplicationListener
                • API 添加
                • 依赖查找
            • 通知对象 - org.springframework.context.ApplicationEvent
        • 执行方式 : 同步/异步
        • 异常处理 : org.springframework.util.ErrorHandler
        • 泛型处理 : org.springframework.core.ResolvableType

Spring boot 事件
    事件类型                                发生时机
    ApplicationStartingEvent             spring boot应用启动时
    ApplicationStartedEvent              spring boot应用已经启动时
    ApplicationEnvironmentPreparedEvent  spring boot Environment实例已经准备就绪
    ApplicationPreparedEvent             spring boot 应用预准备时
    ApplicationReadyEvent                spring boot 应用准备就绪时
    ApplicationFailedEvent               spring boot 应用启动失败时

Spring cloud 2+事件
    事件类型                                发生时机
    EnvironmentChangeEvent              当Environment 示例配置属性发生变化时
    HeartbeatEvent                      当DiscoveryClient客户端发生心跳时
    InstancePreRegisteredEvent          当实例服务注册前时
    InstanceRegisteredEvent             当实例服务注册后时
    RefreshEvent                        当RefreshEndpoint断点被调用时
    RefreshScopeRefreshedEvent          当Refresh scope bean 被刷新时

spring 事件核心接口/组件？
    1、spring 事件 - org.springframework.context.ApplicationEvent
    2、spring 事件监听器 - org.springframework.context.ApplicationListener
    3、spring 事件发布器 - org.springframework.context.ApplicationEventPublisher
    4、spring 事件广播器 - org.springframework.context.event.ApplicationEventMulticaster

spring 同步/异步事件处理使用场景？
    spring 同步事件 - 绝大多数spring使用场景，如: ContextRefreshedEvent
    spring 异步事件 - 主要@EventListener与@Async配合，实现异步处理，不阻塞主线程，比如长时间的数据计算任务，io等、
            不要轻易调整SimpleApplicationEventMulticaster中的taskExecutor对象，除非非常了解spring事件机制，否则容易出现异常行为

spring @EventListener 工作原理?


