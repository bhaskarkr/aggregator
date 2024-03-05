package com.catalog.aggregator.configs;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC;
import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_TOPIC;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic userClickTopic() {
        return new NewTopic(PRODUCT_USER_CLICK_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic userClickStoreTopic() {
        return new NewTopic(PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC, 1, (short) 1);
    }
}
