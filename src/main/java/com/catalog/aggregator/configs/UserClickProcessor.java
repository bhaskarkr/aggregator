package com.catalog.aggregator.configs;

import com.catalog.aggregator.utils.EventUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.WindowStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC;
import static com.catalog.aggregator.utils.Constants.PRODUCT_USER_CLICK_TOPIC;

@Component
@Slf4j
public class UserClickProcessor {


    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public UserClickProcessor(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Autowired
    public KStream<String, String> kStream(@Qualifier("defaultKafkaStreamsBuilder") StreamsBuilder builder) {
        KStream<String, String> clicks = builder.stream(PRODUCT_USER_CLICK_TOPIC);
        KTable<Windowed<String>, Long> clickCounts = clicks
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .count(Materialized.<String, Long, WindowStore<Bytes, byte[]>>as("userClick-stores")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));
        clickCounts.toStream().foreach((key, value) -> {
            if (value >= 5) {
                String userId = EventUtils.getUserIdFromEventKey(key.key());
                String productId = EventUtils.getProductIdFromEventKey(key.key());
                String message = "User " + userId + " clicked 5 times on product "+ productId+ " in the last 5 seconds.";
                kafkaTemplate.send(PRODUCT_USER_CLICK_ANALYTIC_OUTPUT_TOPIC, key.key(), message);
            }
        });
        return clicks;
    }

}
