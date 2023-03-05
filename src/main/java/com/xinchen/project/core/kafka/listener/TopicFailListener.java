package com.xinchen.project.core.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 *
 * see 消息队列-kafka消费异常问题
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 19:20
 */
@Profile("kafka")
@Component
@Slf4j
class TopicFailListener {
  @KafkaListener(id="ThrowError",topics = "fail-topic",groupId = "ThrowErrorGroup")
  public void onMessage1(ConsumerRecord<?, ?> record, Consumer consumer){
    // 在不做任何处理的情况下，会自动重试10次，然后消息会被直接处理掉
    throw new RuntimeException("固定跑出异常");
  }
}
