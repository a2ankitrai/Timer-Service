package com.ank.timer.controller;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.dto.TimerInfoResponse;
import com.ank.timer.service.TimerLogisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TimerController {

    private final TimerLogisticService timerService;

    @PostMapping("/timers")
    public ResponseEntity<TimerCreationResponse> createTimer(
            @RequestBody @Valid TimerCreationRequest timerRequestInput) {

        TimerCreationResponse timerCreationResponse = timerService.createTimer(timerRequestInput);

        return new ResponseEntity<>(timerCreationResponse, HttpStatus.CREATED);
    }

    @GetMapping("/timers/{timerId}")
    public ResponseEntity<TimerInfoResponse> getTimerInfo(@PathVariable("timerId") String timerId) {

        TimerInfoResponse timerInfoResponse = timerService.findTimerInfo(timerId);

        return new ResponseEntity<>(timerInfoResponse, HttpStatus.OK);
    }
}
