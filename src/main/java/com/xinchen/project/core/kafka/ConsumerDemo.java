package com.xinchen.project.core.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/29 14:02
 */
@Profile("kafka")
@Component
class ConsumerDemo {
  // 监听器
  @KafkaListener(id="Hello",topics = "hello",groupId = "CSCS")
  public void onMessage1(ConsumerRecord<?, ?> record, Consumer consumer){
    System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
    // ack-mode: manual_immediate ,手动提交开启时使用
    // consumer.commitAsync();
  }

  // 监听器
  @KafkaListener(id="Hello2",topics = "hello",groupId = "OKOK",containerFactory = "kafkaHelloContainerFactory")
  public void onMessage2(ConsumerRecord<?, ?> record, KafkaConsumer<String,String> consumer){
    System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
    // ack-mode: manual_immediate ,手动提交开启时使用
     consumer.commitAsync();
  }
}
