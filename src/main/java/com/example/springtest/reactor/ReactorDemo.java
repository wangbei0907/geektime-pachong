package com.example.springtest.reactor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import reactor.core.publisher.Flux;

@Slf4j
//@SpringBootApplication
public class ReactorDemo implements ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ReactorDemo.class, args);
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Flux.range(1,6)
                .doOnRequest(c->log.info(" request {} number",c))
                .doOnComplete(()->log.info("published complete 1 "))
                .map(i->{
                    log.info("published Thread -{} {}",Thread.currentThread(),i);
                    return 10/i-3;
                })
                .doOnComplete(()->log.info("published complete2"))
                .onErrorReturn(-1)
                .subscribe(
                        c-> log.info("subscribe {} {}",Thread.currentThread(),c),
                        e -> log.error("error {}", e.toString()),
                        ()->log.info("subscribe complete")
                );
        Thread.sleep(2_000);
    }
}
