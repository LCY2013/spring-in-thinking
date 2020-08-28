认证客户端
    授权码模式的认证过程是这样的：
    1、用户客户端请求认证服务器的认证接口，并附上回调地址；
    2、认证服务接口接收到认证请求后调整到自身的登录界面；
    3、用户输入用户名和密码，点击确认，跳转到授权、拒绝提示页面(也可省略)；
    4、用户点击授权或者默认授权后，跳转到微服务客户端的回调地址，并传入参数 code；
    5、回调地址一般是一个 RESTful 接口，此接口拿到 code 参数后，再次请求认证服务器的 token 获取接口，用来换取 access_token 等信息；
    6、获取到 access_token 后，拿着 token 去请求各个微服务客户端的接口。

测试流程如下:
     1、模拟请求授权,在浏览器访问 /oauth/authorize 授权接口，接口地址为：
        http://localhost:6001/oauth/authorize?client_id=code-client&response_type=code&redirect_uri=http://localhost:6102/client-authcode/login
        注意 response_type 参数设置为 code，redirect_uri 设置为数据库中插入的回调地址。
     2、输入上面地址后，会自动跳转到认证服务端的登录页面，输入用户名、密码，这里用户名是 admin，密码是 123456
     3、点击确定后，来到授权确认页面，页面上有 Authorize 和 Deny (授权和拒绝)两个按钮。可通过将 autoapprove 字段设置为 0 来取消此页面的展示，默认直接同意授权。
     4、点击同意授权后，跳转到了回调地址，虽然是 404 ，但是我们只是为了拿到 code 参数，注意地址后面的 code 参数。
     5、拿到这个 code 参数是为了向认证服务器 /oauth/token 接口请求 access_token ，继续用 REST Client 发送请求，同样的，你也可以用 postman 等工具测试。
         注意 grant_type 参数设置为 authorization_code，code 就是上一步回调地址中加上的，redirect_uri 仍然要带上，会作为验证条件，如果不带或者与前面设置的不一致，会出现错误。
         请求头 Authorization ，仍然是 Basic + 空格 + base64(client_id:client_secret)，可以通过 https://www.sojson.com/base64.html 网站在线做 base64 编码。
         code-client:code-secret-8888 通过 base64 编码后结果为 Y29kZS1jbGllbnQ6Y29kZS1zZWNyZXQtODg4OA==
         POST http://localhost:6001/oauth/token?grant_type=authorization_code&client=code-client&code=BbCE34&redirect_uri=http://localhost:6102/client-authcode/login
         Accept: */*
         Cache-Control: no-cache
         Authorization: Basic Y29kZS1jbGllbnQ6Y29kZS1zZWNyZXQtODg4OA==
     6、把获取到的 access_token 代入到下面的请求中 ${access_token} 的位置，就可以请求微服务中的需要授权访问的接口了。









