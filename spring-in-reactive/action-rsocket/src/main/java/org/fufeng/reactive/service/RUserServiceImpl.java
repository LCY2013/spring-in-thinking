package org.fufeng.reactive.service;

import org.fufeng.r2dbc.dao.UserDao;
import org.fufeng.r2dbc.entity.UserDO;
import org.fufeng.reactive.dto.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RUserServiceImpl implements RUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Mono<UserDO> save(UserRegistration registrationDto) {
        return doSave(registrationDto);
    }

    public Mono<UserDO> doSave(UserRegistration registrationDto) {
        UserDO user = new UserDO(registrationDto.getEmail(), registrationDto.getFirstName(), registrationDto.getLastName(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList("ROLE_USER"));

        return userDao.save(user);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {

        Mono<UserDO> userDOMono = userDao.findByEmail(username);
        return userDOMono
                .map(user -> new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        mapRolesToAuthorities(user.getRoles())));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<String> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    public Flux<UserDO> doGetAll() {
        return userDao.findAll();
    }
}
