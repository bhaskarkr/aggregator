package com.catalog.aggregator.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC;
import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_TOPIC;

@Slf4j
@Service
public class EventListeners {
    @KafkaListener(topics = PRODUCT_USER_CLICK_TOPIC, groupId = "event_processor")
    public void listenUserClick(String message) {
        log.info("Received Message PRODUCT_USER_CLICK_TOPIC : " + message);
    }

    @KafkaListener(topics = PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC, groupId = "event_processor")
    public void listenUserClickStore(String message) {
        log.info("listenUserClickStore - Received Message in group event_processor: " + message);
    }
}
