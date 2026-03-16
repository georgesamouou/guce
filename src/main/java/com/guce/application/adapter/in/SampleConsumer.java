package com.guce.application.adapter.in;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

public class SampleConsumer {

  @KafkaListener(topics = "${physical_person_event-topic-name}")
  public void listen(ConsumerRecord<String, Object> consumerRecord, Acknowledgment ack) {
    //consume your events :)
    ack.acknowledge();
  }

}
