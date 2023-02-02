package com.mamadou.demo.consumer;

import com.azure.core.util.BinaryData;
import com.azure.spring.messaging.checkpoint.Checkpointer;
import com.azure.spring.messaging.eventhubs.support.EventHubsHeaders;
import com.azure.storage.blob.BlobContainerClient;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

import static com.azure.spring.messaging.AzureHeaders.CHECKPOINTER;

@Slf4j
@Component("consumer")
@Profile("consumer")
@RequiredArgsConstructor
public class EventHubConsumer implements Consumer<Message<String>> {

    @SneakyThrows
    @Override
    public void accept(Message<String> message) {
        final var checkpointer = (Checkpointer) message.getHeaders().get(CHECKPOINTER);
        log.info("New message received: '{}', partition key: {}, sequence number: {}, offset: {}, enqueued time: {}",
                message.getPayload(),
                message.getHeaders().get(EventHubsHeaders.PARTITION_KEY),
                message.getHeaders().get(EventHubsHeaders.SEQUENCE_NUMBER),
                message.getHeaders().get(EventHubsHeaders.OFFSET),
                message.getHeaders().get(EventHubsHeaders.ENQUEUED_TIME)
        );
        checkpointer.success()
                    .doOnSuccess(success -> log.info("Message '{}' successfully checkpointed", message.getPayload()))
                    .doOnError(error -> log.error("Exception found", error))
                    .block();
    }

}
