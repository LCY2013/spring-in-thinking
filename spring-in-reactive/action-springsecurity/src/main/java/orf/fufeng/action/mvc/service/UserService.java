package orf.fufeng.action.mvc.service;

import orf.fufeng.action.jpa.entity.UserDO;
import orf.fufeng.action.mvc.dto.UserRegistration;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDO save(UserRegistration registrationDto);

    List<UserDO> getAll();
}
