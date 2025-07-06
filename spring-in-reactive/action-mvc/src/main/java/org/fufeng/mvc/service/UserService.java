package org.fufeng.mvc.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.fufeng.jpa.entity.UserDO;
import org.fufeng.mvc.dto.UserRegistration;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDO save(UserRegistration registrationDto);

    List<UserDO> getAll();
}
