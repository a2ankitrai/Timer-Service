package com.ank.timer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimerInfoResponse {

    String id;

    @JsonProperty("time_left")
    Long timeLeft;

}
