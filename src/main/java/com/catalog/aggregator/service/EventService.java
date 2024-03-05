package com.catalog.aggregator.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
public class EventService {

    @KafkaListener(topics = "userClick", groupId = "event_processor")
    public void listenUserClick(String message) {
        log.info("Received Message in group foo: " + message);
    }

    @KafkaListener(topics = "userClick-stores", groupId = "event_processor")
    public void listenUserClickStore(String message) {
        log.info("listenUserClickStore - Received Message in group event_processor: " + message);
    }
}
