package com.xinchen.project.core.kafka.filter;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

/**
 *
 * 过滤出消息的KEY为NULL的
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/5 22:26
 */
@Component
class KeyIsNullFilter implements RecordFilterStrategy<String,String> {

  @Override
  public boolean filter(ConsumerRecord<String, String> consumerRecord) {
    return null!=consumerRecord.key();
  }

}
