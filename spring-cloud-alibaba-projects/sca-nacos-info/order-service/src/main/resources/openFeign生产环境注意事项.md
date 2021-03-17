### 生产环境 OpenFeign 的配置事项

#### 如何更改 OpenFeign 默认的负载均衡策略
在 OpenFeign 使用时默认引用 Ribbon 实现客户端负载均衡。那如何设置 Ribbon 默认的负载均衡策略呢？

在 OpenFeign 环境下，配置方式其实与之前 Ribbon+RestTemplate 方案完全相同，只需在 application.yml 中调整微服务通信时使用的负载均衡类即可。
```yaml
warehouse-service: #服务提供者的微服务ID
  ribbon:
    #设置对应的负载均衡类
    NFLoadBalancerRuleClassName: com.netflix.loadbalancer.RandomRule 
```

#### 开启默认的 OpenFeign 数据压缩功能
在 OpenFeign 中，默认并没有开启数据压缩功能。但如果你在服务间单次传递数据超过 1K 字节，强烈推荐开启数据压缩功能。默认 OpenFeign 使用 Gzip 方式压缩数据，对于大文本通常压缩后尺寸只相当于原始数据的 10%~30%，这会极大提高带宽利用率。但有一种情况除外，如果应用属于计算密集型，CPU 负载长期超过 70%，因数据压缩、解压缩都需要 CPU 运算，开启数据压缩功能反而会给 CPU 增加额外负担，导致系统性能降低，这是不可取的。
```yaml
feign:
  compression:
    request:
      # 开启请求数据的压缩功能
      enabled: true
      # 压缩支持的MIME类型
      mime-types: text/xml,application/xml, application/json
      # 数据压缩下限 1024表示传输数据大于1024 才会进行数据压缩(最小压缩值标准)
      min-request-size: 1024
    # 开启响应数据的压缩功能
    response:
      enabled: true
```

#### 替换默认通信组件
OpenFeign 默认使用 Java 自带的 URLConnection 对象创建 HTTP 请求，但接入生产时，如果能将底层通信组件更换为 Apache HttpClient、OKHttp 这样的专用通信组件，基于这些组件自带的连接池，可以更好地对 HTTP 连接对象进行重用与管理。作为 OpenFeign 目前默认支持 Apache HttpClient 与 OKHttp 两款产品。我以OKHttp配置方式为例，为你展现配置方法。

1.引入 feign-okhttp 依赖包。
```text
<dependency>
    <groupId>io.github.openfeign</groupId>
    <artifactId>feign-okhttp</artifactId>
    <version>11.0</version>
</dependency>
```

2.在应用入口，利用 Java Config 形式初始化 OkHttpClient 对象。
```java
@SpringBootApplication
@EnableFeignClients
public class OrderServiceApplication {
    //Spring IOC容器初始化时构建okHttpClient对象
    @Bean
    public okhttp3.OkHttpClient okHttpClient(){
        return new okhttp3.OkHttpClient.Builder()
                //读取超时时间
                .readTimeout(10, TimeUnit.SECONDS)
                //连接超时时间
                .connectTimeout(10, TimeUnit.SECONDS)
                //写超时时间
                .writeTimeout(10, TimeUnit.SECONDS)
                //设置连接池
                .connectionPool(new ConnectionPool())
                .build();
    }
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
}
```

3.在 application.yml 中启用 OkHttp。
```yaml
feign:
  okhttp:
    enabled: true
```

到这里，就已将OpenFeign的默认通信对象从URLConnection调整为OKHttp，至于替换为HttpClient组件的配置思路是基本相同的。

需要了解OpenFeign更详细的配置选项，[Spring Cloud OpenFeign](https://docs.spring.io/spring-cloud-openfeign/docs/2.2.6.RELEASE/reference/html/) 官方文档进行学习。
