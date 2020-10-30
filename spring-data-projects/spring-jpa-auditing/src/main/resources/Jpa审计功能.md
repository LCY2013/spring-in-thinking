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

@MappedSuperclass 源码如下：
```
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface MappedSuperclass {
}

里面没有东西，该注解可以理解为一个标示注解
```

第一步：创建一个 BaseEntity，里面放一些实体的公共字段和注解：
```
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

   @CreatedBy
   private Integer createUserId;

   @CreatedDate
   private Instant createTime;

   @LastModifiedBy
   private Integer lastModifiedUserId;

   @LastModifiedDate
   private Instant lastModifiedTime;
}
```

第二步：实体直接继承 BaseEntity

Student 实例继承 BaseEntity，代码如下：
```
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private SexEnum sex;

    private Integer age;

    @OneToMany(mappedBy = "user")
    private List<UserAddress> addresses;

    private Boolean deleted;
}
```

第三步：测试用例

```
@Test
public void testAuditorStudent(){
    //由于测试用例模拟web context环境不是我们的重点，我们这里利用@MockBean，mock掉我们的方法，期待返回7这个用户ID
    Mockito.when(userAuditorAware.getCurrentAuditor()).thenReturn(Optional.of(7));
    //没有显式的指定更新时间、创建时间、更新人、创建人
    Student student = Student.builder()
            .name("fufeng")
            .email("fufeng@magic.com")
            .sex(SexEnum.BOY)
            .age(20)
            .build();
    studentRepository.save(student);

    //验证是否有创建时间、更新时间，UserID是否正确；
    List<Student> students = studentRepository.findAll();
    Assertions.assertEquals(7,students.get(0).getCreateUserId());
    Assertions.assertNotNull(students.get(0).getLastModifiedTime());
    System.out.println(students.get(0));
}
```

[Auditing项目地址](https://github.com/LCY2013/spring-in-thinking/tree/master/spring-data-projects/spring-jpa-auditing)


### JPA 的审计功能解决了哪些问题？

1、可以很容易地让我们写自己的 BaseEntity，把一些公共的字段放在里面，不需要我们关心太多和业务无关的字段，更容易让我们公司的表更加统一和规范，就是统一加上 @CreatedBy、@CreatedDate、@LastModifiedBy、@LastModifiedDate 等。

实际工作中，BaseEntity 可能还更复杂一点，比如说把 ID 和 @Version 加进去，会变成如下形式：
```
@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

   @Id
   @GeneratedValue(strategy= GenerationType.AUTO)
   private Long id;

   @CreatedBy
   private Integer createUserId;

   @CreatedDate
   private Instant createTime;

   @LastModifiedBy
   private Integer lastModifiedUserId;

   @LastModifiedDate
   private Instant lastModifiedTime;

   @Version
   private Integer version;
}
```

### Auditing 的实现原理

第一步：还是从 @EnableJpaAuditing 入手分析：

```
@Inherited
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JpaAuditingRegistrar.class)   // 重要，关注方法 registerBeanDefinitions、registerAuditListenerBeanDefinition、registerBeanConfigurerAspectIfNecessary
public @interface EnableJpaAuditing {
    ...
}
```

第二步：打开 AuditingEntityListener 的源码分析 debug 一下

```
@Configurable
public class AuditingEntityListener {
	private @Nullable ObjectFactory<AuditingHandler> handler;
	/**
	 * Configures the {@link AuditingHandler} to be used to set the current auditor on the domain types touched.
	 * @param auditingHandler must not be {@literal null}.
	 */
	public void setAuditingHandler(ObjectFactory<AuditingHandler> auditingHandler) {
		Assert.notNull(auditingHandler, "AuditingHandler must not be null!");
		this.handler = auditingHandler;
	}
	/**
	 * Sets modification and creation date and auditor on the target object in case it implements {@link Auditable} on
	 * persist events.
	 * @param target
	 */
	@PrePersist // 重要，回调方法
	public void touchForCreate(Object target) {
		Assert.notNull(target, "Entity must not be null!");
		if (handler != null) {
			AuditingHandler object = handler.getObject();
			if (object != null) {
				object.markCreated(target);
			}
		}
	}
	/**
	 * Sets modification and creation date and auditor on the target object in case it implements {@link Auditable} on
	 * update events.
	 * @param target
	 */
	@PreUpdate  // 重要，回调方法
	public void touchForUpdate(Object target) {
		Assert.notNull(target, "Entity must not be null!");
		if (handler != null) {
			AuditingHandler object = handler.getObject();
			if (object != null) {
				object.markModified(target);
			}
		}
	}
}
```

### 原理分析结论

通过 Auditing 的实现源码，其实给我们提供了一个思路，就是怎么利用 @PrePersist、@PreUpdate 等回调函数和 @EntityListeners 定义自己的框架代码，比如说 Auditing 的操作日志场景等。

想成功配置 Auditing 功能，必须将 @EnableJpaAuditing 和 @EntityListeners(AuditingEntityListener.class) 一起使用才有效。

是不是可以不通过 Spring data JPA 给我们提供的 Auditing 功能，而是直接使用 @PrePersist、@PreUpdate 回调函数注解在实体上，也可以达到同样的效果呢？答案是肯定的，因为回调函数是实现的本质。




