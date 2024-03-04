package com.catalog.aggregator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventService {

    @KafkaListener(topics = "userClick", groupId = "event_processor")
    public void listenUserClick(String message) {
        log.info("Received Message in group foo: " + message);
    }
}
