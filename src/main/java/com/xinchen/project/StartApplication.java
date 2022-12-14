package com.xinchen.project;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisLanguageDriverAutoConfiguration;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 *
 * 以下配置自己接管，目前先忽略自动注入
 * 忽略数据库的自动注入 {@link DataSourceAutoConfiguration}
 * 忽略mybatis自动开始注入 {@link MybatisAutoConfiguration} {@link MybatisLanguageDriverAutoConfiguration}
 * 忽略MongoDB自动注入 {@link MongoAutoConfiguration} ,{@link MongoDataAutoConfiguration}是配置多数据源时需要排除
 */
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MybatisAutoConfiguration.class, MybatisLanguageDriverAutoConfiguration.class,
        RedisAutoConfiguration.class,RedissonAutoConfiguration.class,
        MongoAutoConfiguration.class, MongoDataAutoConfiguration.class,
})
public class StartApplication {

  public static void main(String[] args) {
    SpringApplication.run(StartApplication.class, args);
  }

}
