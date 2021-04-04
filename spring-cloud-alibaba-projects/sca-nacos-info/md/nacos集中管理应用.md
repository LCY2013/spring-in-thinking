### 配置中心：基于 Nacos 集中管理应用配置

#### 为什么微服务架构必须部署配置中心
现在微服务开发的主流技术是基于 Spring Boot 进行的，我们都知道 Spring Boot 默认配置文件为 application.yml 或者 application.properties。它保存了应用的主要配置信息，这些配置文件会随着应用发布被打包放入 Jar 文件，随着应用加载并运行。

运维主要问题来自三个方面：
```text
第一，纯粹的工作量增加。假设微服务 A 有 400 个实例，这些配置文件 application.yml 分散存储在每一个 Jar 中，此时因为机房环境变化，数据库服务器的 IP 变更，运维人员就不得不在 400 个实例中对每一个数据库连接 URL 进行调整，这个过程费时费力还容易出错。

第二，版本管理的需求。因为生产环境的状况远比开发、测试环境复杂，谁都无法保证新版本服务上线时新应用一定不会出问题。如果出现重大故障，生产环境下必须具备应用版本回滚的机制，保证生产可用的前提下再分析故障原因，而这个场景中如何对配置文件进行版本管理也是必须要考虑到的。

第三，多环境之间的切换。在成熟的软件研发流程中，是拥有多套不同环境的，例如：开发环境、测试环境、UAT 环境、仿真环境、生产环境。不同环境中各种组件的 IP、用户名、密码、配置项都会有差异，在不同环境下运行要求应用具备快速切换并加载对应的配置文件的能力，显然将配置写死在 Jar 中是无法满足这个要求的。
```

为了解决这些问题，在现有的微服务架构下，必须额外的引入“配置中心”这一组件，配置中心的职责就是集中管理微服务架构中每一个服务实例的配置数据。当微服务架构引入配置中心后，微服务应用只需持有应用启动的最小化配置，在应用启动时微服务应用所需的其他配置数据，诸如数据库连接字符串、各种用户名密码、IP 等信息均从配置中心远程下载，不再本地保存。同时，作为开发应用的程序员，在书写应用配置时也不再直接写入 application.yml 配置，而是直接在配置中心提供的 UI 进行设置。

研发运维人员在配置中心提前定义各种环境的配置信息，之后在微服务实例启动时根据服务名、环境等从配置中心查询配置数据并下载到服务实例本地，最后服务实例加载这些来自配置中心的配置信息完成应用的启动。

在 Spring Cloud Alibaba 这个架构下，Nacos 除了能作为注册中心，还提供了配置中心的功能。别看 Nacos 身兼多职，但每一项职责也并不平庸，Nacos 作为配置中心，除了基本的配置存储，还提供了版本管理、变更推送、监听查询以及友好的中文 UI 界面，无论是研发人员还是运维人员都可以快速上手实现应用配置。

#### 部署 Nacos 配置中心与服务接入
因为 Nacos 本身就同时具备注册中心与配置中心职责，在部署方面与之前部署 Nacos 集群基本一致。唯一不同点是因为 Nacos 要将应用的配置数据保存在数据库以防丢失，所以要配置 Nacos 的数据库访问地址。

#### 微服务接入 Nacos 配置中心
##### 第一步，创建工程引入依赖。
利用 Spring Initializr 向导创建 order-service 订单服务工程，确保 pom.xml 引入以下 3 个依赖。
```text
<!-- Spring Boot Web模块 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<!-- Nacos注册中心starter -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<!-- Nacos配置中心starter -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

##### 第二步，在工程创建配置文件。
在接入 Nacos 配置中心之前，要确保 Spring Boot 配置是完整的。application.yml 文件内容如下，注意最后两行，custom 开头的配置项是自定义的，等一下用于演示环境切换。
```yaml
server:
  port: 8000
spring:
  application:
    name: order-service
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.31.10:8848 #Nacos通信地址
        username: nacos
        password: nacos
custom: #自定义配置项
  flag: development
  database: 192.168.10.31
```

##### 第三步，创建演示代码。
新建 TestController，增加 /test 接口，将配置文件中两个自定义配置输出。
```text
@RestController
public class TestController {
    @Value("${custom.flag}")
    private String flag;
    @Value("${custom.database}")
    private String database;
    @GetMapping("/test")
    public String test(){
        return "flag:" + flag + "<br/> database:" + database;
    }
}
```

##### 第四步，启动应用查看结果。
打开浏览器，访问下面网址。
```text
http://localhost:8000/test
结果如下：
flag:development
database:192.168.10.31
```

其中 flag：development 说明当前是“开发环境”。
一切准备就绪，下面我们接入 Nacos 配置中心。

###### 第一步，打开 Nacos 配置中心页面，点击右上角“+”号新建配置。

在新建配置页面包含六个选项：Data ID、Group、描述、说明、配置格式与配置内容，分别了解下这些选项的作用。
```text
Data ID：配置的唯一标识，格式固定为：{微服务id}-{环境名}.yml，这里填写 order-service-dev.yml，其中 dev 就是环境名代表这个配置文件是 order-service 的开发环境配置文件。

