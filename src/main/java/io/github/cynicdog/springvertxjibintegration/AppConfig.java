package io.github.cynicdog.springvertxjibintegration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Configuration
public class AppConfig {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("spring_vertx_demo");

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return emf;
    }
}
