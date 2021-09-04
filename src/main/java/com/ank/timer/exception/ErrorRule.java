package com.ank.timer.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Getter
public enum ErrorRule {

    TECHNICAL_ERROR(INTERNAL_SERVER_ERROR, "Technical error"),
    TIMER_NOT_FOUND(NOT_FOUND, "Timer with provided id not found");

    private final HttpStatus httpStatus;
    private final String description;
}
