package org.fufeng.project.druid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author luocy
 * @description foo Service
 * @create 2021-05-16
 * @since 1.0
 */
@Component
public class FooService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Transactional
    public void selectUpdate(){
        jdbcTemplate.queryForObject("select id from foo where id=1 for update",Long.class);
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
