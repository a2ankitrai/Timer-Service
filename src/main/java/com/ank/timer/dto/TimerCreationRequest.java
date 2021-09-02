package com.ank.timer.dto;

import com.ank.timer.validator.Url;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class TimerCreationRequest {

    @NotNull
    @Min(0)
    private long hours;

    @NotNull
    @Min(0)
    private long minutes;

    @NotNull
    @Min(0)
    private long seconds;

    @NotNull
    @Url
    private String url;
}
