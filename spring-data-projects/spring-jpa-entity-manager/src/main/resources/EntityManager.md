#### Entity Manager 相关操作

Java Persistence API 规定，操作数据库实体必须要通过 EntityManager 进行，而我们前面看到了所有的 Repository 在 JPA 里面的实现类是 SimpleJpaRepository，它在真正操作实体的时候都是调用 EntityManager 里面的方法。

在 SimpleJpaRepository 里面设置一个断点，这样可以很容易看得出来 EntityManger 是 JPA 的接口协议，而其实现类是 Hibernate 里面的 SessionImpl。

EntityManager 方法展示如下:
```
public interface EntityManager {

  //用于将新创建的Entity纳入EntityManager的管理。该方法执行后，传入persist()方法的 Entity 对象转换成持久化状态。
  public void persist(Object entity);

  //将游离态的实体merge到当前的persistence context里面，一般用于更新。
  public <T> T merge(T entity);

  //将实体对象删除，物理删除
  public void remove(Object entity);

  //将当前的persistence context中的实体，同步到数据库里面，只有执行了这个方法，上面的EntityManager的操作才会DB生效；
  public void flush();

  //根据实体类型和主键查询一个实体对象；
  public <T> T find(Class<T> entityClass, Object primaryKey);

  //根据JPQL创建一个Query对象
  public Query createQuery(String qlString);

  //利用CriteriaUpdate创建更新查询
  public Query createQuery(CriteriaUpdate updateQuery);

  //利用原生的sql语句创建查询，可以是查询、更新、删除等sql
  public Query createNativeQuery(String sqlString);
}
```

EntityManager 如何使用？

1、获得 EntityManager 的方式：通过 @PersistenceContext 注解。

2、将 @PersistenceContext 注解标注在 EntityManager 类型的字段上，这样得到的 EntityManager 就是容器管理的 EntityManager。由于是容器管理的，所以我们不需要、也不应该显式关闭注入的 EntityManager 实例。

下面是关于这种方式的例子，在测试类中获得 @PersistenceContext 里面的 EntityManager：
```
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    //利用该方式获得entityManager
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    /**
     * 测试entityManager用法
     *
     * @throws JsonProcessingException
     */
    @Test
    @Rollback(false)
    public void testEntityManager() throws JsonProcessingException {
        //测试找到一个User对象
        User user = entityManager.find(User.class,2L);
        Assertions.assertEquals(user.getAddresses(),"shanghai");

        //我们改变一下user的删除状态
        user.setDeleted(true);

        //merger方法
        entityManager.merge(user);

        //更新到数据库里面
        entityManager.flush();

        //再通过createQuery创建一个JPQL，进行查询
        List<User> users =  entityManager.createQuery("select u From User u where u.name=?1")
                .setParameter(1,"jack")
                .getResultList();
        Assertions.assertTrue(users.get(0).getDeleted());
    }
}
```

@EnableJpaRepositories 语法

