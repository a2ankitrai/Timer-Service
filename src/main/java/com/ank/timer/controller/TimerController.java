package com.ank.timer.controller;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.service.TimerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class TimerController {

    private final TimerService timerService;

    @PostMapping("/timers")
    public ResponseEntity<TimerCreationResponse> createTimer(
            @RequestBody @Valid TimerCreationRequest timerRequestInput) {

        TimerCreationResponse timerCreationResponse = timerService.createTimer(timerRequestInput);

        return new ResponseEntity<>(timerCreationResponse, HttpStatus.OK);
    }

    @GetMapping("/timers/{timerId}")
    public ResponseEntity<String> getTimerInfo(@RequestParam("timerId") String timerId) {

        return null;
    }
}
