package com.ank.timer.dto;

import com.ank.timer.validator.annotation.Numeric;
import com.ank.timer.validator.annotation.Url;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TimerCreationRequest implements Serializable {

//    @NotNull
//    @Numeric
//    @PositiveOrZero
    @JsonProperty("hours")
    private String hours;

//    @NotNull
//    @Numeric
//    @PositiveOrZero
@JsonProperty("minutes")
    private String minutes;

//    @NotNull
//    @Numeric
//    @PositiveOrZero
@JsonProperty("seconds")
    private String seconds;

//    @NotNull
//    @Url
@JsonProperty("url")
    private String url;
}
