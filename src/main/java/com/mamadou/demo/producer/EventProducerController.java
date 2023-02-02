package com.mamadou.demo.producer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Sinks;

@RestController
@Profile("producer")
@RequiredArgsConstructor
public class EventProducerController {
    private final Sinks.Many<Message<String>> many;

    @GetMapping("/event")
    public ResponseEntity<String> event(@RequestParam("q") String payload) {
        final var message = MessageBuilder.withPayload(payload.toUpperCase())
                                          .build();
        many.emitNext(message, Sinks.EmitFailureHandler.FAIL_FAST);
        return ResponseEntity.ok("Event Sent!");
    }
}
