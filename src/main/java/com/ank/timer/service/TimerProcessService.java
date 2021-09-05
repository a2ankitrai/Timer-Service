package com.ank.timer.service;

import com.ank.timer.model.State;
import com.ank.timer.model.Timer;
import com.ank.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class TimerProcessService {

    private final TimerRepository timerRepository;
    private final TimerWebhookClient timerWebhookClient;

    public void processTimers() {

        long currentTime = System.currentTimeMillis() / 1000;
        List<Timer> timerList =
                timerRepository
                        .findTimersByTriggerTimeStampIsLessThanEqualAndTimerState(currentTime, State.ACTIVE);

        if (timerList.isEmpty())
            return;

        for (Timer timer : timerList) {
            timer.setTimerState(State.EXPIRED);
        }

        timerRepository.saveAll(timerList);

        List<String> webhookUrls = timerList.stream()
                                            .map(this::createTargetUrl)
                                            .collect(Collectors.toList());
        shootWebhooks(webhookUrls);
    }

    private void shootWebhooks(List<String> webhookUrls) {

        List<CompletableFuture<Void>> futureList = new ArrayList<>();

        webhookUrls.forEach(url -> futureList.add(timerWebhookClient.fireWebhook(url)));

        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]))
                         .whenComplete((unused, throwable) -> {
                             final long processedTimers = futureList.stream()
                                                                    .filter(future -> !future
                                                                            .isCompletedExceptionally())
                                                                    .count();

                             log.info("Webhook firing completed successfully for {} timers", processedTimers);
                         });
    }

    private String createTargetUrl(Timer timer) {
        return timer.getWebhookUrl() + "/" + timer.getId().toString();
    }

}
