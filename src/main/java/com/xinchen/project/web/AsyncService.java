package com.xinchen.project.web;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/25 17:21
 */
@Service
@Slf4j

class AsyncService {
  @Async("threadPoolTaskExecutor")
  Future<String> bussiness(){

    try {
      //休眠3秒，模拟耗时的业务操作
      log.info("{} -> Run ",Thread.currentThread());
      //休眠3秒，模拟耗时的业务操作
      TimeUnit.SECONDS.sleep(3);
      log.info("{} -> End ",Thread.currentThread());
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    return new AsyncResult<>("ok");
  }
}
