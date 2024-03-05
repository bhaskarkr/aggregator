package com.catalog.aggregator.configs;

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
        KStream<String, String> clicks = builder.stream("userClick");
        clicks.foreach((key, value) -> log.info("Key#####: " + key + ", Value####: " + value));
        KTable<Windowed<String>, Long> clickCounts = clicks
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                .count(Materialized.<String, Long, WindowStore<Bytes, byte[]>>as("userClick-stores")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(Serdes.Long()));
        clickCounts.toStream().foreach((key, value) -> {
            if (value >= 5) {
                // Construct the message you want to send
                log.info("Found 5 times");
                String message = "User " + key.key() + " clicked 5 times in the last 5 minutes.";
                // Publish to the new topic
                kafkaTemplate.send("userClick-stores", key.key(), message);
            }
        });
        return clicks;
    }

}
