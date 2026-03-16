package com.guce.application.infrastructure.config.kafka;

//import com.bforbank.common.error.model.EventProcessingFailure;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.lang.Nullable;

public class ConsumerDeadLetterPublishingRecoverer extends DeadLetterPublishingRecoverer {

  public ConsumerDeadLetterPublishingRecoverer(
    KafkaOperations<?, ?> template,
    BiFunction<ConsumerRecord<?, ?>, Exception, TopicPartition> destinationResolver) {
    super(template, destinationResolver);
  }

  @Override
  protected ProducerRecord<Object, Object> createProducerRecord(
    ConsumerRecord<?, ?> consumerRecord,
    TopicPartition topicPartition,
    Headers headers,
    byte[] key,
    byte[] value) {
    var eventKey =
      key.length > 0
        ? new String(key, StandardCharsets.UTF_8)
        : Optional.ofNullable(consumerRecord.key())
        .map(Object::toString)
        .orElse(UUID.randomUUID().toString().concat("-generated"));

//    EventProcessingFailure eventProcessingFailure =
//      computeEventProcessingFailureFrom(consumerRecord, headers, eventKey, value);

//    return new ProducerRecord<>(
//      topicPartition.topic(), 0, eventKey, eventProcessingFailure, headers);
    return null;
  }

//  private EventProcessingFailure computeEventProcessingFailureFrom(
//    ConsumerRecord<?, ?> message, Headers headers, String key, @Nullable byte[] value) {
//    Header exceptionMessageHeader = headers.lastHeader(KafkaHeaders.DLT_EXCEPTION_MESSAGE);
//    String exceptionMessage =
//      Objects.nonNull(exceptionMessageHeader) ? new String(exceptionMessageHeader.value()) : null;
//    Header exceptionStackTraceHeader = headers.lastHeader(KafkaHeaders.DLT_EXCEPTION_STACKTRACE);
//    String exceptionStackTrace =
//      Objects.nonNull(exceptionStackTraceHeader)
//        ? new String(exceptionStackTraceHeader.value())
//        : null;
//
//    return EventProcessingFailure.newBuilder()
//      .setRecordKeyAsString(key)
//      .setRecordKey(ByteBuffer.wrap(key.getBytes(StandardCharsets.UTF_8)))
//      .setRecordValueAsString(
//        Objects.nonNull(message.value()) ? message.value().toString() : null)
//      .setRecordValue(Objects.nonNull(value) ? ByteBuffer.wrap(value) : null)
//      .setOriginalTopic(message.topic())
//      .setOriginalPartition(message.partition())
//      .setOriginalOffset(message.offset())
//      .setErrorMessage(exceptionMessage)
//      .setStacktrace(exceptionStackTrace)
//      .build();
//  }
}
