/*
 * The MIT License (MIT)
 * ------------------------------------------------------------------
 * Copyright © 2019 Ramostear.All Rights Reserved.
 *
 * ProjectName: thinking-in-spring-boot
 * @Author : <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @date : 2020-08-28
 * @version : 1.0.0-RELEASE
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.fufeng.cloud.oauth2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.JdbcClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: thinking-in-spring-boot
 * @description: oauth2 配置类
 * @author: <a href="https://github.com/lcy2013">MagicLuo(扶风)</a>
 * @create: 2020-08-28
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    public PasswordEncoder passwordEncoder;
    @Autowired
    public UserDetailsService wkxUserDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;

    //注释Redis相关配置信息
//    @Autowired
//    private TokenStore redisTokenStore;

    @Autowired
    private TokenStore jwtTokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Autowired
    private TokenEnhancer jwtTokenEnhancer;

    // JDBC实现
    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("isAuthenticated()")
                .tokenKeyAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        /*
        ClientId、Client-Secret：这两个参数对应请求端定义的 cleint-id 和 client-secret
        authorizedGrantTypes 可以包括如下几种设置中的一种或多种：
        authorization_code：授权码类型。
        implicit：隐式授权类型。
        password：资源所有者（即用户）密码类型。
        client_credentials：客户端凭据（客户端ID以及Key）类型。
        refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
        accessTokenValiditySeconds：token 的有效期
        scopes：用来限制客户端访问的权限，在换取的 token 的时候会带上 scope 参数，只有在 scopes 定义内的，才可以正常换取 token。
        上面代码中是使用 inMemory 方式存储的，将配置保存到内存中，相当于硬编码了。正式环境下的做法是持久化到数据库中，比如 mysql 中。
            create table oauth_client_details (
              client_id VARCHAR(256) PRIMARY KEY,
              resource_ids VARCHAR(256),
              client_secret VARCHAR(256),
              scope VARCHAR(256),
              authorized_grant_types VARCHAR(256),
              web_server_redirect_uri VARCHAR(256),
              authorities VARCHAR(256),
              access_token_validity INTEGER,
              refresh_token_validity INTEGER,
              additional_information VARCHAR(4096),
              autoapprove VARCHAR(256)
            );
            INSERT INTO oauth_client_details
              (client_id, client_secret, scope, authorized_grant_types,
              web_server_redirect_uri, authorities, access_token_validity,
              refresh_token_validity, additional_information, autoapprove)
            VALUES
              ('user-client', '$2a$10$o2l5kA7z.Caekp72h5kU7uqdTDrlamLq.57M1F6ulJln9tRtOJufq', 'all',
               'authorization_code,refresh_token,password', null, null, 3600, 36000, null, true);
            INSERT INTO oauth_client_details
              (client_id, client_secret, scope, authorized_grant_types,
              web_server_redirect_uri, authorities, access_token_validity,
              refresh_token_validity, additional_information, autoapprove)
            VALUES
              ('order-client', '$2a$10$GoIOhjqFKVyrabUNcie8d.ADX.qZSxpYbO6YK4L2gsNzlCIxEUDlW', 'all',
               'authorization_code,refresh_token,password', null, null, 3600, 36000, null, true);
          注意： client_secret 字段不能直接是 secret 的原始值，需要经过加密。因为是用的 BCryptPasswordEncoder，所以最终插入的值应该是经过 BCryptPasswordEncoder.encode()之后的值。
         代码如下:
            在 OAuth2 配置类(OAuth2Config)中增加 DataSource 的注入
            @Override
            public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
                JdbcClientDetailsServiceBuilder jcsb = clients.jdbc(dataSource);
                jcsb.passwordEncoder(passwordEncoder);
            }
         还有一个重写的方法 public void configure(AuthorizationServerSecurityConfigurer security)，这个方法限制客户端访问认证接口的权限。
            允许客户端访问 OAuth2 授权接口，否则请求 token 会返回 401。
            security.allowFormAuthenticationForClients();
            允许已授权用户访问 checkToken 接口
            security.checkTokenAccess("isAuthenticated()");
            允许已授权用户获取 token 接口
            security.tokenKeyAccess("isAuthenticated()");

          oauth2主要的几个REST接口:
            POST /oauth/authorize 授权码模式认证授权接口
            GET/POST /oauth/token 获取 token 的接口
            POST /oauth/check_token 检查 token 合法性接口
         */
        // JDBC操作
        JdbcClientDetailsServiceBuilder jcsb = clients.jdbc(dataSource);
        jcsb.passwordEncoder(passwordEncoder);

        /*clients.inMemory()
                .withClient("order-client")
                .secret(this.passwordEncoder.encode("order-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600)
                .scopes("all")
                .and()
                .withClient("user-client")
                .secret(this.passwordEncoder.encode("user-secret-8888"))
                .authorizedGrantTypes("refresh_token", "authorization_code", "password")
                .accessTokenValiditySeconds(3600)
                .scopes("all");*/
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        /*
            redis token 方式

            authenticationManage() 调用此方法才能支持 password 模式。
            userDetailsService() 设置用户验证服务。
            tokenStore() 指定 token 的存储方式。
         */
        /*endpoints.authenticationManager(this.authenticationManager)
                .userDetailsService(this.wkxUserDetailsService)
                .tokenStore(this.redisTokenStore);*/

        /**
         * jwt 增强模式
         */
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancerList = new ArrayList<>();
        enhancerList.add( jwtTokenEnhancer );
        enhancerList.add( jwtAccessTokenConverter );
        enhancerChain.setTokenEnhancers( enhancerList );
        endpoints.tokenStore( jwtTokenStore )
                .userDetailsService( wkxUserDetailsService )
                /**
                 * 支持 password 模式
                 */
                .authenticationManager( authenticationManager )
                .tokenEnhancer( enhancerChain )
                .accessTokenConverter( jwtAccessTokenConverter );

        /**
         * 普通 jwt 模式
         */
        /*endpoints.tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(wkxUserDetailsService)
                *//**
                 * 支持 password 模式
                 *//*
                .authenticationManager(authenticationManager);*/
    }

}
