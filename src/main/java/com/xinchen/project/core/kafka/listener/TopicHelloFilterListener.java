package com.xinchen.project.core.kafka.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *
 * 过滤策略过滤
 * see {@link RecordFilterStrategy}
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 19:11
 */
@Profile("kafka")
@Component
@Slf4j
class TopicHelloFilterListener {

  @KafkaListener(id="HelloFilter",
      topics = "hello",
      groupId = "HelloFilterGroup",
      filter = "keyIsNullFilter",
      concurrency = "1")
  public void onMessage2(@Payload String message,
      @Header(KafkaHeaders.RECEIVED_TOPIC)List<String> topics,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets,
      @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String time
      ){
    log.info("[HelloFilter] message: {} , time:{}, topic: {},partitions:{},offsets:{}",message,time,topics,partitions,offsets);
  }

}
