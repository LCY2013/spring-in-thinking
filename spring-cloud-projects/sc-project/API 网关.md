#### 什么是 API 网关？

在微服务架构中，API 网关（也叫服务网关）的出现有其必然性。通常，单个微服务提供的 API 粒度与客户端请求的粒度不一定完全匹配。多个服务之间通过对细粒度 API 的聚合才能满足客户端的要求。更为重要的是，网关能够起到客户端与微服务之间的隔离作用。随着业务需求的变化和时间的演进，网关背后的各个微服务的划分和实现可能需要做相应的调整和升级。这种调整和升级应该实现对客户端透明。

如果在服务调用过程中添加了一个网关层，那么所有的客户端都通过这个统一的网关接入微服务。这样一些非业务功能性需求就可以在网关层进行集中处理。这些需求中，比较常见的包括请求监控、安全管理、路由规则、日志记录、访问控制、服务适配等功能。

#### Spring Cloud 网关介绍(两种解决方案)

1、集成 Netflix 中的 Zuul 网关

2、自研 Spring Cloud Gateway

#### 构建基于Zuul的网关

引入Zuul相关maven依赖
```
<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

启动类
```java
@SpringBootApplication
@EnableZuulProxy  // 启动Zuul代理
public class ZuulApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class,args);
    }
}
```

#### 使用 Zuul 实现服务路由

对于 API 网关而言，最重要的功能就是服务路由，即通过 Zuul 访问的请求会路由并转发到对应的后端服务中。通过 Zuul 进行服务访问的 URL 通用格式如下所示：
http://zuulservice:9990/service

zuulservice 代表 Zuul 服务器的地址。而这里的 service 所对应的后端服务的确定就需要依赖位于 Zuul 中的服务路由信息。

在 Zuul 中，服务路由信息的设置可以使用以下几种常见做法：

第一种:
```
基于服务发现映射服务路由

Zuul 可以基于注册中心的服务发现机制实现自动化服务路由功能，所以使用 Zuul 实现服务路由最常见的、也最推荐的做法就是利用这种自动化的路由映射关系来确定路由信息。

从开发角度讲，系统自动映射也最简单，我们不需要做任何事情，因为在 Eureka 中已经保存了各种服务定义信息，而服务定义信息中包含了各个服务的名称，所以 Zuul 就可以把这些服务的名称与目标服务进行自动匹配，匹配的规则就是直接将目标服务映射到服务名称上。

举例: 在 user-service 的配置文件中通过以下配置项指定了该服务的名称为 userservice：
spring:
   application:
      name: user-service
通过路由访问路径 :  http://zuulservice:9990/user-service

Zuul 可以利用端口查看已经映射的服务路由配置信息:  http://zuulservice:9990/actuator/routes

```

















