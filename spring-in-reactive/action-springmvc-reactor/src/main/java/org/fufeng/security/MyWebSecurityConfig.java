//package org.fufeng.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
//import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
//import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
//
//import java.net.URI;
//
//@Configuration
//@EnableWebFluxSecurity
//public class WebSecurityConfig {
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http.authorizeExchange()
//                .pathMatchers("/registration**").permitAll()
//                .pathMatchers("/login/**").permitAll()
//                .pathMatchers("/resources/**").permitAll()
//                .pathMatchers("/webjars/**").permitAll()
//                .pathMatchers("/css/**").permitAll()
//                .pathMatchers("/image/**").permitAll()
//                .pathMatchers("/api/**").permitAll()
//                .pathMatchers("/console/**").permitAll()
//                .anyExchange().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .and()
//                .logout()
//                .requiresLogout(new PathPatternParserServerWebExchangeMatcher("/logout")).logoutSuccessHandler(logoutSuccessHandler())
//                .and()
//                .build();
//    }
//
//    @Bean
//    public ServerLogoutSuccessHandler logoutSuccessHandler() {
//        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
//        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/login?logout"));
//        return logoutSuccessHandler;
//    }
//
////    @Bean
////    public MapReactiveUserDetailsService userDetailsService() {
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
////        return new MapReactiveUserDetailsService(user);
////    }
//
////    public void configure(WebSecurity web) {
////        web.ignoring()
////                .antMatchers("/resources/**")
////                .antMatchers("/css/**")
////                .antMatchers("/webjars/**")
////                .antMatchers("/image/**")
////                .antMatchers("/api/**")
////                .antMatchers("/console/**");
////    }
//}
