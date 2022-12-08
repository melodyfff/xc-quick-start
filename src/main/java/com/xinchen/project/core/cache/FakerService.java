package com.xinchen.project.core.cache;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

/**
 *
 * <p>@Cacheable 的逻辑是：查找缓存 - 有就返回 -没有就执行方法体 - 将结果缓存起来；</>
 *
 * <p>@CachePut 的逻辑是：执行方法体 - 将结果缓存起来；</>
 *
 * <p>所以 @Cacheable 适用于查询数据的方法，@CachePut 适用于更新数据的方法。</p>
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/4 18:29
 */
@Service
class FakerService {

  private static final Logger LOGGER = LoggerFactory.getLogger(FakerService.class);
  public void magicNoCache(){
    // do business ,sleep
    fakeBusiness();
  }

  @Cacheable(cacheNames = "fake",keyGenerator = "cacheKeyGenerator")
  public void magicQueryCache(){
    // do business ,sleep
    fakeBusiness();
  }

  @CachePut(cacheNames = "fake",keyGenerator = "cacheKeyGenerator")
  public void magicUpdateCache(){
    // do business ,sleep
    fakeBusiness();
  }

  private static void fakeBusiness() {
    StopWatch watch = new StopWatch();
    watch.start();
    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    watch.stop();
    LOGGER.info(">>> Cost {} ms",watch.getTotalTimeMillis());
  }
}
