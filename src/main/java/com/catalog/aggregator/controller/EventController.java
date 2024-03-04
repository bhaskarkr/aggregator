package com.catalog.aggregator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/click")
    public String click() {
        kafkaTemplate.send("userClick", "clicked");
        return "Clicked";
    }
}
