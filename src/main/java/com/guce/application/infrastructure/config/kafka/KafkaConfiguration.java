package com.guce.application.infrastructure.config.kafka;

//import com.bforbank.common.error.model.EventProcessingFailure;

import com.guce.application.infrastructure.config.kafka.KafkaTemplateFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@Slf4j
public class KafkaConfiguration {

  private final KafkaProperties kafkaProperties;

  private final KafkaTemplateFactory kafkaTemplateFactory;

  @Value("${spring.kafka.consumer.retries-number:3}")
  private Long retriesNumber;

  @Value("${spring.kafka.consumer.retries-interval:1000}")
  private Long retriesIntervalInMs;

  @Value("${kafka.topic.processing-errors-dlq-topic-name}")
  private String processingErrorsDlqTopic;

  public KafkaConfiguration(KafkaProperties kafkaProperties) {
    this.kafkaProperties = kafkaProperties;
    this.kafkaTemplateFactory = new KafkaTemplateFactory(kafkaProperties);
  }

  // @Bean
  // public ConcurrentKafkaListenerContainerFactory
  // <String, Object>
  // concurrentKafkaListenerContainerFactory(
  // KafkaTemplate
  // <String, EventProcessingFailure> deadLetterTemplate) {
  // return this.getConcurrentKafkaListenerContainerFactory(deadLetterTemplate);
  // }
  //
  //
  // private <T> ConcurrentKafkaListenerContainerFactory
  // <String, T> getConcurrentKafkaListenerContainerFactory(
  // KafkaTemplate
  // <String, EventProcessingFailure> deadLetterTemplate) {
  //
  // var factory = new ConcurrentKafkaListenerContainerFactory
  // <String, T>();
  // factory.setConsumerFactory(
  // new
  // DefaultKafkaConsumerFactory<>(kafkaProperties.buildConsumerProperties(null)));
  //
  // var recoverer =
  // new ConsumerDeadLetterPublishingRecoverer(
  // deadLetterTemplate,
  // (record, exception) -> {
  // log.error(
  // "Retries exhausted for record with key [{}]. Sending to DLT topic [{}].",
  // record.key(),
  // processingErrorsDlqTopic,
  // exception);
  // return new TopicPartition(processingErrorsDlqTopic, 0);
  // });
  //
  // var errorHandler =
  // new DefaultErrorHandler(recoverer, new FixedBackOff(retriesIntervalInMs,
  // retriesNumber));
  // factory.setCommonErrorHandler(errorHandler);
  //
  // factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
  // return factory;
  // }
  //
  // @Bean
  // public KafkaTemplate
  // <String, EventProcessingFailure> deadLetterTemplate() {
  // return kafkaTemplateFactory.createTemplate();
  // }
}
