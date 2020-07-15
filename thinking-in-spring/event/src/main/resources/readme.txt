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
    13. 依赖注入 ApplicationEventPublisher 14. 依赖查找 ApplicationEventMulticaster 15. ApplicationEventPublisher 底层实现 16. 同步和异步 Spring 事件广播
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






































