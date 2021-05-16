package org.fufeng.project;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
@Slf4j
public class DeclarativeTransactionApplication implements CommandLineRunner {
	@Autowired
	private FooService fooService;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(DeclarativeTransactionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fooService.insertRecord();
		log.info("1-java {}",
				jdbcTemplate
						.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='java'", Long.class));
		try {
			fooService.insertThenRollback();
		} catch (Exception e) {
			log.info("1-go {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='go'", Long.class));
		}

		try {
			// 可以成功回滚
			fooService.invokeBeanInsertThenRollback();
		} catch (Exception e) {
			log.info("3-go {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='go'", Long.class));
		}

		try {
			fooService.invokeInsertThenRollback();
		} catch (Exception e) {
			log.info("2-go {}",
					jdbcTemplate
							.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='go'", Long.class));
		}

		try {
			fooService.insertThenRollbackMainPropagation();
		} catch (Exception e) {

		}
		log.info("4-go {}",
				jdbcTemplate
						.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='4-go'", Long.class));
		log.info("5-go {}",
				jdbcTemplate
						.queryForObject("SELECT COUNT(*) FROM FOO WHERE BAR='5-go'", Long.class));
	}
}

