package io.github.cynicdog.springvertxjibintegration.verticles.api;

import io.github.cynicdog.springvertxjibintegration.entity.User;
import io.github.cynicdog.springvertxjibintegration.utils.JpaOperationUtil;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.stream.Collectors;

public class UserAPI {

    final Vertx vertx;
    final EntityManagerFactory entityManagerFactory;

    public UserAPI(Vertx vertx, EntityManagerFactory entityManagerFactory) {
        this.vertx = vertx;
        this.entityManagerFactory = entityManagerFactory;
    }

    public void getUsernames(RoutingContext ctx) {
        var result = JpaOperationUtil.apply(entityManagerFactory, em ->
                em.createQuery("select u.name from User u", String.class)
                        .getResultList()
        );

        String usernames = result.stream()
                .collect(Collectors.joining(", "));

        ctx.response()
                .putHeader("content-type", "text/plain")
                .end(usernames);
    }

    public void getUsers(RoutingContext ctx) {
        var result = JpaOperationUtil.apply(entityManagerFactory, em ->
                em.createQuery("select u from User u", User.class)
                        .getResultList()
        );

        ctx.response()
                .putHeader("content-type", "text/plain")
                .end(JsonObject.of("data", result).encode());
    }
}
