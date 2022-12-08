package com.xinchen.project.core.cache;


import java.lang.reflect.Method;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 *
 * 默认的keyGenerator为{@link org.springframework.cache.interceptor.SimpleKeyGenerator}以传入参数作为key值
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/12/4 20:06
 */
@Component
class CacheKeyGenerator implements KeyGenerator {

  @Override
  public Object generate(Object target, Method method, Object... params) {
    return target.getClass().getSimpleName() + "_"
        + method.getName() + "_"
        + Stream.of(params).map(Object::toString).collect(Collectors.joining(","));
//        + StringUtils.arrayToDelimitedString(params, ",");
  }
}
