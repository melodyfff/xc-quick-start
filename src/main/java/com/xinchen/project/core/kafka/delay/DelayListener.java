package com.xinchen.project.core.kafka.delay;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;

/**
 *
 * 控制延时启动监听器消费消息
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/29 13:26
 */
@Profile("kafka1")
@Configuration
@AutoConfigureAfter(KafkaAutoConfiguration.class)
class DelayListener {
  private final ConsumerFactory consumerFactory;

  DelayListener(ConsumerFactory consumerFactory) {
    this.consumerFactory = consumerFactory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory delayContainerFactory() {
    ConcurrentKafkaListenerContainerFactory container = new ConcurrentKafkaListenerContainerFactory();
    container.setConsumerFactory(consumerFactory);
    //禁止KafkaListener自启动
    container.setAutoStartup(false);
    return container;
  }
}
