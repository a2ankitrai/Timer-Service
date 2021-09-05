package com.ank.timer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimerWebhookClient {

    private final RestTemplate restTemplate;

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<Void> fireWebhook(String url) {

        try {
            restTemplate.exchange(url,
                                  HttpMethod.POST,
                                  new HttpEntity<>(""),
                                  Object.class);

            return CompletableFuture.completedFuture(null);
        } catch (RuntimeException ex) {
            log.error("Exception occurred while firing webhook " + ex.getMessage());
            return CompletableFuture.failedFuture(ex);
        }

    }

}
