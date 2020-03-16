依赖注入的模式和类型
    setter <property name="user" ref="userBean"></property>
    构造器 <constructor-arg name="user" reg="userBean"/>
    字段 @Autowired User user
    方法 @Autowired public void user(User user){this.user = user;}
    接口回调 class userBean implements BeanFactoryAware{}









