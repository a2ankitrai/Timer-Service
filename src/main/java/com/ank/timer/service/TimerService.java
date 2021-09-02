package com.ank.timer.service;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.dto.TimerInfoResponse;
import com.ank.timer.exception.TimerNotFoundException;
import com.ank.timer.model.State;
import com.ank.timer.model.Timer;
import com.ank.timer.repository.TimerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TimerService {

    private final TimerRepository timerRepository;

    public TimerCreationResponse createTimer(TimerCreationRequest request) {

        Timer timer = mapTimerCreationRequest(request);
        timer = timerRepository.save(timer);

        return TimerCreationResponse.builder()
                                    .id(timer.getId().toString())
                                    .build();
    }

    public TimerInfoResponse findTimerInfo(String timerId) {

        Optional<Timer> timer = timerRepository.findTimerById(new ObjectId(timerId));

        return timer.map(this::mapTimer)
                    .orElseThrow(TimerNotFoundException::new);
    }

    private Timer mapTimerCreationRequest(TimerCreationRequest request) {

        Timer timer = Timer.builder()
                           .hours(request.getHours())
                           .minutes(request.getMinutes())
                           .seconds(request.getSeconds())
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
            timerInfo.setTimeLeft(0);
        } else {
            long currentTime = System.currentTimeMillis();
            long timerTriggerTimestamp = timer.getTriggerTimeStamp();
            long diffInSeconds = (currentTime - timerTriggerTimestamp) / 1000;
            timerInfo.setTimeLeft(diffInSeconds);
        }

        return timerInfo;
    }
}
