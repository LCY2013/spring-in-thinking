package org.fufeng.security;//package com.com.com.org.fufeng.security;
//
//import com.com.com.org.fufeng.security.auth.filter.CustomAuthenticationFilter;
//import com.com.com.org.fufeng.security.auth.handler.CustomFailureHandler;
//import com.com.com.org.fufeng.security.auth.handler.CustomSuccessHandler;
//import com.com.com.org.fufeng.security.auth.provider.CustomAdminAuthenticationProvider;
//import com.com.com.org.fufeng.security.auth.provider.SudoAuthenticationProvider;
//import com.com.com.org.fufeng.mvc.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//@Configuration
//@EnableWebSecurity
//public class MultiAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private SudoAuthenticationProvider sudoAuthenticationProvider;
//
//    @Autowired
//    private CustomSuccessHandler customSuccessHandler;
//
//    @Autowired
//    private CustomFailureHandler customFailureHandler;
//
//    @Autowired
//    private CustomAdminAuthenticationProvider customAdminAuthenticationProvider;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService);
//        auth.setPasswordEncoder(passwordEncoder());
//        return auth;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) {
//        auth
////                .authenticationProvider(daoAuthenticationProvider())
////                .authenticationProvider(sudoAuthenticationProvider)
//                .authenticationProvider(customAdminAuthenticationProvider);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/registration**").permitAll()
////                .anyRequest().authenticated()
////                .and()
////                .oauth2Login();
//                .formLogin()
//                .loginPage("/login").permitAll()
//                .defaultSuccessUrl("/")
//                .successHandler(customSuccessHandler)
//                .failureUrl("/login?error=true")
//                .failureHandler(customFailureHandler)
//                .and()
//                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//                .logout().invalidateHttpSession(true).clearAuthentication(true)
//                .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login?logout")
//                .permitAll();
//    }
//
//    public CustomAuthenticationFilter authenticationFilter() throws Exception {
//        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
//        filter.setAuthenticationManager(authenticationManagerBean());
//        filter.setAuthenticationSuccessHandler(customSuccessHandler);
//        filter.setAuthenticationFailureHandler(customFailureHandler);
//        return filter;
//    }
//
//    @Override
//    public void configure(WebSecurity web) {
//        web.ignoring()
//                .antMatchers("/resources/**")
//                .antMatchers("/css/**")
//                .antMatchers("/webjars/**")
//                .antMatchers("/image/**")
//                .antMatchers("/console/**");
//    }
//}
