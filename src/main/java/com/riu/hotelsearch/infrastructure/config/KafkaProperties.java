package com.riu.hotelsearch.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka")
public record KafkaProperties(Topics topics) {

    public KafkaProperties {
        if (topics == null) {
            topics = new Topics(null);
        }
    }

    public record Topics(String searches) {}
}
