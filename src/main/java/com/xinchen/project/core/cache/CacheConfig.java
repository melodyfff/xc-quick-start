package com.xinchen.project.core.cache;

import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheInterceptor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 相关默认：
 * CacheResolver：{@link SimpleCacheResolver} 本质为ConcurrentHashMap 内存存储
 * KeyGenerator： {@link SimpleKeyGenerator}
 *
 * see {@link CachingConfigurer} that wish or need to specify explicitly how caches are resolved and how keys are generated for annotation-driven cache management.
 *
 * <p>If both a {@link #cacheManager()} and {@code #cacheResolver()} are set,the cache manager is ignored.
 *
 *
 * <p>@Cacheable 的逻辑是：查找缓存 - 有就返回 -没有就执行方法体 - 将结果缓存起来；</>
 * <p>@CachePut 的逻辑是：执行方法体 - 将结果缓存起来；</>
 *
 * 查询： @Cacheable
 * 新增： @CachePut
 * 更新或者删除： @CacheEvict
 *
 * {@link CacheInterceptor} AOP拦截器时缓存相关
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/4 16:21
 */
@Configuration
@EnableCaching(mode = AdviceMode.PROXY)
class CacheConfig extends CachingConfigurerSupport {



  @Override
  public KeyGenerator keyGenerator() {
    return super.keyGenerator();
  }
}
