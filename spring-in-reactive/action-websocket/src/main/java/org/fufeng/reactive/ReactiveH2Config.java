package org.fufeng.reactive;

import org.fufeng.r2dbc.entity.converter.UserDOWriteConveter;
import org.fufeng.r2dbc.entity.converter.UserDoReadConverter;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.convert.R2dbcCustomConversions;
import org.springframework.data.r2dbc.dialect.H2Dialect;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableR2dbcRepositories
@EnableTransactionManagement
public class ReactiveH2Config {
//    @Bean
//    public H2ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url("mem:stocktest;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
//                        .username("admin")
//                        .password("password")
//                        .build()
//        );
//    }

    @Bean
    public ConnectionFactoryInitializer connectionFactoryInitializer(ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
        populator.addPopulators(
                new ResourceDatabasePopulator(new ClassPathResource("db/schema.sql")),
                new ResourceDatabasePopulator(new ClassPathResource("db/data.sql"))
        );
        initializer.setDatabasePopulator(populator);
        return initializer;
    }

    @Bean
    public R2dbcCustomConversions customConverters() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new UserDoReadConverter());
        converters.add(new UserDOWriteConveter());
        //return new R2dbcCustomConversions(converters);
        return R2dbcCustomConversions.of(H2Dialect.INSTANCE , converters);
    }
}
