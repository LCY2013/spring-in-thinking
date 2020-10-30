### Auditing 指的是什么？

Auditing 是做审计用的，当我们操作一条记录的时候，需要知道这是谁创建的、什么时间创建的、最后修改人是谁、最后修改时间是什么时候，甚至需要修改记录……这些都是 Spring Data JPA 里面的 Auditing 支持的，提供了四个注解来完成上面说的一系列事情，如下：

1、@CreatedBy 是哪个用户创建的。

2、@CreatedDate 创建的时间。

3、@LastModifiedBy 最后修改实体的用户。

4、@LastModifiedDate 最后一次修改的时间。

利用上面的四个注解实现方法，一共有三种方式实现 Auditing。

### 第一种方式：直接在实例里面添加上述四个注解

第一步：在 @Entity：User 里面添加四个注解，并且新增 @EntityListeners(AuditingEntityListener.class) 注解。

User 的实体代码如下：
```
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "addresses")
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable {
    private static final long serialVersionUID = 5536651377776175919L;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private SexEnum sex;
    private Integer age;
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserAddress> addresses;
    private Boolean deleted;
    @CreatedBy
    private Integer createUserId;
    @CreatedDate
    private Date createTime;
    @LastModifiedBy
    private Integer lastModifiedUserId;
    @LastModifiedDate
    private Date lastModifiedTime;
}
```

第二步：实现 AuditorAware 接口，告诉 JPA 当前的用户是谁

实现 AuditorAware 接口，以及 getCurrentAuditor 方法，并返回一个 Integer 的 user ID

```
public class UserAuditorAware implements AuditorAware<Integer> {
   //需要实现AuditorAware接口，返回当前的用户ID
   @Override
   public Optional<Integer> getCurrentAuditor() {
      ServletRequestAttributes servletRequestAttributes =
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      Integer userId = (Integer) servletRequestAttributes.getRequest().getSession().getAttribute("userId");
      return Optional.ofNullable(userId);
   }
}

这里获得用户 ID 的方法不止这一种，实际工作中，可能将当前的 user 信息放在 Session 中，可能把当前信息放在 Redis 中，也可能放在 Spring 的 security 里面管理。

以 security 为例：
Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
if (authentication == null || !authentication.isAuthenticated()) {
  return null;
}
Integer userId = ((LoginUserInfo) authentication.getPrincipal()).getUser().getId();
```

第三步：通过 @EnableJpaAuditing 注解开启 JPA 的 Auditing 功能

开启 JPA 的 Auditing 功能（默认没开启），这里需要用到的注解是 @EnableJpaAuditing，代码如下：
```
@Configuration
@EnableJpaAuditing
public class AuditingConfiguration {
    @Bean
    public UserAuditorAware userAuditorAware(){
        return new UserAuditorAware();
    }
}
```

EnableJpaAuditing 注解源码：
```
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JpaAuditingRegistrar.class)
public @interface EnableJpaAuditing {
    //auditor用户的获取方法，默认是找AuditorAware的实现类；
    String auditorAwareRef() default "";
    //是否在创建修改的时候设置时间，默认是true
    boolean setDates() default true;
    //在创建的时候是否同时作为修改，默认是true
    boolean modifyOnCreate() default true;
    //时间的生成方法，默认是取当前时间(为什么提供这个功能呢？因为测试的时候有可能希望时间保持不变，它提供了一种自定义的方法)；
    String dateTimeProviderRef() default "";
}
```

第四步：测试用例测试

```
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(JpaConfiguration.class)
public class AuditorTest {

    @Autowired
    private UserRepository userRepository;

    @MockBean
    UserAuditorAware userAuditorAware;

    @Test
    public void testAuditing() {
        //由于测试用例模拟web context环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回7这个用户ID
        Mockito.when(myAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(7));
        //没有显式的指定更新时间、创建时间、更新人、创建人
        User user = User.builder()
                .name("fufeng")
                .email("fufeng@magic.com")
                .sex(SexEnum.BOY)
                .age(20)
                .build(); 
        userRepository.save(user);

        //验证是否有创建时间、更新时间，UserID是否正确；
        List<User> users = userRepository.findAll();
        Assertions.assertEquals(7,users.get(0).getCreateUserId());
        Assertions.assertNotNull(users.get(0).getLastModifiedTime());
        System.out.println(users.get(0));
    }
}
```

### 第二种方式：实体里面实现Auditable 接口

Person 实体对象，如下：

```
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Person implements Auditable<Integer,Long, Instant> {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    private Integer age;

    private Boolean deleted;

    private Integer createUserId;

    private Instant createTime;

    private Integer lastModifiedUserId;

    private Instant lastModifiedTime;

    @Override

    public Optional<Integer> getCreatedBy() {
        return Optional.ofNullable(this.createUserId);
    }

    @Override
    public void setCreatedBy(Integer createdBy) {
        this.createUserId = createdBy;
    }

    @Override
    public Optional<Instant> getCreatedDate() {
        return Optional.ofNullable(this.createTime);
    }

    @Override
    public void setCreatedDate(Instant creationDate) {
        this.createTime = creationDate;
    }

    @Override
    public Optional<Integer> getLastModifiedBy() {
        return Optional.ofNullable(this.lastModifiedUserId);
    }

    @Override
    public void setLastModifiedBy(Integer lastModifiedBy) {
        this.lastModifiedUserId = lastModifiedBy;
    }

    @Override
    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedTime = lastModifiedDate;
    }

    @Override
    public Optional<Instant> getLastModifiedDate() {
        return Optional.ofNullable(this.lastModifiedTime);
    }

    @Override
    public boolean isNew() {
        return id==null;
    }

}
```

### 第三种方式：利用 @MappedSuperclass 注解

它主要是用来解决公共 BaseEntity 的问题，而且其代表的是继承它的每一个类都是一个独立的表。

我们先看一下 @MappedSuperclass 的语法。













