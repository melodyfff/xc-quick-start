package com.xinchen.project.core.redis;

import java.util.function.Supplier;

/**
 *
 * 方法执行
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/9 18:24
 */
public interface MethodLock {
  void lockExec(String lockName, Supplier<Void> method);
  void tryLockExec(String lockName, Supplier<Void> method);
  <R> R tryLockExecReturn(String lockName, Supplier<R> method);
}
