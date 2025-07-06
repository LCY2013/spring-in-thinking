package org.fufeng.reactive.service;

import org.fufeng.r2dbc.entity.UserDO;
import org.fufeng.reactive.dto.UserRegistration;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;

public interface RUserService extends ReactiveUserDetailsService {
    Mono<UserDO> save(UserRegistration registrationDto);
}
