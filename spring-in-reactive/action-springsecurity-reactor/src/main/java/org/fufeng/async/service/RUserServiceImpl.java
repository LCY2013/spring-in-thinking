package org.fufeng.async.service;

import org.fufeng.jpa.dao.UserDao;
import org.fufeng.jpa.entity.RoleDO;
import org.fufeng.jpa.entity.UserDO;
import org.fufeng.mvc.dto.UserRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class RUserServiceImpl implements RUserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Async
    @Override
    public Future<UserDO> saveAsync(UserRegistration registrationDto) {
        return new AsyncResult<>(doSave(registrationDto));
    }

    @Override
    public CompletableFuture<UserDO> save(UserRegistration registrationDto) {
        return CompletableFuture.supplyAsync(() -> doSave(registrationDto));
    }

    @Async
    @Override
    public Future<List<UserDO>> getAllAsync() {
        return new AsyncResult<>(doGetAll());
    }

    @Override
    public CompletableFuture<List<UserDO>> getAll() {
        return CompletableFuture.supplyAsync(() -> doGetAll());
    }

    public UserDO doSave(UserRegistration registrationDto) {
        UserDO user = new UserDO(registrationDto.getEmail(), registrationDto.getFirstName(), registrationDto.getLastName(),
                passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new RoleDO("ROLE_USER")));

        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDO user = userDao.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<RoleDO> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public List<UserDO> doGetAll() {
        return userDao.findAll();
    }
}
