package org.fufeng.project.druid;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @see DruidDataSource
 */
@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@Slf4j
public class DruidFilterApplication implements CommandLineRunner {
    @Resource
    private DataSource dataSource;

    @Resource
    private FooService fooService;

    public static void main(String[] args) {
        SpringApplication.run(DruidFilterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(dataSource.toString());
        new Thread(fooService::selectUpdate).start();
        new Thread(fooService::selectUpdate).start();
    }
}

