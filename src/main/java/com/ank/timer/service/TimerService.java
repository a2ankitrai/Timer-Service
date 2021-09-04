package com.ank.timer.service;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.dto.TimerInfoResponse;
import com.ank.timer.exception.TimerNotFoundException;
import com.ank.timer.model.State;
import com.ank.timer.model.Timer;
import com.ank.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;

    public TimerCreationResponse createTimer(TimerCreationRequest request) {

        Timer timer = mapTimerCreationRequest(request);

        timer = timerRepository.save(timer);
        log.debug("Persisted new timer with id : " + timer.getId().toString());

        return TimerCreationResponse.builder()
                                    .id(timer.getId().toString())
                                    .build();
    }

    public TimerInfoResponse findTimerInfo(String timerId) {

        if (!ObjectId.isValid(timerId))
            throw new TimerNotFoundException();

        Optional<Timer> timer = timerRepository.findTimerById(new ObjectId(timerId));

        return timer.map(this::mapTimer)
                    .orElseThrow(TimerNotFoundException::new);
    }

    private Timer mapTimerCreationRequest(TimerCreationRequest request) {

        Timer timer = Timer.builder()
                           .hours(Long.parseLong(request.getHours()))
                           .minutes(Long.parseLong(request.getMinutes()))
                           .seconds(Long.parseLong(request.getSeconds()))
                           .webhookUrl(request.getUrl())
                           .build();

        long currentTime = System.currentTimeMillis();
        long timerInSeconds = timer.getHours() * 60 + timer.getMinutes() * 60 + timer.getSeconds();
        long triggerTimestamp = currentTime + (timerInSeconds * 1000);

        timer.setTriggerTimeStamp(triggerTimestamp);
        timer.setTimerState(State.ACTIVE);
        return timer;
    }

    private TimerInfoResponse mapTimer(Timer timer) {

        TimerInfoResponse timerInfo = TimerInfoResponse.builder()
                                                       .id(timer.getId().toString())
                                                       .build();

        if (timer.getTimerState() == State.EXPIRED) {
            timerInfo.setTimeLeft(0L);
        } else {
            long currentTime = System.currentTimeMillis();
            long timerTriggerTimestamp = timer.getTriggerTimeStamp();
            long diffInSeconds = (timerTriggerTimestamp - currentTime) / 1000;
            timerInfo.setTimeLeft(diffInSeconds > 0 ? diffInSeconds : 0L);
        }

        return timerInfo;
    }
}
