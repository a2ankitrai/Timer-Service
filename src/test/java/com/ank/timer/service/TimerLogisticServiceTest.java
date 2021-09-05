package com.ank.timer.service;

import com.ank.timer.dto.TimerCreationRequest;
import com.ank.timer.dto.TimerCreationResponse;
import com.ank.timer.dto.TimerInfoResponse;
import com.ank.timer.model.Timer;
import com.ank.timer.repository.TimerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TimerLogisticServiceTest {

    private static final String SOME_URL = "https://www.google.com";
    @Autowired
    private TimerLogisticService timerService;
    @Autowired
    private TimerRepository timerRepository;

    @Test
    public void shouldCreateTimer() {

        TimerCreationRequest timerCreationRequest = TimerCreationRequest.builder()
                                                                        .hours("1")
                                                                        .minutes("2")
                                                                        .seconds("3")
                                                                        .url(SOME_URL)
                                                                        .build();

        TimerCreationResponse timerCreationResponse = timerService.createTimer(timerCreationRequest);

        Optional<Timer> timerOptional = timerRepository.findTimerById(timerCreationResponse.getId());

        assertThat(timerOptional).isNotEmpty();

        Timer timer = timerOptional.get();
        assertThat(timer.getHours()).isEqualTo(1);
        assertThat(timer.getMinutes()).isEqualTo(2);
        assertThat(timer.getSeconds()).isEqualTo(3);
        assertThat(timer.getWebhookUrl()).isEqualTo(SOME_URL);
    }

    @Test
    public void shouldGetTimerInfo() {
        TimerCreationRequest timerCreationRequest = TimerCreationRequest.builder()
                                                                        .hours("0")
                                                                        .minutes("2")
                                                                        .seconds("4")
                                                                        .build();

        String id = timerService.createTimer(timerCreationRequest).getId();

        TimerInfoResponse timerInfoResponse = timerService.findTimerInfo(id);

        assertThat(timerInfoResponse).isNotNull();
    }
}
