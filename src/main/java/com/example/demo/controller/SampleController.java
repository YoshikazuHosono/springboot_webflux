package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Stream;

@RestController
@RequestMapping("/sample1")
public class SampleController {

    @GetMapping("/hello/mono")
    Mono<String> getHelloMono() {
        return Mono.just("hello world");
    }

    @GetMapping("/hello/flux")
    Flux<String> getHelloFlux() {
        return Flux.just("hello", "world");
    }

    @GetMapping("/hello/flux/{name}")
    Flux<String> getHelloFlux(@PathVariable String name) {
        return Flux.just("hello", "world", name);
    }

    @GetMapping("/streamWithLimit")
    Flux<Map<String, Integer>> stream() {
        Stream<Integer> integerStream = Stream.iterate(0, i -> i + 1);

        return Flux.fromStream(integerStream.limit(10)).map(it -> Collections.singletonMap("value", it));
    }

}
