package org.fufeng.async.service;

import org.fufeng.jpa.entity.UserDO;
import org.fufeng.mvc.dto.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface RUserService extends UserDetailsService {
    Future<UserDO> saveAsync(UserRegistration registrationDto);

    CompletableFuture<UserDO> save(UserRegistration registrationDto);

    Future<List<UserDO>> getAllAsync();

    CompletableFuture<List<UserDO>> getAll();
}
