package com.xinchen.project.core.kafka.listener;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 20:54
 */
@Profile("kafka")
@Component
@Slf4j
class TopicHelloBatchListener {
  @KafkaListener(id="Hello-Batch",
      topics = "hello",
      groupId = "HelloBatchListenerGroup",
      batch = "true",
      concurrency = "1",
      containerFactory = "kafkaBatchContainerFactory",
      properties = {"max.poll.records=100"},
      clientIdPrefix = "Hello-Batch"
  )
  public void batchMessage(List<ConsumerRecord<?, ?>> records, Acknowledgment ack){
    // 批量消费
    // yml配置listener:type 要改为batch ,concurrency = "2" 启动多少线程执行，应小于等于broker数量，避免资源浪费
    // yml配置consumer：max-poll-records: ？？（每次拉取多少条数据消费）

    log.info("[Hello-Batch] 批量消费size: {} \n record: {}",records.size(),records);
    // ack-mode: manual_immediate ,手动提交开启时使用
    ack.acknowledge();
  }
}
