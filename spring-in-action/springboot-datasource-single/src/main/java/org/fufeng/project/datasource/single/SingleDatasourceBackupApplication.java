package org.fufeng.project.datasource.single;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author luocy
 * @description 单数据源支持
 * @create 2021-05-16
 * @since 1.0
 */
@Slf4j
@SpringBootApplication
public class SingleDatasourceBackupApplication implements CommandLineRunner {

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(SingleDatasourceBackupApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
    }

    private void showConnection() throws SQLException {
        log.info(dataSource.toString());
        final Connection connection = dataSource.getConnection();
        log.info(connection.toString());
        connection.close();
    }
}
