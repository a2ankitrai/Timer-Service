package com.ank.timer.controller;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.dto.TimerInfoResponse;
import com.ank.timer.repository.TimerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimerControllerIT {

    private static final String SOME_URL = "https://www.google.com";
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private TimerController timerController;
    @Autowired
    private TimerRepository timerRepository;

    @BeforeEach
    public void setup() {
        timerRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void shouldCreateTimer() {
        TimerCreationRequest timerCreationRequest = TimerCreationRequest.builder()
                                                                        .hours("1")
                                                                        .minutes("2")
                                                                        .seconds("3")
                                                                        .url(SOME_URL)
                                                                        .build();

        ResponseEntity<TimerCreationResponse> response = timerController.createTimer(timerCreationRequest);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @SneakyThrows
    public void shouldGetTimerInfo() {
        TimerCreationRequest timerCreationRequest = TimerCreationRequest.builder()
                                                                        .hours("1")
                                                                        .minutes("2")
                                                                        .seconds("3")
                                                                        .url(SOME_URL)
                                                                        .build();

        ResponseEntity<TimerCreationResponse> response = timerController.createTimer(timerCreationRequest);

        String timerId = Objects.requireNonNull(response.getBody()).getId();

        ResponseEntity<TimerInfoResponse> timerInfoResponse = timerController.getTimerInfo(timerId);

        assertThat(timerInfoResponse).isNotNull();
        assertThat(timerInfoResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
