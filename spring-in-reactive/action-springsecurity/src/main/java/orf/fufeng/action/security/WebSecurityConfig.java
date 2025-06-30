
package orf.fufeng.action.security;

import lombok.extern.slf4j.Slf4j;
import orf.fufeng.action.mvc.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.AntPathMatcher;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity // 启用方法级安全控制
public class WebSecurityConfig {

    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF（根据需求选择，API服务建议禁用）
                .csrf(AbstractHttpConfigurer::disable)

                // 授权配置
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/", "/home").permitAll()
                        .requestMatchers("/registration**").permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                // 表单登录配置
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/dashboard")
                        .failureUrl("/login?error=true")
                )

                // 退出登录配置
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        /*.logoutRequestMatcher(request -> {
                            boolean matches = *//* 匹配逻辑 *//*;
                            logger.debug("Logout request matched: {} - {}", request.getRequestURI(), matches);
                            return matches;
                        })*/
                        // 指定 POST 方法更安全
                        .logoutRequestMatcher(request -> {
                            boolean matches = new AntPathMatcher().match("/logout", request.getRequestURI());
                            log.debug("Logout request matched: {} - {}", request.getRequestURI(), matches);
                            return matches;
                        })
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )

                // 记住我功能
                .rememberMe(remember -> remember
                        .key("uniqueAndSecret")
                        .tokenValiditySeconds(86400) // 1天
                );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/resources/**")
                .requestMatchers("/css/**")
                .requestMatchers("/js/**")
                .requestMatchers("/images/**")
                .requestMatchers("/webjars/**")
                .requestMatchers("/favicon.ico");
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