package com.guce.application.infrastructure.config.kafka;

import java.util.Map;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaTemplateFactory {
  private final KafkaProperties kafkaProperties;

  public KafkaTemplateFactory(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
  }

  public <T> KafkaTemplate<String, T> createTemplate() {
    Map<String, Object> props = kafkaProperties.buildProducerProperties();
    ProducerFactory<String, T> factory = new DefaultKafkaProducerFactory<>(props);
    return new KafkaTemplate<>(factory);
  }
}
