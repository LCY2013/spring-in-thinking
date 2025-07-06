package org.fufeng.r2dbc.dao;

import org.fufeng.r2dbc.entity.UserDO;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserDao extends ReactiveCrudRepository<UserDO, Long> {
    Mono<UserDO> findByEmail(String email);
}
