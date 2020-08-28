gateway 实现oauth2 认证,去访问其他的服务
    1、 认证服务器中添加关于网关服务的信息
        INSERT INTO `spring_cloud`.`oauth_client_details`(`client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`, `web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`, `additional_information`, `autoapprove`)
        VALUES ('gateway-client', NULL, '$2a$10$vJvrQvbuVGmL3nRl6d5eWuRfK/vEG4gv97WNNLtrqhefCsxcG2RbC',
        'all', 'authorization_code,refresh_token,password', NULL, NULL, 3600, 36000, NULL, '1');
    2、http://localhost:10000/oauth-service/oauth/token?grant_type=password&username=admin&password=123456&scope=all
        head:
            Authorization:Basic Z2F0ZXdheS1jbGllbnQ6Z2F0ZXdheS1zZWNyZXQ=
            Z2F0ZXdheS1jbGllbnQ6Z2F0ZXdheS1zZWNyZXQ=(来自base64[gateway-client:gateway-secret])
    3、 通过网关访问其他服务 http://localhost:10000/consul-oauth-client/get






