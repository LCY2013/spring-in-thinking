Request Bean作用域
    配置
        XML - <bean class="" scope="request"/>
        java注解 - @RequestScope或@Scope(WebApplicationContext.SCOPE_REQUEST)
    实现
        API - org.springframework.web.context.request.RequestScope

Session Bean作用域
    配置
        XML - <bean class="" scope="session"/>
        java注解 - @SessionScope或@Scope(WebApplicationContext.SCOPE_SESSION)
    实现
        API - org.springframework.web.context.request.SessionScope

Application Bean作用域
    配置
        XML - <bean class="" scope="application"/>
        java注解 - @ApplicationScope或@Scope(WebApplicationContext.SCOPE_APPLICATION)
    实现
        API - org.springframework.web.context.support.ServletContextScope





执行远程debug
    jdk9->
    java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8864  *.jar
    ->jdk8
    java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8864 *.jar













