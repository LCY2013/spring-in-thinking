### SpringBoot 特性

#### 基于Profile的不同环境激活
properties如下：
```properties
spring.profiles.active = dev
```
yaml如下：
```yaml
spring:
  profiles:
    active: dev
```
多环境同时激活：
```properties
spring.profiles.active= prod, myprofile1, myprofile2
```
```yaml
spring:
  profiles:
    active: dev,text
```
yaml同文件多个配置环境：
```yaml
spring: 
 profiles: test
 #test 环境相关配置信息
---
spring: 
 profiles: prod
 #prod 环境相关配置信息
```
Java启动时设置环境参数如下：
```text
java –jar customerservice-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```
#### 代码控制与profile
利用不同的配置环境构建不同的数据库源，java 代码示例如下：
```java
@Configuration
public class DataSourceConfig {

    @Bean
    @Profile("dev")
    public DataSource devDataSource() {
    //创建 dev 环境下的 DataSource 
    }
    
    @Bean()
    @Profile("prod")
    public DataSource prodDataSource(){
    //创建 prod 环境下的 DataSource 
    }
}
```
一个常见的需求是根据不同的运行环境初始化数据，常见的做法是独立执行一段代码或脚本。基于 @Profile 注解，就可以将这一过程包含在代码中并做到自动化，如下所示：
```java
@Profile("dev")
@Configuration
public class DevDataInitConfig {

    @Bean
    public CommandLineRunner dataInit() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                //执行 Dev 环境的数据初始化
            }
        };
    }
    
}
```
这里用到了 Spring Boot 所提供了启动时任务接口 CommandLineRunner，实现了该接口的代码会在 Spring Boot 应用程序启动时自动进行执行。

@Profile 注解的应用范围很广，可以将它添加到包含 @Configuration 和 @Component 注解的类及其方法，也就是说可以延伸到继承了 @Component 注解的 @Service、@Controller、@Repository 等各种注解中。

对于一个 Web 应用程序而言，最常见的配置可能就是指定服务暴露的端口地址，如下所示：
```yaml
server:
  port: 8080
```

关于数据源的设置也是常见的一种配置场景，这里以 JPA 为例，给出如下所示的一种配置方案：
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create
    show-sql: true
```

设置日志级别和对象，如下：
```properties
logging.level.root=WARN
logging.level.com.springcss.customer=INFO
```

一个全局的 application.yml 配置文件和多个局部的 profile 配置文件的情况下，通过spring: profiles: active: test,dev指定加载某一个或多个局部profile后，springboo加载profile顺序为1application.yml，2application-test.yml，3application-dev.yml。全局profile第一个加载，2和3的顺序取决于active配置的值，上面例子逗号前面的test早于dev加载。相同配置项，后加载的profile会覆盖先加载的profile。

