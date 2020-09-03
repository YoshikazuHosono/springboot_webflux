package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SampleController2 {

    @Bean
    RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(
                RequestPredicates.GET("/sample2/hello"),
                request -> ServerResponse.ok().body(Flux.just("hello", "world"), String.class)
        );
    }

    @Bean
    RouterFunction<ServerResponse> routes2() {
        return RouterFunctions.route(
                RequestPredicates.GET("/sample2/hello2"),
                this::hello2
        );
    }

    Mono<ServerResponse> hello2(ServerRequest req) {
        return ServerResponse.ok().body(Flux.just("hello", "world", "2"), String.class);
    }

    @Bean
    RouterFunction<ServerResponse> routes3() {
        return RouterFunctions.route(
                RequestPredicates.GET("/sample2/hello3/{name}"),
                this::hello3
        );
    }

    Mono<ServerResponse> hello3(ServerRequest req) {
        return ServerResponse.ok().body(Flux.just("hello", "world", "3", req.pathVariable("name")), String.class);
    }

}
