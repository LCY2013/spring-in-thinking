项目简介:
    spring-cloud-with-oauth2-server(oauth2 认证中心) :
        oauth2 服务实现 -> 验证账号、密码，生成token，存储 token，检查 token ,刷新 token
    spring-cloud-with-oauth2-order(订单服务) :
        收到请求后去认证中心验证
    spring-cloud-with-oauth2-user(用户服务) :
        收到请求后去认证中心验证

测试流程:
    我是用 REST Client 来做访问请求的，请求格式如下：

    POST http://localhost:6001/oauth/token?grant_type=password&username=admin&password=123456&scope=all
    Accept: */*
    Cache-Control: no-cache
    Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==

    假设咱们在一个 web 端使用，grant_type 是 password，表明这是使用 OAuth2 的密码模式。

    username=admin 和 password=123456 就相当于在 web 端登录界面输入的用户名和密码，
    我们在认证服务端配置中固定了用户名是 admin 、密码是 123456，而线上环境中则应该通过查询数据库获取。

    scope=all 是权限有关的，在认证服务的 OAuthConfig 中指定了 scope 为 all 。

    Authorization 要加在请求头中，格式为 Basic 空格 base64(clientId:clientSecret)，
    这个微服务客户端的 client-id 是 user-client，client-secret 是 user-secret-8888，
    将这两个值通过冒号连接，并使用 base64 编码(user-client:user-secret-8888)之后的值为
    dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==，可以通过 https://www.sojson.com/base64.html 在线编码获取。

    运行请求后，如果参数都正确的话，获取到的返回内容如下，是一段 json 格式
    {
        "access_token": "66e4f302-897e-4fea-b343-47ceb1c3ea4e",
        "token_type": "bearer",
        "refresh_token": "d00f0937-c0e6-4569-b436-d976b07d0556",
        "expires_in": 3599,
        "scope": "all"
    }
    access_token :  就是之后请求需要带上的 token，也是本次请求的主要目的 token_type：为 bearer，
    这是 access token 最常用的一种形式 refresh_token：之后可以用这个值来换取新的 token，
    而不用输入账号密码 expires_in：token 的过期时间(秒)

    我们在用户客户端中定义了一个接口 http://localhost:6101/client-user/get，
    现在就拿着上一步获取的 token 来请求这个接口。
    GET http://localhost:6101/client-user/get
    Accept: */*
    Cache-Control: no-cache
    Authorization: bearer 66e4f302-897e-4fea-b343-47ceb1c3ea4e

    token 过期后，用 refresh_token 换取 access_token
    一般都会设置 access_token 的过期时间小于 refresh_token 的过期时间，以便在 access_token 过期后，
    不用用户再次登录的情况下，获取新的 access_token。
    ### 换取 access_token
    POST http://localhost:6001/oauth/token?grant_type=refresh_token&refresh_token=d00f0937-c0e6-4569-b436-d976b07d0556
    Accept: */*
    Cache-Control: no-cache
    Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==
    grant_type 设置为 refresh_token。
    refresh_token 设置为请求 token 时返回的 refresh_token 的值。
    请求头加入 Authorization，格式依然是 Basic + 空格 + base64(client-id:client-secret)
    请求成功后会返回和请求 token 同样的数据格式。

    用 JWT 替换 redisToken
    上面 token 的存储用的是 redis 的方案，Spring Security OAuth2 还提供了 jdbc 和 jwt 的支持，
    jdbc 的暂不考虑，现在来介绍用 JWT 的方式来实现 token 的存储。

    用 JWT 的方式就不用把 token 再存储到服务端了，JWT 有自己特殊的加密方式，可以有效的防止数据被篡改，
    只要不把用户密码等关键信息放到 JWT 里就可以保证安全性。

    认证服务端改造
    先把有关 redis 的配置去掉。

    运行请求 token 接口的请求
    POST http://localhost:6001/oauth/token?grant_type=password&username=admin&password=123456&scope=all
    Accept: */*
    Cache-Control: no-cache
    Authorization: Basic dXNlci1jbGllbnQ6dXNlci1zZWNyZXQtODg4OA==

    结果如下:
        {
            "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg1ODk3NTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI3NjEzZjkzZi01ODFkLTQ1MjgtYTY3Mi0zMmU5OGEwMjUwZTciLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCIsInNjb3BlIjpbImFsbCJdfQ.Elg6c_XuOLu_rqm8nw4AoRsJ6dJVc2tzDih1BmssC7w",
            "token_type": "bearer",
            "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsInNjb3BlIjpbImFsbCJdLCJhdGkiOiI3NjEzZjkzZi01ODFkLTQ1MjgtYTY3Mi0zMmU5OGEwMjUwZTciLCJleHAiOjE2MDExNzgxNTgsImF1dGhvcml0aWVzIjpbIlJPTEVfQURNSU4iXSwianRpIjoiM2YyMWY1NGYtMTA0NS00NWY0LTgzNjktYmNlZmNhNGNkZTBjIiwiY2xpZW50X2lkIjoidXNlci1jbGllbnQifQ.DxxajnqBuubVSurD7iIYJGuUGqv91MBr5M9cjW6PQE8",
            "expires_in": 3599,
            "scope": "all",
            "jti": "7613f93f-581d-4528-a672-32e98a0250e7"
        }
    我们已经看到返回的 token 是 JWT 格式了，到 JWT 在线解码网站 https://jwt.io/ 或者 http://jwt.calebb.net/将 token 解码看一下

    拿着返回的 token 请求用户客户端接口
    GET http://localhost:6101/client-user/get
    Accept: */*
    Cache-Control: no-cache
    Authorization: bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1OTg1ODk3NTgsInVzZXJfbmFtZSI6ImFkbWluIiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI3NjEzZjkzZi01ODFkLTQ1MjgtYTY3Mi0zMmU5OGEwMjUwZTciLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCIsInNjb3BlIjpbImFsbCJdfQ.Elg6c_XuOLu_rqm8nw4AoRsJ6dJVc2tzDih1BmssC7w

    增强 JWT
    如果我想在 JWT 中加入额外的字段(比方说用户的其他信息)怎么办呢，当然可以。
    spring security oauth2 提供了 TokenEnhancer 增强器。其实不光 JWT ，RedisToken 的方式同样可以。
    声明一个JWTokenEnhancer
    通过 oAuth2Authentication 可以拿到用户名等信息，通过这些我们可以在这里查询数据库或者缓存获取更多的信息，而这些信息都可以作为 JWT 扩展信息加入其中。

    OAuthConfig 配置类修改
    @Autowired
    private TokenEnhancer jwtTokenEnhancer;
    @Bean
    public TokenEnhancer jwtTokenEnhancer(){
       return new JWTokenEnhancer();
    }

    修改 configure(final AuthorizationServerEndpointsConfigurer endpoints)方法
    @Override
    public void configure( final AuthorizationServerEndpointsConfigurer endpoints ) throws Exception{
    /**
    * jwt 增强模式
    */
    TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
    List<TokenEnhancer> enhancerList = new ArrayList<>();
    enhancerList.add( jwtTokenEnhancer );
    enhancerList.add( jwtAccessTokenConverter );
    enhancerChain.setTokenEnhancers( enhancerList );
    endpoints.tokenStore( jwtTokenStore )
    .userDetailsService( kiteUserDetailsService )
    /**
    * 支持 password 模式
    */
    .authenticationManager( authenticationManager )
    .tokenEnhancer( enhancerChain )
    .accessTokenConverter( jwtAccessTokenConverter );
    }
    再次请求token:
    {
        "access_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp3dC1leHQiOiJKV1QgaW5mb3JtYXRpb24xNTk4NTkzNzYzODU4Iiwic2NvcGUiOlsiYWxsIl0sImV4cCI6MTU5ODU5NzM2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI5YzM4MGRmMC03NzY0LTQ5NmYtYTBlMy05OTFjYzFlM2RmYmUiLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCJ9.ijPnSUrZFvB77brgSPYt40yFr-YqUrwdz3XR_99a7C8",
        "token_type": "bearer",
        "refresh_token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJhZG1pbiIsImp3dC1leHQiOiJKV1QgaW5mb3JtYXRpb24xNTk4NTkzNzYzODU4Iiwic2NvcGUiOlsiYWxsIl0sImF0aSI6IjljMzgwZGYwLTc3NjQtNDk2Zi1hMGUzLTk5MWNjMWUzZGZiZSIsImV4cCI6MTYwMTE4NTc2MywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdLCJqdGkiOiI3NzQwZjE2Ni1iYTJlLTRjODItOGE1Yi02OTcyZTY4ZDZkNWIiLCJjbGllbnRfaWQiOiJ1c2VyLWNsaWVudCJ9.laFOLT84j5agXe_kt0I5lsFfaC6Ei68GWsyADTwICv8",
        "expires_in": 3599,
        "scope": "all",
        "jwt-ext": "JWT information1598593763858",
        "jti": "9c380df0-7764-496f-a0e3-991cc1e3dfbe"
    }
    用户客户端解析 JWT 数据
    我们如果在 JWT 中加入了额外信息，这些信息我们可能会用到，而在接收到 JWT 格式的 token 之后，
    用户客户端要把 JWT 解析出来。



