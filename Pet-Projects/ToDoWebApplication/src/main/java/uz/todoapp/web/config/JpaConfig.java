package uz.todoapp.web.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "uz.todoapp.web.repository")
@EnableTransactionManagement
public class JpaConfig {
    // Additional JPA configurations can be added here
}