```java
public @interface EnableJpaRepositories {
   /**
        等价于basePackages
        用于配置扫描 Repositories 所在的 package 及子 package

    使用如下:
        @EnableJpaRepositories(basePackages = "com.fufeng")
        @EnableJpaRepositories(basePackages = {"com.fufeng.repository1", 	"com.fufeng.repository2"})

        默认 @SpringBootApplication 注解出现目录及其子目录
    */
   String[] value() default {};
   String[] basePackages() default {};
   /**
         指定 Repository 类所在包，可以替换 basePackage 的使用。
         一样可以单个字符，DomainRepository.class 所在 Package 下面的所有 Repositories 都会被扫描注册。  
         @EnableJpaRepositories(basePackageClasses = DomainRepository.class)
         @EnableJpaRepositories(basePackageClasses = {Domain1Repository.class,Domain2Repository.class})
   
    */
   Class<?>[] basePackageClasses() default {};
   /**
       指定包含的过滤器，该过滤器采用 ComponentScan 的过滤器，可以指定过滤器类型。
        
       下面表示只扫描带 Repository 注解的类
       @EnableJpaRepositories( includeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Repository.class)})   
    */
   Filter[] includeFilters() default {};
   /**
       指定不包含过滤器，该过滤器也是采用 ComponentScan 的过滤器里面的类。
       
       @Service 和 @Controller 注解的类，不用扫描进去，当我们的项目变大了之后可以加快应用的启动速度。    
       @EnableJpaRepositories(excludeFilters={@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Service.class),@ComponentScan.Filter(type=FilterType.ANNOTATION, value=Controller.class)})
 
    */
   Filter[] excludeFilters() default {};
   /**
      当自定义 Repository 的时候，约定的接口 Repository 的实现类的后缀是什么，默认是 Impl。     
    */
   String repositoryImplementationPostfix() default "Impl";
   /**
       named SQL 存放的位置，默认为 META-INF/jpa-named-queries.properties
       eg:
          Todo.findBySearchTermNamedFile=SELECT t FROM Table t WHERE LOWER(t.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY t.title ASC  
       建议不要用，因为虽然功能很强大，但是，当使用了这么复杂的方法时，需要想一想是否有更简单的方法。
    */
   String namedQueriesLocation() default "";
   /**
        构建条件查询的查找策略，包含三种方式：CREATE、USE_DECLARED_QUERY、CREATE_IF_NOT_FOUND。

        CREATE：按照接口名称自动构建查询方法，即我们前面说的 Defining Query Methods；
        USE_DECLARED_QUERY：用 @Query 这种方式查询；
        CREATE_IF_NOT_FOUND：如果有 @Query 注解，先以这个为准；如果不起作用，再用 Defining Query Methods；这个是默认的，基本不需要修改，我们知道就行了。
    */
   Key queryLookupStrategy() default Key.CREATE_IF_NOT_FOUND;
   /**
        指定生产 Repository 的工厂类，默认 JpaRepositoryFactoryBean。

        JpaRepositoryFactoryBean 的主要作用是以动态代理的方式，帮我们所有 Repository 的接口生成实现类。
        
        例如当我们通过断点，看到 UserRepository 的实现类是 SimpleJpaRepository 代理对象的时候，就是这个工厂类干的，一般我们很少会去改变这个生成代理的机制。
    */
   Class<?> repositoryFactoryBeanClass() default JpaRepositoryFactoryBean.class;
   
   /**
        用来指定自定义的 Repository 的实现类是什么。默认是 DefaultRepositoryBaseClass，即表示没有指定的 Repository 的实现基类。
        
        以上就是 @EnableJpaRepositories 的基本语法了，涉及的方法比较多。

    */
   Class<?> repositoryBaseClass() default DefaultRepositoryBaseClass.class;
   /**
       用来指定创建和生产 EntityManager 的工厂类是哪个，默认是 name=“entityManagerFactory” 的 Bean。

       几乎很难修改，除非要改变整个 session 的创建机制。
   */  
   String entityManagerFactoryRef() default "entityManagerFactory";
   String transactionManagerRef() default "transactionManager";
   boolean considerNestedRepositories() default false;
   boolean enableDefaultTransactions() default true;
}
```

默认情况下是 spring 的自动加载机制，通过 spring.factories 的文件加载 JpaRepositoriesAutoConfiguration，如下图：
```
org.springframework.boot/spring-boot-autoconfigure/2.3.3.RELEASE/455ff01f4ad77513f96d14a6f84fc1bc68069ee9/spring-boot-autoconfigure-2.3.3.RELEASE-sources.jar!/META-INF/spring.factories
```

#### 自定义 Repository 的 impl 的方法

##### 定义自己的 Repository 的实现，有以下两种方法

第一种方法：定义独立的 Repository 的 Impl 实现类
```
第一步：定义一个 CustomizedUserRepository 接口

@EnableJpaRepositories 开启之后扫描到，代码如下：

public interface CustomizedUserRepository {
    User logicallyDelete(User user);
}

第二步：创建一个 CustomizedUserRepositoryImpl 实现类。

实现类用我们上面说过的 Impl 结尾，如下所示：

import javax.persistence.EntityManager;
public class CustomizedUserRepositoryImpl implements CustomizedUserRepository {
    private EntityManager entityManager;

    public CustomizedUserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User logicallyDelete(User user) {
        user.setDeleted(true);
        return entityManager.merge(user);
    }
}

第三步：当用到 UserRepository 的时候，直接继承自定义的 CustomizedUserRepository 接口即可

public interface UserRepository extends JpaRepository<User,Long>, JpaSpecificationExecutor<User>, CustomizedUserRepository {
}

第四步：写一个测试用例测试一下

@Test
public void testCustomizedUserRepository() {
    //查出来一个User对象
    User user = userRepository.findById(2L).get();
    //调用我们的逻辑删除方法进行删除
    userRepository.logicallyDelete(user);
    //我们再重新查出来，看看值变了没有
    List<User> users = userRepository.findAll();
    Assertions.assertEquals(users.get(0).getDeleted(),Boolean.TRUE);
}


```




































