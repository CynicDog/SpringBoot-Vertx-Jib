package io.github.cynicdog.springvertxjibintegration.route;

import io.github.cynicdog.springvertxjibintegration.route.api.UserAPI;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManagerFactory;

@Component
public class ServerVerticle extends AbstractVerticle {
    private final Logger logger = Logger.getLogger(ServerVerticle.class);
    private final Integer defaultPort = 8888;

    private final EntityManagerFactory emf;

    public ServerVerticle(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void start() throws Exception {

        Router router = Router.router(vertx);

        // logging filter
        router.route().handler(ctx -> {
            logger.info("Request URI: " + ctx.request().uri());
            ctx.next();
        });

        // User API routes registry
        var userAPI = new UserAPI(vertx, emf);
        router.get("/users").handler(userAPI::getUsers);
        router.get("/usernames").handler(userAPI::getUsernames);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(config().getInteger("http.port", defaultPort), result -> {
                    if (result.succeeded()) {
                        System.out.println("Server started on port " + defaultPort);
                    } else {
                        System.err.println("Failed to start server: " + result.cause());
                    }
                });

        super.start();
    }
}
