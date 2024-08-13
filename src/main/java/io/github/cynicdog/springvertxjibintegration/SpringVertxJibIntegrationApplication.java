package io.github.cynicdog.springvertxjibintegration;

import io.github.cynicdog.springvertxjibintegration.route.ServerVerticle;
import io.vertx.core.Vertx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.annotation.PostConstruct;

@EntityScan("io.github.cynicdog.springvertxjibintegration.entity")
@SpringBootApplication
public class SpringVertxJibIntegrationApplication {

    @Autowired
    private ServerVerticle serverVerticle;

    public static void main(String[] args) {
        SpringApplication.run(SpringVertxJibIntegrationApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {

        final Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(serverVerticle);
    }
}
