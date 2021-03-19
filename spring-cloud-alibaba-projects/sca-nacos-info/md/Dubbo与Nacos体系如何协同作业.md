###  RPC 框架 Dubbo 是如何与 Spring Cloud Alibaba 协同作业的
```text
对比 RESTful 与 RPC。

介绍 Dubbo的特点。

讲解 Dubbo 与 Spring Cloud Alibaba 的整合过程。
```

#### Restful 与 RPC 的区别
在微服务定义中提道，每个小服务运行在自己的进程中，并以轻量级的机制进行通信。微服务间通信本身还有另一个轻量级的选择：以 Dubbo 为代表的 RPC通信方式。

那什么是 RPC 呢？RPC 是远程过程调用（Remote Procedure Call）的缩写形式，RPC 与 RESTful 最大的不同是，RPC 采用客户端（Client) - 服务端（Server） 的架构方式实现跨进程通信，实现的通信协议也没有统一的标准，具体实现依托于研发厂商的设计。

RESTful 通信更适合调用延时不敏感、短连接的场景。而 RPC 则拥有更好的性能，适用于长连接、低延时系统。两者本质是互补的，并不存在孰优孰劣。在微服务架构场景下，因为大多数服务都是轻量级的，同时 90%的任务通过短连接就能实现，因此马丁福勒更推荐使用 RESTful 通信。这只是因为应用场景所决定的，并不代表 RPC 比 RESTful 落后。

##### [Apache Dubbo](http://dubbo.apache.org/)

Dubbo 是典型的 RPC 框架的代表，通过客户端/服务端结构实现跨进程应用的高效二进制通信。

Apache Dubbo 提供了六大核心能力：
```text
面向接口代理的高性能 RPC 调用；

智能容错和负载均衡；

服务自动注册和发现；

高度可扩展能力；

运行期流量调度；

可视化的服务治理与运维。
```

Dubbo 架构中，包含 5 种角色：
```text
Provider：RPC服务提供者，Provider 是消息的最终处理者。

Container：容器，用于启动、停止 Provider 服务。这里的容器并非 Tomcat、Jetty 等 Web 容器，Dubbo 也并不强制要求 Provider 必须具备 Web 能力。Dubbo 的容器是指对象容器，例如 Dubbo 内置的 SpringContainer 容器就提供了利用 Spring IOC 容器管理 Provider 对象的职能。

Consumer：消费者，调用的发起者。Consumer 需要在客户端持有 Provider 的通信接口才能完成通信过程。

Registry：注册中心，Dubbo 架构中注册中心与微服务架构中的注册中心职责类似，提供了 Dubbo Provider 的注册与发现职能，Consumer通过 Registry 可以获取Provider 可用的节点实例的 IP、端口等，并产生直接通信。需要注意的是，前面我们讲解的 Alibaba Nacos 除了可以作为微服务架构中的注册中心外，同样对自家的 Dubbo 提供了 RPC 调用注册发现的职责，这是其他 Spring Cloud 注册中心所不具备的功能。

Monitor：监控器，监控器提供了Dubbo的监控职责。在 Dubbo 产生通信时，Monitor 进行收集、统计，并通过可视化 UI 界面帮助运维人员了解系统进程间的通信状况。Dubbo Monitor 主流产品有 Dubbo Admin、Dubbo Ops 等。
```

#### Dubbo与 Nacos 协同作业

##### 开发 Provider仓储服务
###### 第一步，创建工程引入依赖。

利用 Spring Initializr 向导创建 warehouse-service 工程，确保 pom.xml 引入以下依赖。
```text
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>org.apache.dubbo</groupId>
    <artifactId>dubbo-spring-boot-starter</artifactId>
    <version>2.7.8</version>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
相比标准微服务，需要额外依赖 dubbo-spring-boot-starter 与 spring-boot-starter-actuator。其中 dubbo-spring-boot-starter 是 Dubbo 与 Spring Boot 整合最关键的组件，为 Spring Boot 提供了 Dubbo 的默认支持。而 spring-boot-starter-actuator则为微服务提供了监控指标接口，便于监控系统从应用收集各项运行指标。

###### 第二步，设置微服务、Dubbo 与 Nacos 通信选项。
打开 application.yml 文件，配置 Nacos 地址与 Dubbo通信选项。
```yaml
spring:
  application:
    name: warehouse-service # 引用微服务名称，仓储服务
  cloud:
    nacos:
      discovery:
        #server-addr: 238019z50f.zicp.vip:80
        server-addr: 192.168.0.170:80
        username: nacos
        password: nacos
