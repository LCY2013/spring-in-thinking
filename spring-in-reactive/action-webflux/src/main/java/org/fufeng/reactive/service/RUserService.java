package org.fufeng.reactive.service;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import reactor.core.publisher.Mono;
import org.fufeng.r2dbc.entity.UserDO;
import org.fufeng.reactive.dto.UserRegistration;

public interface RUserService extends ReactiveUserDetailsService {
    Mono<UserDO> save(UserRegistration registrationDto);
}
