package com.catalog.aggregator.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {
    @GetMapping("/click")
    public String click() {
        return "Clicked";
    }
}
