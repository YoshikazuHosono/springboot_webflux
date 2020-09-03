package com.example.demo.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sample3")
public class SampleController3 {

    static List<String> normalNameList = Arrays.asList("hosono", "yoshikazu");
    static Flux<String> fluxNameList = Flux.just("hosono", "yoshikazu");

    static Map<String, String> akaMap;

    static {
        akaMap = new HashMap<>();
        akaMap.put("hosono", "BlackThunder");
        akaMap.put("yoshikazu", "RedFighter");
    }

    @GetMapping("/test")
    Flux<String> test() {
        System.out.println("/test called");
        WebClient webClient = WebClient.builder().build();

        Flux<String> nameList = webClient.get()
                .uri("localhost:8080/sample3/nameList/flux")
                .retrieve()
                .bodyToFlux(String.class)
                .cache();

        System.out.println("check point 1");

        nameList.subscribe(s -> System.out.println("nameList subscribe : " + s));

        System.out.println("check point 2");

        Flux<String> akaNameList = nameList.flatMap(name -> {
            System.out.println("in flatMap : " + name);

            return webClient.get()
                    .uri("localhost:8080/sample3/aka/mono/" + name)
                    .retrieve()
                    .bodyToMono(String.class);
        }).cache();

        System.out.println("check point 3");

        akaNameList.subscribe(s -> System.out.println("akaNameList subscribe : " + s));

        System.out.println("check point 4");

        return akaNameList;
    }


    @GetMapping("/nameList")
    List<String> getNameList() {
        System.out.println("/nameList called");
        return normalNameList;
    }

    @GetMapping(value = "/nameList/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Flux<String> getFluxNameList() {
        System.out.println("/nameList/flux called");
        return fluxNameList;
    }

    @GetMapping("/aka/{name}")
    String getAka(@PathVariable String name) {
        System.out.println("/aka/" + name + " called");
        return akaMap.get(name);
    }

    @GetMapping(value = "/aka/mono/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    Mono<String> getMonoAka(@PathVariable String name) {
        System.out.println("/aka/mono/" + name + " called");
        return Mono.just(akaMap.get(name));
    }

}