Group：指定配置文件的分组，这里设置默认分组 DEFAULT_GROUP 即可。

描述：说明 order-service-dev.yml 配置文件的用途。

配置格式：指定“配置内容”的类型，这里选择 YAML 即可。

配置内容：将 order-service 工程的 application.yml 文件内容粘贴过来。
```
之后点击右下角的“发布”按钮完成设置。

与此同时，在 nacos_config 数据库的 config_info 表中也出现了对应配置数据。

####### 第二步，回到 order-service 工程，删除 application.yml，因为这个文件内容已经保存在 Nacos 配置中心中了。

###### 第三步，在 resources 目录下创建 bootstrap.yml 引导文件，对 Nacos 配置中心地址进行设置。注意，bootstrap.yml 文件名是固定的，不要随意改变。
```yaml
spring:
  application:
    name: order-service #微服务id
  profiles:
    active: dev #环境名
  cloud:
    nacos:
      config: #Nacos配置中心配置
        file-extension: yml #文件扩展名
        server-addr: 192.168.31.10:8848
        username: nacos
        password: nacos
logging: #开启debug日志，仅为学习时使用
  level:
    root: debug
```
在上面的配置中，包含了两部分内容，第一部分说明 Nacos 配置中心的 IP 端口等信息，第二部分是通过文件中的 “微服务 id”-“环境名”.“文件扩展名” 三部分组合为有效的 data id，即order-service-dev.yml。

这个 data id 要和 Nacos 的设置大小写保持完全一致，这样在微服务启动时便自动会从 Nacos配置中心获取 order-service-dev.yml 配置并下载到本地完成启动过程。

下面咱们启动应用看一下日志输出:
```text
2021-04-04 10:53:54.763  INFO 68663 --- [           main] b.c.PropertySourceBootstrapConfiguration : Located property source: [BootstrapPropertySource {name='bootstrapProperties-order-service-dev.yaml,DEFAULT_GROUP'}, BootstrapPropertySource {name='bootstrapProperties-order-service.yaml,DEFAULT_GROUP'}, BootstrapPropertySource {name='bootstrapProperties-order-service,DEFAULT_GROUP'}]
2021-04-04 10:53:54.767  INFO 68663 --- [           main] org.fufeng.sca.OrderApplication          : The following profiles are active: dev
2021-04-04 10:53:55.215  WARN 68663 --- [           main] o.s.boot.actuate.endpoint.EndpointId     : Endpoint ID 'nacos-config' contains invalid characters, please migrate to a valid format.
2021-04-04 10:53:55.218  WARN 68663 --- [           main] o.s.boot.actuate.endpoint.EndpointId     : Endpoint ID 'nacos-discovery' contains invalid characters, please migrate to a valid format.
```

到这里我们完成了 Nacos 配置中心的接入。这里做个小总结，因为我们把配置数据放在 Nacos 配置中心中，微服务在启动时自动进行下载，因此同一个微服务的所有实例得到的配置信息都是一致的，如果需要调整里面的配置，只需在 Nacos 中进行调整，然后让微服务实例重启即可重新下载生效。

说到这你肯定又联想到另一个问题，一旦配置变更就必须手动重启，那运维的效率还是太低，如果微服务能自动监听到配置变化自动加载新配置那岂不是更好。答案是肯定的，Nacos 通过主动推送方式允许程序在运行期间重新下载配置，下面我们就来介绍几个在生产中实用的配置技巧。

#### Nacos 生产环境中的配置技巧
##### 配置热加载技术
在 Nacos 中支持配置热加载，在运行过程中允许直接对新的配置项进行重新加载而不需要手动重启。首先咱们了解下热加载背后的处理机制。

当研发运维人员在 Nacos 配置中心对服务A配置进行变更后，org.springframework.cloud.context.refresh.ContextRefresher 这个 Nacos 内置类会监听到 A 服务配置发生改变。再通过 Nacos 注册中心中的可用实例列表找到服务 A 所有实例信息，然后将新配置主动推送到服务 A 的各个实例，各个实例接收到配置进行重新加载。

在这个过程中，服务 A 的程序针对热加载需要作出如下变动：
```text
第一，配置数据必须被封装到单独的配置 Bean 中；

