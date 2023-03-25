package com.xinchen.project.web;

import com.xinchen.project.core.common.Result;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * 参考： SpringBoot的controller为什么不能并行执行？同一个浏览器连续多次访问同一个url竟然是串行的 ： https://blog.csdn.net/linxingliang/article/details/111300684
 *
 * 异步响应
 *
 * 同一个浏览器同时访问同样的url，串行响应
 * 如：
 * http://localhost:8080/async
 *
 *
 * 同一个浏览器同时访问同样的url,带参不同，并行响应
 * 如：
 * http://localhost:8080/async?ok=1
 * http://localhost:8080/async?ok=2
 *
 * 不同浏览器同时请求同样url，并行响应
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2023/3/25 16:35
 */
@RestController
@Slf4j
class AsyncController {

  private final AsyncService service;

  AsyncController(AsyncService service) {
    this.service = service;
  }

  @ResponseBody
  @RequestMapping("/sync")
  public Result<String> sync() throws InterruptedException {
    StopWatch stopWatch = new StopWatch("同步响应任务");
    stopWatch.start();
    log.info("{} -> Start",Thread.currentThread());
    //休眠3秒，模拟耗时的业务操作
    TimeUnit.SECONDS.sleep(3);
    stopWatch.stop();
    log.info("{} -> End,Cost : {} ms",Thread.currentThread(),stopWatch.getTotalTimeMillis());
    return Result.success("ok");
  }

  @RequestMapping("/async")
  public Result<String> async() throws InterruptedException, ExecutionException, TimeoutException {
    StopWatch stopWatch = new StopWatch("异步响应任务");
    stopWatch.start();
    log.info("{} -> Start",Thread.currentThread());

    Future<String> n = service.bussiness();

    stopWatch.stop();
    log.info("{} -> End,Cost : {} ms",Thread.currentThread(),stopWatch.getTotalTimeMillis());
    return Result.success(n.get());
  }

  @RequestMapping("/async/timeout")
  public Result<String> asyncTimeout() throws InterruptedException, ExecutionException, TimeoutException {
    StopWatch stopWatch = new StopWatch("异步超时响应任务");
    stopWatch.start();
    log.info("{} -> Start",Thread.currentThread());

    Future<String> n = service.bussiness();

    stopWatch.stop();
    log.info("{} -> End,Cost : {} ms",Thread.currentThread(),stopWatch.getTotalTimeMillis());
    return Result.success(n.get(1,TimeUnit.SECONDS));
  }
}
