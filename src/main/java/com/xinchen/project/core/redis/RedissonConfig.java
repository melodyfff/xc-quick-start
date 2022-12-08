package com.xinchen.project.core.redis;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Redisson连接
 * <P>
 *   link https://github.com/redisson/redisson/
 * </P>
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/8 16:05
 */
@Profile("redis-redisson")
@Configuration
@Import({RedisAutoConfiguration.class,RedissonAutoConfiguration.class,})
public class RedissonConfig {
}
