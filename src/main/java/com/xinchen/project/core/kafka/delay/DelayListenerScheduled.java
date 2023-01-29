package com.xinchen.project.core.kafka.delay;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/29 13:40
 */
@Profile("kafka1")
@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
@EnableScheduling
class DelayListenerScheduled {
  private final KafkaListenerEndpointRegistry registry;

  DelayListenerScheduled(KafkaListenerEndpointRegistry registry) {
    this.registry = registry;
  }

  // 监听器
  @KafkaListener(id="timingConsumer",topics = "topic-test",containerFactory = "delayContainerFactory")
  public void onMessage1(ConsumerRecord<?, ?> record){
    System.out.println("消费成功："+record.topic()+"-"+record.partition()+"-"+record.value());
  }

  // 定时启动监听器

  @Scheduled(cron = "0/5 * * * * ? ")
  public void startListener() {
    // "timingConsumer"是@KafkaListener注解后面设置的监听器ID,标识这个监听器
    final MessageListenerContainer timingConsumer = registry.getListenerContainer("timingConsumer");
    if (null!=timingConsumer && !timingConsumer.isRunning()) {
      System.out.println("启动监听器...");
      timingConsumer.start();
    }
  }

  // 定时停止监听器
  @Scheduled(cron = "0/30 * * * * ? ")
  public void shutDownListener() {
    final MessageListenerContainer timingConsumer = registry.getListenerContainer("timingConsumer");
    if (null!=timingConsumer && timingConsumer.isRunning()){
      System.out.println("关闭监听器...");
      timingConsumer.pause();
    }
  }
}
