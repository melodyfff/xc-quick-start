package com.xinchen.project.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.async.TimeoutCallableProcessingInterceptor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 *
 * 配置全局序列化使用gson
 *
 * @author Xin Chen (xinchenmelody@gmail.com)
 * @version 1.0
 * @date Created In 2022/8/14 14:40
 */
@Configuration
public class ConfigWebMvc implements WebMvcConfigurer {

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        // 支持异步处理
        // 60S超时
        configurer.setDefaultTimeout(60 * 1000L);
        configurer.registerCallableInterceptors(timeoutInterceptor());
        configurer.setTaskExecutor(threadPoolTaskExecutor());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
    }

    @Bean
    GsonHttpMessageConverter gsonHttpMessageConverter(){
        final GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss SSS").create();
        gsonHttpMessageConverter.setGson(gson);
        return gsonHttpMessageConverter;
    }

    @Bean
    public TimeoutCallableProcessingInterceptor timeoutInterceptor() {
        return new TimeoutCallableProcessingInterceptor();
    }
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        return new TaskExecutorBuilder()
            .threadNamePrefix("Async-")
            .corePoolSize(10)
            .maxPoolSize(100)
            .queueCapacity(20)
            .build();
    }
}
