package com.ank.timer.scheduler;

import com.ank.timer.service.TimerProcessService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/***
 *
 * scheduler which will run every second
 *
 * finds out the timers waiting to be triggered
 * calls the webhook urls, in an executor thread with timeout
 * and based on the response will update the activation status of timer.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class TimeoutScheduler {

    private final TimerProcessService timerProcessService;

    @Scheduled(cron = "${timer-scheduler.cron}")
    public void processTimers() {
        log.debug("Triggering scheduler at " + LocalDateTime.now());
        timerProcessService.processTimers();
    }
}
