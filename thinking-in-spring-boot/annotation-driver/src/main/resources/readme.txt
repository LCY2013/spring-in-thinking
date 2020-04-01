1、可以通过ClassPathBeanDefinitionScanner和AnnotationTypeFilter实现自定义注解实现类的识别和注册
    (示例:Dubbo @service实现，ServiceAnnotationBeanPostProcessor#registerServiceBeans)
2、classpath的规约资源/META-INF/spring.handlers
    context模块:
        http\://www.springframework.org/schema/context=org.springframework.context.config.ContextNamespaceHandler
        http\://www.springframework.org/schema/jee=org.springframework.ejb.config.JeeNamespaceHandler
        http\://www.springframework.org/schema/lang=org.springframework.scripting.config.LangNamespaceHandler
        http\://www.springframework.org/schema/task=org.springframework.scheduling.config.TaskNamespaceHandler
        http\://www.springframework.org/schema/cache=org.springframework.cache.config.CacheNamespaceHandler

