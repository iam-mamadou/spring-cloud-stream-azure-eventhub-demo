package com.mamadou.demo.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Supplier;

@Slf4j
@Configuration
@Profile("producer")
public class EventHubProducerConfig {

    @Bean
    public Sinks.Many<Message<String>> many() {
        return Sinks.many().unicast().onBackpressureBuffer();
    }

    @Bean
    public Supplier<Flux<Message<String>>> producer(Sinks.Many<Message<String>> many) {
        return () -> many.asFlux()
                         .doOnNext(message -> log.info("Sending message: {}", message))
                         .doOnError(throwable -> log.error("Error occurred while sending message",  throwable));
    }
}
