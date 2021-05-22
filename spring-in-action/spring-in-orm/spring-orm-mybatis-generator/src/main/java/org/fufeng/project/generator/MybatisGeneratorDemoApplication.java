package org.fufeng.project.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
@MapperScan("org.fufeng.project.generator.mapper")
public class MybatisGeneratorDemoApplication implements ApplicationRunner {
	//@Autowired
	//private CoffeeMapper coffeeMapper;

	public static void main(String[] args) {
		SpringApplication.run(MybatisGeneratorDemoApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		generateArtifacts();
//		playWithArtifacts();
	}

	private void generateArtifacts() throws Exception {
		List<String> warnings = new ArrayList<>();
		ConfigurationParser cp = new ConfigurationParser(warnings);
		//File configFile = new File("/Users/magicLuoMacBook/software/java/ideawork/im/gitlab/spring-in-thinking/spring-in-action/spring-in-orm/spring-orm-mybatis-generator/src/main/resources/generatorConfig.xml");
		Configuration config = cp.parseConfiguration(
				this.getClass().getResourceAsStream("/generatorConfig.xml"));
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(new ProgressCallback() {
			@Override
			public void introspectionStarted(int totalTasks) {
				System.out.printf("introspectionStarted : %s\n",totalTasks);
			}

			@Override
			public void generationStarted(int totalTasks) {
				System.out.printf("generationStarted : %s\n",totalTasks);
			}

			@Override
			public void saveStarted(int totalTasks) {
				System.out.printf("saveStarted : %s\n",totalTasks);
			}

			@Override
			public void startTask(String taskName) {
				System.out.printf("startTask : %s\n",taskName);
			}

			@Override
			public void done() {
				System.out.println("完成");
			}

			@Override
			public void checkCancel() throws InterruptedException {
				System.out.println("取消");
			}
		});
	}

	/*private void playWithArtifacts() {
		Coffee espresso = new Coffee()
				.withName("espresso")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 20.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		coffeeMapper.insert(espresso);

		Coffee latte = new Coffee()
				.withName("latte")
				.withPrice(Money.of(CurrencyUnit.of("CNY"), 30.0))
				.withCreateTime(new Date())
				.withUpdateTime(new Date());
		coffeeMapper.insert(latte);

		Coffee s = coffeeMapper.selectByPrimaryKey(1L);
		log.info("Coffee {}", s);

		CoffeeExample example = new CoffeeExample();
		example.createCriteria().andNameEqualTo("latte");
		List<Coffee> list = coffeeMapper.selectByExample(example);
		list.forEach(e -> log.info("selectByExample: {}", e));
	}*/
}

