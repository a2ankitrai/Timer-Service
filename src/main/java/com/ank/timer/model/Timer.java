package com.ank.timer.model;

import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "timer")
public class Timer {

    @Id
    private ObjectId id;

    private long hours;
    private long minutes;
    private long seconds;

    private long triggerTimeStamp;
    private String webhookUrl;

    private State timerState;
}
