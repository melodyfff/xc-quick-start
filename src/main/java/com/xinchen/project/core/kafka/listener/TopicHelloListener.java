package com.xinchen.project.core.kafka.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 *
 * @Payload：获取的是消息的消息体，也就是发送内容
 *
 * @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY)：获取发送消息的key
 *
 * @Header(KafkaHeaders.RECEIVED_PARTITION_ID)：获取当前消息是从哪个分区中监听到的
 *
 * @Header(KafkaHeaders.RECEIVED_TOPIC)：获取监听的TopicName
 *
 * @Header(KafkaHeaders.RECEIVED_TIMESTAMP)：获取时间戳
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 19:11
 */
@Profile("kafka")
@Component
@Slf4j
class TopicHelloListener {
  // 监听器
  @KafkaListener(id="Hello",
      topics = "hello",
      groupId = "HelloListenerGroup",
      concurrency = "1")
  public void onMessage1(ConsumerRecord<?, ?> record, Consumer<?,?> consumer){
    log.info("[Hello] record: {} \n consumer: {}",record,consumer);
    // ack-mode: manual_immediate ,手动提交开启时使用
    // consumer.commitAsync();
  }

  @KafkaListener(id="Hello2",
      topics = "hello",
      groupId = "HelloListenerGroup2",
      concurrency = "1")
  public void onMessage2(@Payload String message,
      @Header(KafkaHeaders.RECEIVED_TOPIC)List<String> topics,
      @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
      @Header(KafkaHeaders.OFFSET) List<Long> offsets,
      @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
      @Header(KafkaHeaders.RECEIVED_TIMESTAMP) String time
      ){
    log.info("[Hello2] key:{} ,message: {} , time:{}, topic: {},partitions:{},offsets:{}",key,message,time,topics,partitions,offsets);
  }

}
