package com.ank.timer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;

@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    @Value("${timer-scheduler.thread-pool.core-size}")
    private int threadPoolCoreSize;

    @Value("${timer-scheduler.thread-pool.max-size}")
    private int threadPoolMaxSize;

    @Bean(name = "threadPoolTaskExecutor")
    public Executor getAsyncExecutor() {
        var threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(threadPoolCoreSize);
        threadPoolExecutor.setMaxPoolSize(threadPoolMaxSize);
        threadPoolExecutor.setAllowCoreThreadTimeOut(true);

        return threadPoolExecutor;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
