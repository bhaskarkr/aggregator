package com.catalog.aggregator.controller;

import jakarta.validation.constraints.NotBlank;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    private int count = 0;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/products/{productId}/users/{userId}/click")
    public String click(@NotBlank @PathVariable("productId") String productId,
                        @NotBlank @PathVariable("userId") String userId) {
        kafkaTemplate.send("userClick", productId + "_" + userId, "clicked" + count++);
        return "Clicked" + count;
    }
}
