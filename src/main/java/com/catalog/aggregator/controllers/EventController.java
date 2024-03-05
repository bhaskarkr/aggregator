package com.catalog.aggregator.controllers;

import com.catalog.aggregator.utils.EventUtils;
import com.catalog.aggregator.utils.ValidationUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_TOPIC;

@RestController
public class EventController {

    private int count = 0;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @GetMapping("/products/{productId}/users/{userId}/click")
    public String click(@NotBlank @PathVariable("productId") String productId,
                        @NotBlank @PathVariable("userId") String userId) {
        if(ValidationUtils.isInValidId(productId) || ValidationUtils.isInValidId(userId))
            throw new BadRequestException();
        kafkaTemplate.send(PRODUCT_USER_CLICK_TOPIC, EventUtils.getProductUserEventKey(productId, userId), userId + " clicked  on " + productId);
        return "Total click : " + count;
    }
}
