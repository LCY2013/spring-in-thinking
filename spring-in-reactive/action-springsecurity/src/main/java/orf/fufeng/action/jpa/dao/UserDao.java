package orf.fufeng.action.jpa.dao;

import orf.fufeng.action.jpa.entity.UserDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserDO, Long> {
    UserDO findByEmail(String email);
}
