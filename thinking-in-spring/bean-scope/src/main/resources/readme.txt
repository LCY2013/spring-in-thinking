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

自定义Bean作用域
    实现Scope - org.springframework.beans.factory.config.Scope
    注册Scope - org.springframework.beans.factory.config.ConfigurableBeanFactory
        配置:
         <bean class="org.springframework.beans.factory.config.CustomScopeConfigurer">
            <property name="scopes">
                <map>
                    <entry key="..."></entry>
                </map>
            </property>
         </bean>


Spring中Bean的Scope有哪些？
 singleton,prototype,request,session,application,websocket

singleton Bean在应用中是否是唯一的？
 不是，singleton Bean在一个IOC容器，也就是BeanFactory中是唯一的

一个应用中的class是否唯一？
 不是，在同一个ClassLoader下是唯一的

Application 作用域的Bean是否有其他实现方案？
 可以实现，application bean其实是在servletContext作用域中存在的唯一的一个bean，
 可以看成是一个singleton bean，可以直接通过singleton bean的方式去实现









