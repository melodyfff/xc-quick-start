package com.xinchen.project.core.schedule;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.scheduling.support.PeriodicTrigger;
import org.springframework.stereotype.Component;

/**
 *
 * 参考：https://mp.weixin.qq.com/s/EP47FX907XrxIMzeVGK6bg
 *
 *
 * SpringBoot中通过注解 @Scheduled 注解的方法都是一个定时执行的任务, 默认都是单线程的.
 * 可以通过 调整以下配置改变调度线程。注：调度线程在被调度任务完成前不会空闲，若上次任务未完成，即使到了下一次调度时间，任务也不会重复调度
 * <pre>
 * # 线程池大小
 * spring.task.scheduling.pool.size=5
 * # 线程名前缀
 * spring.task.scheduling.thread-name-prefix=myScheduling-
 * </pre>
 *
 * 并行执行：@EnableAsync开启多线程 @Async标记其为一个异步任务
 * 通过修改以下配置调整线程：
 * <pre>
 * spring.task.execution.thread-name-prefix=mytask-
 * spring.task.execution.pool.core-size=5
 * </pre>
 *
 *
 * 以上配置只在使用原生Spring注解时生效
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @since 1.0 Created In 2022/11/15 21:47
 **/
@Profile("schedule")
@EnableScheduling
@Component
public class ScheduleTask implements SchedulingConfigurer {

  private final static String corn = "0/10 * * * * ?";
  private final static Long timer = 10000L;
  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    // 可通过以下代码设置自定义线程执行
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        executor.setThreadNamePrefix("scheduled-");// 设置定时任务线程名称的前缀
//        int corePoolSize  = 10; // 设置定时任务的核心线程数
//        taskRegistrar.setScheduler(Executors.newScheduledThreadPool(corePoolSize,executor));
    taskRegistrar.addTriggerTask(new Runnable() {
      @Override
      public void run() {
        System.out.format("[%s] - Current Time : %s \n", Thread.currentThread().getName(), LocalDateTime.now());
      }
    }, new Trigger() {
      @Override
      public Date nextExecutionTime(TriggerContext triggerContext) {
        // 使用CronTrigger触发器，可动态修改cron表达式来操作循环规则
//        CronTrigger cronTrigger = new CronTrigger(corn);
//        Date nextExecutionTime = cronTrigger.nextExecutionTime(triggerContext);

        // 使用不同的触发器，为设置循环时间的关键，区别于CronTrigger触发器，该触发器可随意设置循环间隔时间，单位为毫秒
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(timer);
        Date nextExecutionTime = periodicTrigger.nextExecutionTime(triggerContext);
        return nextExecutionTime;
      }
    });


  }
}
