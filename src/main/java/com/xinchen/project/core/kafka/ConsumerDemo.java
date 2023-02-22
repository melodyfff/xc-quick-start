package com.xinchen.project.core.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.List;

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

  @KafkaListener(id="Hello3",topics = "hello",groupId = "OKOK3",containerFactory = "kafkaHelloContainerFactory")
  public void onMessage3(ConsumerRecord<?, ?> record, Acknowledgment ack){
    System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
    // ack-mode: manual_immediate ,手动提交开启时使用
    //确认收到消息
    ack.acknowledge();
  }

  @KafkaListener(id="Hello4",topics = "hello",groupId = "OKOK4",containerFactory = "kafkaHelloContainerFactory")
  public void onMessage4(ConsumerRecord<?, ?> record, Acknowledgment ack){
    System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
    // ack-mode: manual_immediate ,手动提交开启时使用
    //确认收到消息
    ack.acknowledge();
  }

  @KafkaListener(id="Hello5",topics = "hello",groupId = "OKOK5",containerFactory = "kafkaHelloContainerFactory")
  public void onMessage5(List<ConsumerRecord<?, ?>> records, Acknowledgment ack){
    // 批量消费
    // yml配置listener:type 要改为batch ,concurrency = "2" 启动多少线程执行，应小于等于broker数量，避免资源浪费
    // yml配置consumer：max-poll-records: ？？（每次拉取多少条数据消费）
    records.forEach(record->{
      // 可开线程池进行处理
      System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
    });

    // ack-mode: manual_immediate ,手动提交开启时使用
    //确认收到消息
    ack.acknowledge();
  }
}
