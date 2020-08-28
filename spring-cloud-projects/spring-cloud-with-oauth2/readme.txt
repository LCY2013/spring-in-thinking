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



