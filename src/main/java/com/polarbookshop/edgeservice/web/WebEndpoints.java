package com.polarbookshop.edgeservice.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;
import static org.springframework.web.reactive.function.server.ServerResponse.status;

@Configuration
public class WebEndpoints {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return route()
                .GET("/catalog-fallback", request ->
                        ok().body(Mono.just(""), String.class))
                .POST("/catalog-fallback", request ->
                        status(SERVICE_UNAVAILABLE).build())
                .build();
    }
}
