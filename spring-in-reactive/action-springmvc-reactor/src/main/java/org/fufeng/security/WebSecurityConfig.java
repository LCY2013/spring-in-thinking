
package org.fufeng.security;

import lombok.extern.slf4j.Slf4j;
import org.fufeng.reactive.service.RUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
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
@EnableWebFluxSecurity
public class WebSecurityConfig {

    private final RUserService userService;

    public WebSecurityConfig(RUserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.authorizeExchange((exchanges) ->
                exchanges.pathMatchers("/registration**").permitAll()
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

//    @Bean
//    public MapReactiveUserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new MapReactiveUserDetailsService(user);
//    }

//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("/resources/**")
//                .antMatchers("/css/**")
//                .antMatchers("/webjars/**")
//                .antMatchers("/image/**")
//                .antMatchers("/api/**")
//                .antMatchers("/console/**");
//    }
}