package pl.romanek.blog.config;

import javax.sql.DataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class AppConfig {

    @Value("${spring.datasource.password}")
    String password;

    @Bean
    DataSource datasource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/blog");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password(password);
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");

        return dataSourceBuilder.build();
    }
}
