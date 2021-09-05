package com.ank.timer.controller;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.repository.TimerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TimerControllerIT {

    private static final String SOME_URL = "https://www.google.com";
    static String TIMER_PATH = "/timers";

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TimerRepository timerRepository;

    private final ObjectMapper mapper = new ObjectMapper();
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

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(timerCreationRequest), headers);

        ResponseEntity<Object> response = restTemplate.exchange(TIMER_PATH, HttpMethod.POST,
                                                                new HttpEntity<>(request),
                                                                Object.class);

        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }


}
