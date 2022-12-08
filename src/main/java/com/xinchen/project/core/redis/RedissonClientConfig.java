package com.xinchen.project.core.redis;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties.Sentinel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.util.ReflectionUtils;

/**
 *
 * @see {@link RedissonAutoConfiguration}
 *
 * // 锁的使用
 * RLock lock = redissonClient.getLock("stock:" + productId);
 *
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/8 21:29
 */
@Profile("redisson-client")
@Configuration
@Import({RedisAutoConfiguration.class})
@AutoConfigureBefore(RedisAutoConfiguration.class)
class RedissonClientConfig {
  private static final String REDIS_PROTOCOL_PREFIX = "redis://";
  private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

  @Bean(destroyMethod = "shutdown")
  public RedissonClient redissonClient(RedisProperties redisProperties) {
    Config config;
    Method clusterMethod = ReflectionUtils.findMethod(
        org.springframework.boot.autoconfigure.data.redis.RedisProperties.class, "getCluster");
    Method usernameMethod = ReflectionUtils.findMethod(
        org.springframework.boot.autoconfigure.data.redis.RedisProperties.class, "getUsername");
    Method timeoutMethod = ReflectionUtils.findMethod(
        org.springframework.boot.autoconfigure.data.redis.RedisProperties.class, "getTimeout");
    Object timeoutValue = ReflectionUtils.invokeMethod(timeoutMethod, redisProperties);
    int timeout;
    if(null == timeoutValue){
      timeout = 10000;
    }else if (!(timeoutValue instanceof Integer)) {
      Method millisMethod = ReflectionUtils.findMethod(timeoutValue.getClass(), "toMillis");
      timeout = ((Long) ReflectionUtils.invokeMethod(millisMethod, timeoutValue)).intValue();
    } else {
      timeout = (Integer)timeoutValue;
    }

    String username = null;
    if (usernameMethod != null) {
      username = (String) ReflectionUtils.invokeMethod(usernameMethod, redisProperties);
    }

    if (redisProperties.getSentinel() != null) {
      Method nodesMethod = ReflectionUtils.findMethod(Sentinel.class, "getNodes");
      Object nodesValue = ReflectionUtils.invokeMethod(nodesMethod, redisProperties.getSentinel());

      String[] nodes;
      if (nodesValue instanceof String) {
        nodes = convert(Arrays.asList(((String)nodesValue).split(",")));
      } else {
        nodes = convert((List<String>)nodesValue);
      }

      config = new Config();
      config.useSentinelServers()
          .setMasterName(redisProperties.getSentinel().getMaster())
          .addSentinelAddress(nodes)
          .setDatabase(redisProperties.getDatabase())
          .setConnectTimeout(timeout)
          .setUsername(username)
          .setPassword(redisProperties.getPassword());
    } else if (clusterMethod != null && ReflectionUtils.invokeMethod(clusterMethod, redisProperties) != null) {
      Object clusterObject = ReflectionUtils.invokeMethod(clusterMethod, redisProperties);
      Method nodesMethod = ReflectionUtils.findMethod(clusterObject.getClass(), "getNodes");
      List<String> nodesObject = (List<String>) ReflectionUtils.invokeMethod(nodesMethod, clusterObject);

      String[] nodes = convert(nodesObject);

      config = new Config();
      config.useClusterServers()
          .addNodeAddress(nodes)
          .setConnectTimeout(timeout)
          .setUsername(username)
          .setPassword(redisProperties.getPassword());
    } else {
      config = new Config();
      String prefix = REDIS_PROTOCOL_PREFIX;
      Method method = ReflectionUtils.findMethod(RedisProperties.class, "isSsl");
      if (method != null && (Boolean)ReflectionUtils.invokeMethod(method, redisProperties)) {
        prefix = REDISS_PROTOCOL_PREFIX;
      }

      config.useSingleServer()
          .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
          .setConnectTimeout(timeout)
          .setDatabase(redisProperties.getDatabase())
          .setUsername(username)
          .setPassword(redisProperties.getPassword());
    }
    return Redisson.create(config);
  }

  private String[] convert(List<String> nodesObject) {
    List<String> nodes = new ArrayList<String>(nodesObject.size());
    for (String node : nodesObject) {
      if (!node.startsWith(REDIS_PROTOCOL_PREFIX) && !node.startsWith(REDISS_PROTOCOL_PREFIX)) {
        nodes.add(REDIS_PROTOCOL_PREFIX + node);
      } else {
        nodes.add(node);
      }
    }
    return nodes.toArray(new String[nodes.size()]);
  }
}