第二，这个配置 Bean 需要被 @Configuration 与 @RefreshScope 两个注解描述。
```

下面我们通过实例讲解：
> 对原有 order-server 作出修改，将 flag 与 database 两个属性移到单独的配置 Bean，并加入 @Configuration 与 @RefreshScope 注解。
```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
@Configuration
@RefreshScope
public class CustomConfig {
    @Value("${custom.flag}")
    private String flag;
    @Value("${custom.database}")
    private String database;
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
}
```
其中，@Configuration 说明当前 Bean 是一个配置 Bean。是 Spring Boot 自带的 Java Config 注解。而 @RefreshScope 则用于监听，当 Nacos 推送新的配置后，由这个注解负责接收并为属性重新赋值。

此外，原有的 TestController 代码变更为引用 CustomConfig 对象。

启动应用后，咱们在 Nacos 手动改变 database 配置项从 31 变为 33。

点击发布后，出现前后配置对比，左边是新版本，右边是上一个旧版本，之间的差异使用红绿线已经标出。

确认发布后，在 order-service 服务的日志立即产生重新加载的信息，提示“Refresh Nacos config”。

运行结果如下，新的配置已即时生效。

##### 切换环境配置文件
假如产品开发完成准备投产，便可利用 Nacos 提供的环境配置迅速完成从开发到生产环境的切换。

第一步，在 Nacos 中设置生产环境的配置，Data Id 为 order-service-prd.yml，其中 pro 是 production 的缩写，代表生产环境配置。

这份配置最大的变化是 Nacos 通信地址与自定义配置均指向生产环境 IP，同时 flag 也变为production 代表生产环境。
```yaml
spring:
  application:
    name: order-service
  cloud:
    nacos:
      discovery:
        server-addr: 238019z50f.zicp.vip:80
        #server-addr: 192.168.0.170:8848
        username: nacos
        password: nacos
server:
  port: 6789
dubbo:
  application:
    name: order-service-dubbo
  registry:
    #address: nacos://192.168.0.170:8848
    address: nacos://238019z50f.zicp.vip:80
  consumer:
    check: false

custom: #自定义配置项
  flag: production
  database: 192.168.10.22
```

第二步，调整 order-service 的 bootstrap.yml 引导文件，最重要的地方是修改环境名为 pro，同时更换为生产环境 Nacos 的通信地址，打包后发布。
```yaml
spring:
  application:
    name: order-service #微服务id
  profiles:
    active: pro #环境名
  cloud:
    nacos:
      config: #这里配置的是Nacos配置中心
        file-extension: yaml #指定文件扩展名
        server-addr: 192.168.31.10:8848
        username: nacos
        password: nacos
```
运行结果如下，这里看到 flag:production 说明已切换到生产环境。

##### 管理基础配置数据
对比 order-service-dev.yaml 与 order-service-prd.yaml 发现，在不同环境的配置文件中普遍存在固定的配置项，例如：spring.application.name=order-service 配置项就是稳定的，且修改它会影响所有环境配置文件。对于这种基础的全局配置，我们可以将其存放到单独的 order-service.yaml 配置中，在 order-service 服务启动时，这个不带环境名的配置文件必然会被加载。

其中，order-service.yaml 包含了应用名称，是其他环境配置都需要包含的内容。
```yaml
spring:
  application:
    name: order-service
```

order-service-dev.yaml 只包含与开发环境的相关配置。
```yaml
server:
  port: 8000
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 192.168.31.10:8848
        username: nacos
        password: nacos
custom: #自定义配置项
  flag: development
  database: 192.168.10.33
```

order-service-prd.yaml 只包含与生产环境的相关配置。
```yaml
server:
  port: 80
spring:
  cloud:
    nacos:
      discovery:
        server-addr: 10.181.36.10:8848
        username: nacos
        password: nacos
custom: #自定义配置项
  flag: production
  database: 10.181.36.22
```

配置中心将各服务实例的配置数据进行集中管理，让运维人员摆脱烦琐的重复工作；其次介绍了 Nacos 作为配置中心是如何部署以及微服务的接入过程，微服务通过 bootstrap.yml 引导文件加载 Nacos 指定 data id 的配置数据；最后我介绍了三种常用在生产环境中的 Nacos 使用技巧，包括配置热加载技术、切换环境配置文件与管理基础配置数据。

#### 共享配置总结
```text
shared-dataids方式：
适合于共享配置文件与项目默认配置文件处于相同Group时，直接两条命令就可以搞定
优点：配置方便
缺点：只能在同一Group中

ext-config方式：
它可以由开发者自定义要读取的共享配置文件的DataId、Group、refresh属性，这样刚好解决了shared-dataids存在的局限性。
优点：可以与shared-dataids方案结合使用，用户自定义配置。灵活性强
缺点：配置容易出错，要熟悉YAML语法
```
