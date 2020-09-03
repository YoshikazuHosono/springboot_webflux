package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/sample1")
public class SampleController {

    @GetMapping("/hello/mono")
    Mono<String> getHelloMono() {
        return Mono.just("hello world");
    }

    @GetMapping(value = "/hello/flux")
    Flux<String> getHelloFlux() {
        return Flux.just("hello", "world");
    }

    @GetMapping(value = "/hello/flux/{name}")
    Flux<String> getHelloFlux(@PathVariable String name) {
        return Flux.just("hello", "world", name);
    }

}
