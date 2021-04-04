### Sentinel 进阶应用场景
生产环境下通过 Nacos 实现 Sentinel 规则持久化。

> Sentinel 与Nacos整合实现规则持久化；

> 自定义资源点进行熔断保护；

> 开发友好的异常处理程序。

#### Sentinel 与 Nacos 整合实现规则持久化
细心的你肯定在前面 Sentinel的使用过程中已经发现，当微服务重启以后所有的配置规则都会丢失，其中的根源是默认微服务将 Sentinel 的规则保存在 JVM 内存中，当应用重启后 JVM 内存销毁，规则就对丢失。为了解决这个问题，就需要通过某种机制将配置好的规则进行持久化保存，同时这些规则变更后还能及时通知微服务进行变更。

Nacos 配置中心的用法，无论是配置数据的持久化特性还是配置中心主动推送的特性都是需要的，因此 Nacos 自然就成了 Sentinel 规则持久化的首选。

#### 案例准备
首先，快速构建演示工程 sentinel-sample。

1. 利用 Spring Initializr 向导创建 sentinel-sample 工程，pom.xml 增加以下三项依赖。
```text
<!-- Nacos 客户端 Starter-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<!-- Sentinel 客户端 Starter-->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
<!-- 对外暴露 Spring Boot 监控指标-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

2. 配置 Nacos 与 Sentinel 客户端。
```yaml
spring:
  application:
    name: sentinel-sample #应用名&微服务 id
  cloud:
    sentinel: #Sentinel Dashboard 通信地址
      transport:
        dashboard: 192.168.31.10:9100
      eager: true #取消控制台懒加载
    nacos: #Nacos 通信地址
      server-addr: 192.168.31.10:8848
      username: nacos
      password: nacos
  jackson:
    default-property-inclusion: non_null
server:
  port: 80
management:
  endpoints:
    web: #将所有可用的监控指标项对外暴露
      exposure: #可以访问 /actuator/sentinel进行查看Sentinel监控项
        include: '*'
logging:
  level:
    root: debug #开启 debug 是学习需要，生产改为 info 即可
```

3. 在 sentinel-sample服务中，增加 SentinelSampleController 类，用于演示运行效果。
```java
@RestController
public class SentinelSampleController {
    //演示用的业务逻辑类
    @Resource
    private SampleService sampleService;
    /**
     * 流控测试接口
     * @return
     */
    @GetMapping("/test_flow_rule")
    public ResponseObject testFlowRule(){
        //code=0 代表服务器处理成功
        return new ResponseObject("0","success!");
    }

    /**
     * 熔断测试接口
     * @return
     */
    @GetMapping("/test_degrade_rule")
    public ResponseObject testDegradeRule(){
        try {
            sampleService.createOrder();
        }catch (IllegalStateException e){
            //当 createOrder 业务处理过程中产生错误时会抛出IllegalStateException
            //IllegalStateException 是 JAVA 内置状态异常，在项目开发时可以更换为自己项目的自定义异常
            //出现错误后将异常封装为响应对象后JSON输出
            return new ResponseObject(e.getClass().getSimpleName(),e.getMessage());
        }
        return new ResponseObject("0","order created!");
    }
}
```

ResponseObject 对象封装了响应的数据。
```java
/**
 * 封装响应数据的对象
 */
public class ResponseObject {
    private String code; //结果编码，0-固定代表处理成功
    private String message;//响应消息
    private Object data;//响应附加数据（可选）
 
    public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }
    //Getter/Setter省略
}
```

4. 额外增加 SampleService 用于模拟业务逻辑，等下我们将用它讲解自定义资源点与熔断设置。
```java
/**
 * 演示用的业务逻辑类
 */
