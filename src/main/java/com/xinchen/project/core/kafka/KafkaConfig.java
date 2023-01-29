package com.xinchen.project.core.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

/**
 *
 * AT-LEAST-ONCE:
 *
 * AT-MOST-ONCE:
 *
 * EXACTLY-ONCE:
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/1/28 17:58
 */
@Profile("kafka")
@EnableKafka
@Configuration
class KafkaConfig {
  @Bean
  public KafkaAdmin admin(KafkaProperties properties){
    // see: org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration.kafkaAdmin()
    KafkaAdmin admin = new KafkaAdmin(properties.buildAdminProperties());
    admin.setFatalIfBrokerNotAvailable(true);
    return admin;
  }

  @Bean
  public NewTopic topic2() {
    // 创建topic，需要指定创建的topic的"名称"、"分区数"、"副本数量(副本数数目设置要小于Broker数量)"
    // return new NewTopic("topic-kl", 1, (short) 1);
    return TopicBuilder.name("topic-test") // 名称
        .partitions(1) // 分区
        .replicas(1)   // 副本
        //  .compact() // 压缩策略，当该策略开启，key不能为null
        .build();
  }
}
