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



























