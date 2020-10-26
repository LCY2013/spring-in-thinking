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

































