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

第二种:
```
基于动态配置映射服务路由

基于服务发现机制的系统自动映射非常方便，但也有明显的局限性。在日常开发过程中，往往对服务映射关系有更多的定制化需求，比方说不使用默认的服务名称来命名目标服务，或者在各个请求路径之前加一个统一的前缀（Prefix）等。Zuul 充分考虑到了这些需求，开发和运维人员可以通过配置实现服务路由的灵活映射。

首先可以在 zuul-server 工程下的 application.yml 配置文件中为 user-service 配置特定的服务名称与请求地址之间的映射关系，如下所示：
zuul:
 routes:
  user-service: /user/**

相当于将/user/** 路径下的都映射到user-service实例中，而它本身的/user-service/** 也会映射到user-service实例中。

将原来的自动映射的路径记录变成了两条:
1、一个是系统自动映射的路由
2、一个是通过配置所生成的路由

如果不希望系统自动映射的路由被外部使用时，就可以通过 ignored-services 配置项把它们从服务路由中去掉
zuul:
  routes:
    user-service: /user/**
  ignored-services: 'user-service'

在一个大型的微服务架构中，可能会有非常多的微服务。这就需要对这些服务进行全局性的规划，可以通过模块或子系统的方式进行管理。
表现在路由信息上，在各个服务请求地址上添加一个前缀用来标识模块和子系统是一项最佳实践。
针对这种场景，就可以用到 Zuul 提供的“prefix”配置项：
zuul:
  routes:
    user-service: /user/**
  prefix: /health
  ignored-services: 'user-service'
将“prefix”配置项设置为“/health”，代表所有配置的路由请求地址之前都会自动添加“/health”前缀，用来识别这些请求的都属于 health 微服务系统。
```

第三种:
```
基于静态配置映射服务路由

在绝大多数场景下，通过注册中心映射服务路由是可以支持日常的开发工作的。这也是我们通常推荐的实现方法。但是 Zuul 还提供了不依赖于 Eureka 的服务路由方式，这种方式可以让 Zuul 具备更多的扩展性。这种方式的实现，使我们可以使用自定义的路由规则完成与其他各种第三方系统的集成。

考虑这样一个场景，如果系统中存在一个第三方服务，该服务无法注册到我们的 Eureka 注册中心，还会暴露了一个 HTTP 端点供 Health 系统进行调用。我们知道 Spring Cloud 中各个服务之间都是通过轻量级的 HTTP 协议进行交互的。而 HTTP 协议具备技术平台无关性，只要能够获取服务的 HTTP 端口地址，原则上我们就可以进行远程访问，而不用关注该服务在实现上采用了何种技术和工具。因此，针对这一场景，我们可以在配置文件中添加如下的静态路由配置，这样访问“/thirdpartyservice/**”时，Zuul 会将该请求转发到外部的第三方服务http://thirdparty.com/thirdpartyservice中。

zuul:
 routes:
     thirdpartyservice:
      path: /thirdpartyservice/**
      url: http://thirdparty.com/thirdpartyservice

Zuul 能够与 Ribbon 进行整合，而这种整合也来自手工设置静态服务路由的方式
zuul:
 routes:
  thirdpartyservice:
   path: /thirdpartyservice/**
   serviceId: thirdpartyservice
ribbon:
 eureka:
  enabled: false

thirdpartyservice:
 ribbon:
	listOfServers: http://thirdpartyservice1:8080,http://thirdpartyservice2:8080

配置了一个 thirdpartyservice 路由信息，通过“/thirdpartyservice/**”映射到 serviceId 为“thirdpartyservice”的服务中。
然后我们希望通过自定义 Ribbon 的方式来实现客户端负载均衡，这时候就需要关闭 Ribbon 与 Eureka 之间的关联。可以通过“ribbon.eureka.enabled: false”配置项完成这一目标。在不使用 Eureka 的情况下，我们需要手工指定 Ribbon 的服务列表。
“users.ribbon.listOfServers”配置项为我们提供了这方面的支持，如上面的“http://thirdpartyservice1:8080，http://thirdpartyservice2:8080”就为 Ribbon 提供了两个服务定义作为实现负载均衡的服务列表。

```