server:
  port: 7890
dubbo: #dubbo与nacos的通信配置
  application:
    name: warehouse-house  #provider在Nacos中的应用id
  registry: #Provider与Nacos通信地址，与spring.cloud.nacos地址一致
    address: nacos://192.168.0.170:8848
  protocol:
    name: dubbo #通信协议名
    port: 20880 #配置通信端口，默认为20880
  scan:
    base-packages: org.fufeng.sca.warehouse.dubbo
```
很多人不明白，为什么上面配置了 spring.cloud.nacos.discovery.server-addr 还要在下面配置 dubbo.registry.address 呢？前面介绍架构时介绍了，Dubbo 需要依托 Container（容器）对外暴露服务，而这个容器配置与微服务配置是分开的，需要额外占用一个网络端口20880提供服务。

这里还有一个配置点需要特别注意：dubbo.scan.base-packages 代表在 Dubbo 容器启动时自动扫描 org.fufeng.sca.warehouse.dubbo 包下的接口与实现类，并将这些接口信息在Nacos 进行登记，因此 Dubbo 对外暴露的接口必须放在该包下。

###### 第三步，开发接口与实现类
1. 开发 Stock 库存商品对象。
```java
import java.io.Serializable;
//库存商品对象
public class Stock implements Serializable {
    private Long skuId; //商品品类编号
    private String title; //商品与品类名称
    private Integer quantity; //库存数量
    private String unit; //单位
    private String description; //描述信息
    //带参构造函数
    public Stock(Long skuId, String title, Integer quantity, String unit) {
        this.skuId = skuId;
        this.title = title;
        this.quantity = quantity;
        this.unit = unit;
    }
    //getter 与 setter...
}
```
注意：Dubbo 在对象传输过程中使用了 JDK 序列化，对象必须实现 Serializable 接口。

2. 在 org.fufeng.sca.warehouse.dubbo包下创建 WarehouseService 接口并声明方法。包名要与 dubbo.scan.base-packages 保持一致。
```java
public interface WarehouseService {
    /**
     * 查询库存
     *
     * @param skuId 商品类目ID
     * @return
     */
    public Stock getStock(Long skuId);
}
```
3. 在 org.fufeng.sca.warehouse.dubbo.impl 包下创建实现类 WarehouseServiceImpl。
```java
@DubboService
public class WarehouseServiceImpl implements WarehouseService {

    @Override
    public Stock getStock(Long skuId) {
        Stock stock = null;
        if(skuId == 1101L){
            //模拟有库存商品
            stock = new Stock(1101L, "Apple iPhone 11 128GB 紫色", 32, "台");
            stock.setDescription("Apple 11 紫色版对应商品描述");
        }else if(skuId == 1102L){
            //模拟无库存商品
            stock = new Stock(1102L, "Apple iPhone 11 256GB 白色", 0, "台");
            stock.setDescription("Apple 11 白色版对应商品描述");
        }else{
            //演示案例，暂不考虑无对应skuId的情况
        }
        return stock;
    }

}
```
实现逻辑非常简单，不做赘述，重点是在实现类上需要额外增加 @DubboService注解。@DubboService 是 Provider 注解，说明该类所有方法都是服务提供者，加入该注解会自动将类与方法的信息在 Nacos中注册为 Provider。

###### 第四步，启动微服务，验证 Nacos 注册信息
启动 WarehouseServiceApplication.main()，之后打开下面网址访问 Nacos 服务列表。

此时在服务列表中出现了 2 条数据，warehouse-service 是仓储微服务的注册信息，providers 开头的是 Dubbo 在 Nacos 的注册 Provider 信息，这也验证了前面介绍 Dubbo 的注册过程。

而查看 Provider详情后，你会得到更多信息，其中包含 Provider 的 IP、端口、接口、方法、pid、版本的明细，方便开发、运维人员对应用进行管理。

##### 开发 Consumer 订单服务























