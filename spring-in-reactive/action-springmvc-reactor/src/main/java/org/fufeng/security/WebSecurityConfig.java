
package org.fufeng.security;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.reactive.service.RUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

import java.net.URI;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 启用方法级安全控制
public class WebSecurityConfig {

    private final RUserService userService;

    public WebSecurityConfig(RUserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange((exchanges) -> exchanges.pathMatchers("/registration**").permitAll()
                .pathMatchers("/login/**").permitAll()
                .pathMatchers("/resources/**").permitAll()
                .pathMatchers("/webjars/**").permitAll()
                .pathMatchers("/css/**").permitAll()
                .pathMatchers("/image/**").permitAll()
                .pathMatchers("/api/**").permitAll()
                .pathMatchers("/console/**").permitAll()
                .anyExchange().authenticated());
        http.formLogin(spec -> spec.loginPage("/login"));
        http.logout(spec -> spec.logoutSuccessHandler(logoutSuccessHandler()).
                requiresLogout(new PathPatternParserServerWebExchangeMatcher("/logout")));
        return http.build();
    }

    @Bean
    public ServerLogoutSuccessHandler logoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/login?logout"));
        return logoutSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}