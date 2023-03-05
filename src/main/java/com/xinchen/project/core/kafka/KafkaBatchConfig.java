package com.xinchen.project.core.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.ContainerProperties.AckMode;

/**
 *
 * 批量提交配置
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 21:06
 */
@Profile("kafka")
@Configuration
class KafkaBatchConfig {
  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean(name = "kafkaBatchTemplate")
  public KafkaTemplate<String, String> kafkaBatchTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }
  @Bean(name = "kafkaBatchContainerFactory")
  KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Integer, String>> kafkaHelloContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory());
    factory.setConcurrency(3);
    // yml配置listener:type 要改为batch
    factory.setBatchListener(true);
    // Listener配置
    factory.getContainerProperties().setPollTimeout(3000);
    // 开启手动提交确认，确保ENABLE_AUTO_COMMIT_CONFIG为false
    factory.getContainerProperties().setAckMode(AckMode.MANUAL);
    factory.getContainerProperties().setPollTimeout(15000);
    // 老的版本这样设置
    // factory.getContainerProperties().setBatchErrorHandler(new BatchLoggingErrorHandler());
//    factory.setCommonErrorHandler(new CommonLoggingErrorHandler());
    return factory;
  }

  private ProducerFactory<String, String> producerFactory() {
    return new DefaultKafkaProducerFactory<>(producerConfigs());
  }

  private ConsumerFactory<Integer, String> consumerFactory() {
    // 这里可以通过接受KafkaProperties properties ,从而在application.yml中配置
    return new DefaultKafkaConsumerFactory<>(consumerConfigs());
  }

  private Map<String, Object> producerConfigs() {
    // 这里可以通过接受KafkaProperties properties ,从而在application.yml中配置
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.RETRIES_CONFIG, 10);
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.BATCH_SIZE_CONFIG, 1000);
    props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return props;
  }

  private Map<String, Object> consumerConfigs() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "Customer-Batch-Consumer");
    // 开启自动提交,这里设置为false主要是观察ack手动提交
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    return props;
  }
}
