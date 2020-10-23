#### [Specification 实现一套自己的JPA操作框架](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-data-projects/spring-jpa-specification)

JPA 提供 JpaSpecificationExecutor 的目的不是做日常的业务查询，而是提供了一种自定义 Query for rest 的架构思路，而做日常的增删改查，肯定不如 Defining Query Methods 和 @Query 方便。

#### 自定义扩展Specification流程

Specification自定义实现 - <MagicSpecification>
```java
public class MagicSpecification<Entity> implements Specification<Entity> {
   private SearchCriteria criteria;
   public MySpecification (SearchCriteria criteria) {
      this.criteria = criteria;
   }
   /**
    * 实现实体根据不同的字段、不同的Operator组合成不同的Predicate条件
    *
    * @param root            must not be {@literal null}.
    * @param query           must not be {@literal null}.
    * @param builder  must not be {@literal null}.
    * @return a {@link Predicate}, may be {@literal null}.
    */
   @Override
   public Predicate toPredicate(Root<Entity> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
      if (criteria.getOperation().compareTo(Operator.GT)==0) {
         return builder.greaterThanOrEqualTo(
               root.<String> get(criteria.getKey()), criteria.getValue().toString());
      }
      else if (criteria.getOperation().compareTo(Operator.LT)==0) {
         return builder.lessThanOrEqualTo(
               root.<String> get(criteria.getKey()), criteria.getValue().toString());
      }
      else if (criteria.getOperation().compareTo(Operator.LK)==0) {
         if (root.get(criteria.getKey()).getJavaType() == String.class) {
            return builder.like(
                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
         } else {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
         }
      }
      return null;
   }
}
```

测试用例代码如下:
```java
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MagicSpecificationTest {
    @Autowired
    private UserRepository userRepository;
    private final Date date = new Date();
    /**
     * 负责添加数据，假设数据库里面已经有的数据
     */
    @BeforeAll
    @Rollback(false)
    @Transactional
    void init(){
        User user = User.builder().name("fufeng").age(18).email("fufeng@magic.com")
                .updateDate(date).createDate(Instant.now()).build();

        userRepository.save(user);
    }
    /**
     * 测试自定义的Specification语法
     */
    @Test
    public void givenLast_whenGettingListOfUsers_thenCorrect() {
        MagicSpecification<User> name =
                new MagicSpecification<>(new SearchCriteria("name", Operator.LK, "fufeng"));
        MagicSpecification<User> age =
                new MagicSpecification<>(new SearchCriteria("age", Operator.GT, 2));
        List<User> results = userRepository.findAll(Specification.where(name).and(age));
        results.forEach(System.out::println);
    }
}
```

#### 利用 Specification 创建 search 为查询条件的 Rest API 接口

创建一个 Controller，用来接收 search 这样的查询条件：类似 users search=name:fufeng,age>0 的参数。


```java
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    @ResponseBody
    public List<User> search(@RequestParam(value = "search") String search) {
        Specification<User> spec = new SpecificationsBuilder<User>().buildSpecification(search);
        return userRepository.findAll(spec);
    }
}
```















