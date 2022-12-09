package com.xinchen.project.core.redis;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/9 18:26
 */
@Profile("redisson-client")
@Component
class MethodLockImpl implements MethodLock {

  private final RedissonClient redissonClient;

  MethodLockImpl(RedissonClient redissonClient) {
    this.redissonClient = redissonClient;
  }

  @Override
  public void lockExec(String lockName, Supplier<Void> method) {
    final RLock lock = redissonClient.getLock(lockName);
    try {
      lock.lock();
      method.get();
    } finally {
      if (lock.isLocked() && lock.isHeldByCurrentThread()){
        lock.unlock();
      }
    }
  }

  @Override
  public void tryLockExec(String lockName, Supplier<Void> method) {
    final RLock lock = redissonClient.getLock(lockName);
    try {
      if (lock.tryLock(0,5, TimeUnit.SECONDS)){
        method.get();
      }
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      if (lock.isLocked() && lock.isHeldByCurrentThread()){
        lock.unlock();
      }
    }
  }

  @Override
  public <R> R tryLockExecReturn(String lockName, Supplier<R> method) {
    final RLock lock = redissonClient.getLock(lockName);
    try {
      if (lock.tryLock(0,5, TimeUnit.SECONDS)){
        return method.get();
      }
      throw new RuntimeException("attempt get lock fail.");
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      if (lock.isLocked() && lock.isHeldByCurrentThread()){
        lock.unlock();
      }
    }
  }
}