@Service
public class SampleService {
    //模拟创建订单业务
    public void createOrder(){
        try {
            //模拟处理业务逻辑需要101毫秒
            Thread.sleep(101);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("订单已创建");
    }
}
```
启动 sentinel-sample，访问http://localhost/test_flow_rule，浏览器出现code=0 的 JSON 响应，说明 sentinel-sample 服务启动成功。

#### 流控规则持久化
工程创建完成，下面将 Sentinel接入 Nacos 配置中心。

第一步，pom.xml 新增 sentinel-datasource-nacos 依赖。
```text
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```
sentinel-datasource-nacos 是 Sentinel 为 Nacos 扩展的数据源模块，允许将规则数据存储在 Nacos 配置中心，在微服务启动时利用该模块 Sentinel 会自动在 Nacos下载对应的规则数据。

第二步，在application.yml 文件中增加 Nacos下载规则，在原有的sentinel 配置下新增 datasource 选项。这里咱们暂时只对流控规则进行设置，重要配置项在代码中进行了注释。
```yaml
spring:
  application:
    name: sentinel-sample #应用名&微服务id
  cloud:
    sentinel: #Sentinel Dashboard通信地址
      transport:
        dashboard: 192.168.31.10:9100
      eager: true #取消控制台懒加载
      datasource:
        flow: #数据源名称，可以自定义
          nacos: #nacos配置中心
            #Nacos内置配置中心，因此重用即可
            server-addr: ${spring.cloud.nacos.server-addr} 
            dataId: ${spring.application.name}-flow-rules #定义流控规则data-id，完整名称为:sentinel-sample-flow-rules，在配置中心设置时data-id必须对应。
            groupId: SAMPLE_GROUP #gourpId对应配置文件分组，对应配置中心groups项
            rule-type: flow #flow固定写死，说明这个配置是流控规则
            username: nacos #nacos通信的用户名与密码
            password: nacos
    nacos: #Nacos通信地址
      server-addr: 192.168.31.10:8848
      username: nacos
      password: nacos
```
通过这一份配置，微服务在启动时就会自动从 Nacos 配置中心 SAMPLE_GROUP 分组下载 data-id 为 sentinel-sample-flow-rules 的配置信息并将其作为流控规则生效。

第三步，在 Nacos 配置中心页面，新增 data-id 为sentinel-sample-flow-rules 的配置项。

这里 data-id 与 groups 与微服务应用的配置保持对应，最核心的配置内容采用 JSON 格式进行书写，我们来分析下这段流控规则。
```json
[
    {
        "resource":"/test_flow_rule", #资源名，说明对那个URI进行流控
        "limitApp":"default",  #命名空间，默认default
        "grade":1, #类型 0-线程 1-QPS
        "count":2, #超过2个QPS限流将被限流
        "strategy":0, #限流策略: 0-直接 1-关联 2-链路
        "controlBehavior":0, #控制行为: 0-快速失败 1-WarmUp 2-排队等待
        "clusterMode":false #是否集群模式
    }
]
```
仔细观察不难发现，这些配置项与 Dashboard UI 中的选项是对应的，其实使用 DashboardUI 最终目的也是为了生成这段 JSON 数据而已，只不过通过 UI 更容易使用罢了。

[参数学习](https://github.com/alibaba/Sentinel/wiki/%E6%B5%81%E9%87%8F%E6%8E%A7%E5%88%B6)

最后，我们启动应用来验证流控效果。

访问 http://localhost/test_flow_rule，在日志中将会看到这条 Debug 信息，说明在服务启动时已向 Nacos 配置中心获取到流控规则。
```text
2021-04-04 13:54:24.525 DEBUG 70876 --- [10)-192.168.1.8] s.n.www.protocol.http.HttpURLConnection  : sun.net.www.MessageHeader@bf1a6bc15 pairs: {GET /nacos/v1/cs/configs?dataId=sentinel-sample-flow-rules&accessToken=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuYWNvcyIsImV4cCI6MTYxNzUzMzY1OX0.6YLB-FxFKYipGL29RVbg8Utbr0G9M21sUU1AzW6NJ2Y&group=sentinel-config HTTP/1.1: null}{Content-Type: application/json}{Accept-Charset: UTF-8}{Accept-Encoding: gzip}{Content-Encoding: gzip}{Client-AppName: unknown}{Client-RequestTS: 1617515664522}{Client-RequestToken: a24c22af3a93084e60458e19b1eab56c}{Client-Version: 1.3.2}{exConfigInfo: true}{RequestId: f3388e70-1734-4b8e-80f3-b021a3f9859d}{User-Agent: Java/11.0.6}{Host: 238019z50f.zicp.vip}{Accept: text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2}{Connection: keep-alive}
```










